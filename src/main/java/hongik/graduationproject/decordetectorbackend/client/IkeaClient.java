package hongik.graduationproject.decordetectorbackend.client;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IkeaClient {

    private Product mapProduct(JSONObject jsonObject){
        Product product = new Product();
        product.setExternalId(Long.parseLong((String) jsonObject.get("id")));
        product.setName(((String) jsonObject.get("name")) + " " + ((String) jsonObject.get("typeName")));
        product.setImage((String) jsonObject.get("mainImageUrl"));
        product.setLink((String) jsonObject.get("pipUrl"));
        return product;
    }

    private Optional<JSONArray> parseIkeaApiResponse(String jsonString){
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            JSONObject jsonProducts = (JSONObject) jsonObject.get("moreProducts");
            JSONArray jsonProductWindow = (JSONArray) jsonProducts.get("productWindow");
            return Optional.ofNullable(jsonProductWindow);
        }catch(ParseException e){
            System.out.println("Parsing failed");
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Product> getProductData(String category, String start, String end){
        URI uri = UriComponentsBuilder
                .fromUriString("https://sik.search.blue.cdtapps.com/kr/ko/product-list-page/more-products?category={category}&start={start}&end={end}&c=lf&v=20220826&sort=RELEVANCE")
                .buildAndExpand(category, start, end)
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        List<Product> result = new ArrayList<>();
        Optional<JSONArray> ikeaProductList = parseIkeaApiResponse(responseEntity.getBody());

        if(ikeaProductList.isPresent()) {
            for (Object o : ikeaProductList.get()) {
                JSONObject jsonProduct = (JSONObject) o;
                result.add(mapProduct(jsonProduct));
            }
        }

        return result;
    }
}
