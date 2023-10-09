package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.SearchKey;
import hongik.graduationproject.decordetectorbackend.domain.SimilarProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaSearchKeyRepository extends JpaRepository<SearchKey, Long>, SearchKeyRepository {
    @Query(
            value = "WITH input_vector AS(\n" +
                    "    SELECT * FROM key_vector WHERE searchkey_id= :id\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    A.product_id AS id,\n" +
                    "    P.name AS name,\n" +
                    "    P.image AS image,\n" +
                    "    P.link AS link,\n" +
                    "    SUM(A.vector_value * B.vector_value) / (SQRT(SUM(A.vector_value * A.vector_value)) * SQRT(SUM(B.vector_value * B.vector_value))) AS cosineSimilarity\n" +
                    "FROM vector AS A\n" +
                    "JOIN input_vector AS B\n" +
                    "    ON (A.vector_order = B.vector_order)\n" +
                    "JOIN product AS P\n" +
                    "    ON (A.product_id = P.id)\n" +
                    "GROUP BY A.product_id\n" +
                    "ORDER BY cosineSimilarity desc",
            nativeQuery = true)
    List<SimilarProduct> findBySimilarity(@Param(value = "id") Long id);

}
