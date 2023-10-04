package hongik.graduationproject.decordetectorbackend.client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AiApiClient {

    private List<Float> jsonArrayToListFloat(JSONArray jsonArray){
        List<Float> listFloat = new ArrayList<>();
        for(Object o: jsonArray){
            listFloat.add(Float.parseFloat(o.toString()));
        }
        return listFloat;
    }
    private List<Float> parseResponse(ResponseEntity<String> responseEntity) throws Exception{
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonResponseBody = (JSONObject) jsonParser.parse(responseEntity.getBody());
        JSONArray jsonVector = (JSONArray) jsonResponseBody.get("vectors");
        return jsonArrayToListFloat(jsonVector);
    }
    public List<Float> convertToVector(String imagelink) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        URL url = new URL(imagelink);
        Resource resource = new UrlResource(url);

        body.add("image", resource);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = "http://127.0.0.1:5000/vectorize";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        System.out.println("status : " + responseEntity.getStatusCode());

        List<Float> vector = parseResponse(responseEntity);

        return vector;
    }
}
