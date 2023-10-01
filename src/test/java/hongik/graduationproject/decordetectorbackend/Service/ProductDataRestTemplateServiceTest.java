package hongik.graduationproject.decordetectorbackend.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.graduationproject.decordetectorbackend.service.ProductDataRestTemplateService;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductDataRestTemplateServiceTest {
    @Autowired
    ProductDataRestTemplateService productDataRestTemplateService;

    @Test
    void getDataTest(){
        String res = productDataRestTemplateService.getProductData("fu001", "0", "1");



        System.out.println(res);
    }
}