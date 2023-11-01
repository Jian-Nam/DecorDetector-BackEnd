package hongik.graduationproject.decordetectorbackend.Client;

import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.util.List;

@SpringBootTest
public class AiApiClientTest {

    @Autowired
    AiApiClient aiApiClient;

    @Test
    void convertToVectorTest() throws Exception{
        Resource resource = new PathResource("src/test/java/hongik/graduationproject/decordetectorbackend/Client/testImage.JPG");
        try {
            List<Float> res = aiApiClient.convertToVector(resource);
            System.out.println(res);
        }catch(Exception e){}
    }
}
