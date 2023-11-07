package hongik.graduationproject.decordetectorbackend.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import hongik.graduationproject.decordetectorbackend.domain.ProductAndSimilarity;
import hongik.graduationproject.decordetectorbackend.domain.ProductDocument;
import jakarta.json.Json;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;


import java.io.IOException;
import java.util.*;

public class SpringDataElasticSearchProductRepository implements ProductSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;
    private final RestClient restClient;

    public SpringDataElasticSearchProductRepository(ElasticsearchOperations elasticsearchOperations, RestClient restClient) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.restClient = restClient;
    }

    @Override
    public ProductDocument save(ProductDocument productDocument){
        return elasticsearchOperations.save(productDocument);
    }

    public void delete(Long id){
        elasticsearchOperations.delete(id.toString(), ProductDocument.class);
    }

    public Optional<ProductDocument> findById(Long id){
        ProductDocument result = elasticsearchOperations.get(id.toString(), ProductDocument.class);
        return Optional.ofNullable(result);
    }


    public List<ProductAndSimilarity> getByImageVector(List<Float> imageVector) throws JsonProcessingException, IOException, ParseException {
        String endpoint = "/product/_search";

        Map<String, Object> knnQuery = new HashMap<>();
        knnQuery.put("field", "imageVector");
        knnQuery.put("k", 10);
        knnQuery.put("num_candidates", 100);
        knnQuery.put("query_vector", imageVector);

        List<String> responseFields = new ArrayList<String>();
        responseFields.add("id");

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("knn", knnQuery);
        requestPayload.put("fields", responseFields);

        // Serialize the request payload as JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ObjectWriter objectWriter = objectMapper.writer();

        // JsonProcessingException
        String payloadJSON = objectWriter.writeValueAsString(requestPayload);
        Request request = new Request("POST", endpoint);
        request.setJsonEntity(payloadJSON);

        // IOException
        Response response = restClient.performRequest(request);
        String responseBody = EntityUtils.toString(response.getEntity());

        // Parse Respoonse, ParseException
        JSONArray jsonHitsArray = parseESSearchApiResponse(responseBody);
        ArrayList<ProductAndSimilarity> result = new ArrayList<ProductAndSimilarity>();

        for(Object o: jsonHitsArray){
            JSONObject jsonHit = (JSONObject) o;
            ProductAndSimilarity productAndSimilarity = mapSearchResult(jsonHit);
            result.add(productAndSimilarity);
        }
        return result;
    }

    private JSONArray parseESSearchApiResponse(String jsonString) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONObject jsonHitsInfo = (JSONObject) jsonObject.get("hits");
        return (JSONArray) jsonHitsInfo.get("hits");
    }

    private ProductAndSimilarity mapSearchResult(JSONObject jsonHit){
        ProductAndSimilarity productAndSimilarity = new ProductAndSimilarity();
        productAndSimilarity.setId(Long.valueOf((String) jsonHit.get("_id")) );
        productAndSimilarity.setCosineSimilarity(((Double) jsonHit.get("_score")).floatValue());
        return productAndSimilarity;
    }
}
