package hongik.graduationproject.decordetectorbackend.Service;

import hongik.graduationproject.decordetectorbackend.form.SearchForm;
import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

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
        String productName = "testProduct";
        String externalId = "234987";
        String image = "https://www.ikea.com/kr/ko/images/products/hattasen-bedside-table-shelf-unit-light-beige__1188929_pe899630_s5.jpg";
        String link = "https://www.ikea.com/kr/ko/p/hyltarp-3-seat-sofa-gransel-natural-s49489635/";

        Product product = new Product();
        product.setProductName(productName);
        product.setExternalId(externalId);
        product.setImage(image);
        product.setLink(link);

        //when
        try {
            productService.addProduct(product);
        }catch (Exception e){

        }

        Product result = productRepository.findByProductName(product.getProductName()).get();

        //then
        assertThat(product).isEqualTo(result);
    }

    @Test
    @Commit
    void updateAllProductsTest(){
        try {
            productService.updateAllProducts("fu001", "0", "7");
        }catch(Exception e){

        }

    }

    @Test
    void searchProductsTest(){
        SearchForm searchForm = new SearchForm();
        //productService.searchProduct(searchForm);
    }

}
