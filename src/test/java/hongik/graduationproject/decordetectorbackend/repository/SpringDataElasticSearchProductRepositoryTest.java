package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.ProductAndSimilarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SpringDataElasticSearchProductRepositoryTest {

    @Autowired
    ProductSearchRepository productSearchRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    void getByImageVectorTest(){
//        //given
//        Optional<Product> optionalProduct = productRepository.findById(Long.valueOf(5));
//        Product product = optionalProduct.get();
//
//        //when
//        try {
//            List<ProductAndSimilarity> result = productSearchRepository.getByImageVector(vector);
//            for(ProductAndSimilarity p: result){
//                System.out.println(p.getId());
//                System.out.println(p.getCosineSimilarity());
//            }
//        } catch (Exception e){
//            System.out.println(e);
//            e.printStackTrace();
//        }
//        //then
    }
}
