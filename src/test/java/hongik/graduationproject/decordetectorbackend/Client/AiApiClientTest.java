package hongik.graduationproject.decordetectorbackend.Client;

import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AiApiClientTest {

    @Autowired
    AiApiClient aiApiClient;

    @Test
    void convertToVectorTest(){
        String a = "tempt";
        try {
            List<Float> res = aiApiClient.convertToVector(a);
        }catch(Exception e){}
    }
}
