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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class SpringDataJpaRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    void saveProductAndFindByNameTest(){
        //given
        Product product = new Product();
        product.setProductName("123");
        product.setExternalId("112233");
        product.setImage("http/blabla/desk.jpg");
        product.setLink("http/blabla/desk");

        //when
        repository.save(product);
        Product result = repository.findByProductName(product.getProductName()).get();
        //then
        assertThat(result).isEqualTo(product);
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
    void findBySimilarityTest(){
        //given

        //when
        //List<SimilarProduct> similarProducts = repository.findBySimilarity();
        //then
        //for(SimilarProduct sp: similarProducts ){
            //System.out.println(sp.getId() + " " + sp.getName() + " " + sp.getCosineSimilarity());
        //}
    }
}


