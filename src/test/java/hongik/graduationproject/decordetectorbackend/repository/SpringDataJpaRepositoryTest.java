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
        product.setName("123");
        product.setExternalId("112233");
        product.setImage("http/blabla/desk.jpg");
        product.setLink("http/blabla/desk");

        List<Float> vector1 = new ArrayList<Float>();
        vector1.add(3.14F);
        vector1.add(8.05F);
        vector1.add(5.29F);
        vector1.add(3.28F);
        product.setVector( vector1 );

        //when
        repository.save(product);
        Product result = repository.findByName(product.getName()).get();
        //then
        assertThat(result).isEqualTo(product);
    }
    @Test
    void saveProductAndFindByExternalIdTest(){
        //given
        Product product = new Product();
        product.setName("123");
        product.setExternalId("112233");
        product.setImage("http/blabla/desk.jpg");
        product.setLink("http/blabla/desk");

        List<Float> vector1 = new ArrayList<Float>();
        vector1.add(3.14F);
        vector1.add(8.05F);
        vector1.add(5.29F);
        vector1.add(3.28F);
        product.setVector( vector1 );

        //when
        repository.save(product);
        Product result = repository.findByExternalId(product.getExternalId()).get();
        //then
        assertThat(result).isEqualTo(product);
    }
}


