package hongik.graduationproject.decordetectorbackend.client;

import hongik.graduationproject.decordetectorbackend.exception.ExternalApiBadConnectionException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.core.io.Resource;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AiApiClient {
    private final RestTemplate restTemplate;
    @Value("${externalapi.ai.baseurl}")
    private String aiApiBaseUrl;
    @Value("${externalapi.ai.segment.path}")
    private String aiApiSegmentPath;
    @Value("${externalapi.ai.vectorize.path}")
    private String aiApiVectorizePath;

    public AiApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private List<Float> jsonArrayToListFloat(JSONArray jsonArray){
        List<Float> listFloat = new ArrayList<>();
        for(Object o: jsonArray){
            listFloat.add(Float.parseFloat(o.toString()));
        }
        return listFloat;
    }
    private List<Float> parseVectorizationResponse(ResponseEntity<String> responseEntity) throws ParseException, ExternalApiBadConnectionException {
        JSONParser jsonParser = new JSONParser();
        //Exception
        JSONObject jsonResponseBody = (JSONObject) jsonParser.parse(responseEntity.getBody());
        JSONArray jsonVector = (JSONArray) jsonResponseBody.get("vectors");
        return jsonArrayToListFloat(jsonVector);
    }
    public List<Float> convertToVector(Resource resource) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("image", resource);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = aiApiBaseUrl+aiApiVectorizePath;

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            throw new ExternalApiBadConnectionException("Ai서버 /vectorize api 연결 실패");
        }

        return parseVectorizationResponse(responseEntity);
    }

    public Resource segmentImage(Resource resource, int pointX, int pointY){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", resource);
        body.add("pointX", pointX);
        body.add("pointY", pointY);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = aiApiBaseUrl + aiApiSegmentPath;

        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(serverUrl, requestEntity, byte[].class);

        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            throw new ExternalApiBadConnectionException("Ai서버 /segment api 연결 실패");
        }

        return new ByteArrayResource(responseEntity.getBody());
    }
}
