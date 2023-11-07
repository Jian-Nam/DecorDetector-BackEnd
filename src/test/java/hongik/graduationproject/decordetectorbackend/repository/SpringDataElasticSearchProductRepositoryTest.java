package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.ProductAndSimilarity;
import hongik.graduationproject.decordetectorbackend.domain.ProductDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    void saveTest(){
        // Given
        Long id = 100L;
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(id);

        Float[] vectorArray = new Float[1000];
        Arrays.setAll(vectorArray, value -> Float.valueOf(0.1f));
        ArrayList<Float> vector = new ArrayList<>(Arrays.asList(vectorArray));

        productDocument.setImageVector(vector);

        // When
        productSearchRepository.save(productDocument);
        ProductDocument result = productSearchRepository.findById(id).get();

        // Then
        assertThat(result.getImageVector()).isEqualTo(vector);
    }

    @Test
    void deleteTest(){
        // Given
        Long id = 100L;
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(id);

        Float[] vectorArray = new Float[1000];
        Arrays.setAll(vectorArray, value -> Float.valueOf(0.1f));
        ArrayList<Float> vector = new ArrayList<>(Arrays.asList(vectorArray));

        productDocument.setImageVector(vector);

        // When
        productSearchRepository.save(productDocument);
        ProductDocument savedResult = productSearchRepository.findById(id).get();

        productSearchRepository.delete(id);
        Optional<ProductDocument> deletedResult = productSearchRepository.findById(id);

        // Then
        assertThat(savedResult.getImageVector()).isEqualTo(vector);
        assertThat(deletedResult.isEmpty()).isTrue();

    }

//    @Test
//    void getByImageVectorTest(){
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
//    }
}
