package hongik.graduationproject.decordetectorbackend.client;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IkeaClient {

    private final RestTemplate restTemplate;
    @Value("${externalapi.ikea.url}")
    private String ikeaApiUrl;

    public IkeaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Product mapProduct(JSONObject jsonObject){
        Product product = new Product();
        product.setExternalId((String)jsonObject.get("id"));
        product.setProductName(((String) jsonObject.get("name")) + " " + ((String) jsonObject.get("typeName")));
        product.setImage((String) jsonObject.get("mainImageUrl"));
        product.setLink((String) jsonObject.get("pipUrl"));
        return product;
    }

    private JSONArray parseIkeaApiResponse(String jsonString) throws Exception{
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONObject jsonProducts = (JSONObject) jsonObject.get("moreProducts");
        return (JSONArray) jsonProducts.get("productWindow");
    }

    public List<Product> getProductData(String category, String start, String end){
        URI uri = UriComponentsBuilder
                .fromUriString(ikeaApiUrl)
                .queryParam("category", "{category}")
                .queryParam("start", "{start}")
                .queryParam("end", "{end}")
                .queryParam("c", "lf")
                .queryParam("v", "20220826")
                .queryParam("sort", "RELEVANCE")
                .encode()
                .buildAndExpand(category, start, end)
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        System.out.println("status : " + responseEntity.getStatusCode());

        List<Product> result = new ArrayList<>();

        try {
            JSONArray ikeaProductList = parseIkeaApiResponse(responseEntity.getBody());
            for (Object o : ikeaProductList) {
                JSONObject jsonProduct = (JSONObject) o;
                result.add(mapProduct(jsonProduct));
            }
        }catch (Exception e){
            System.out.println("parsing failed");
            e.printStackTrace();
        }



        return result;
    }
}
