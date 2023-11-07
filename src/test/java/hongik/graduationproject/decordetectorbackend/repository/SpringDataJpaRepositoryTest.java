package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class SpringDataJpaRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    void saveProductAndFindByIdTest(){
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
        Product savedProduct = repository.save(product);
        Long saverProductId = savedProduct.getId();
        Product result = repository.findById(saverProductId).get();

        //then
        assertThat(result.getProductName()).isEqualTo(productName);
        assertThat(result.getExternalId()).isEqualTo(externalId);
        assertThat(result.getImage()).isEqualTo(image);
        assertThat(result.getLink()).isEqualTo(link);
    }
    @Test
    void saveProductAndFindByExternalIdTest(){
        //given
        Product product = new Product();
        product.setProductName("123");
        product.setExternalId("112233");
        product.setImage("http/blabla/desk.jpg");
        product.setLink("http/blabla/desk");

        //when
        repository.save(product);
        Product result = repository.findByExternalId(product.getExternalId()).get();
        //then
        assertThat(result).isEqualTo(product);
    }

    @Test
    void deleteByIdTest(){
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
        Product savedProduct = repository.save(product);
        Long saverProductId = savedProduct.getId();
        Product savedResult = repository.findById(saverProductId).get();


        repository.deleteById(saverProductId);
        Optional<Product> deletedResult = repository.findById(saverProductId);


        //then
        assertThat(savedResult.getProductName()).isEqualTo(productName);
        assertThat(savedResult.getExternalId()).isEqualTo(externalId);
        assertThat(savedResult.getImage()).isEqualTo(image);
        assertThat(savedResult.getLink()).isEqualTo(link);

        assertThat(deletedResult.isEmpty()).isTrue();
    }


}


