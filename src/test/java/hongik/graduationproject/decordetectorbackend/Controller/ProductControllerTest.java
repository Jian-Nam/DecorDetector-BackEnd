package hongik.graduationproject.decordetectorbackend.Controller;

import hongik.graduationproject.decordetectorbackend.controller.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductControllerTest {
    @Autowired
    ProductController productController;


}
