package hongik.graduationproject.decordetectorbackend.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class IkeaClient {

    public String getProductData(String category, String start, String end){
        URI uri = UriComponentsBuilder
                .fromUriString("https://sik.search.blue.cdtapps.com/kr/ko/product-list-page/more-products?category={category}&start={start}&end={end}&c=lf&v=20220826&sort=RELEVANCE")
                .buildAndExpand(category, start, end)
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        return responseEntity.getBody();
    }
}
