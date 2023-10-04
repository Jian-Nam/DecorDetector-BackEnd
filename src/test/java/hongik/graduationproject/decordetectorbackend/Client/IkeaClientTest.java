package hongik.graduationproject.decordetectorbackend.Client;

import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
import hongik.graduationproject.decordetectorbackend.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class IkeaClientTest {
    @Autowired
    IkeaClient ikeaClient;

    @Test
    void getDataTest(){
        List<Product> res = ikeaClient.getProductData("fu001", "0", "3");
        for(Product p: res){
            System.out.println(p.getName());
        }
    }
}