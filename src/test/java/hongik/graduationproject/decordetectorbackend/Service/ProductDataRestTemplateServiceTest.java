package hongik.graduationproject.decordetectorbackend.Service;

import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductDataRestTemplateServiceTest {
    @Autowired
    IkeaClient productDataRestTemplateService;

    @Test
    void getDataTest(){
        String res = productDataRestTemplateService.getProductData("fu001", "0", "1");



        System.out.println(res);
    }
}