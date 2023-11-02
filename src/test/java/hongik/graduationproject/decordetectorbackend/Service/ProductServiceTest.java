package hongik.graduationproject.decordetectorbackend.Service;

import hongik.graduationproject.decordetectorbackend.controller.SearchForm;
import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Test
    void addProductTest(){
        //given
        Product product = new Product();
        product.setName("qqq");
        product.setImage("https://www.ikea.com/kr/ko/images/products/hattasen-bedside-table-shelf-unit-light-beige__1188929_pe899630_s5.jpg");
        product.setLink("http/blabla/desk");

        //when
        productService.addProduct(product);
        Product result = productRepository.findByName(product.getName()).get();

        //then
        assertThat(product).isEqualTo(result);
    }

    @Test
    void updateAllProductsTest(){
        productService.updateAllProducts("fu001", "0", "2");
    }

    @Test
    void searchProductsTest(){
        SearchForm searchForm = new SearchForm();
        //productService.searchProduct(searchForm);
    }

}
