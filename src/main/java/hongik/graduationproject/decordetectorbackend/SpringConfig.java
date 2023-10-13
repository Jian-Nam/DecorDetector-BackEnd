package hongik.graduationproject.decordetectorbackend;

import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.repository.ProductSearchRepository;
import hongik.graduationproject.decordetectorbackend.repository.SpringDataElasticSearchProductRepository;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import hongik.graduationproject.decordetectorbackend.service.SearchingService;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.List;

@Configuration
public class SpringConfig {
    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final RestClient restClient;

    @Autowired
    public SpringConfig(ProductRepository productRepository, ElasticsearchOperations elasticsearchOperations, RestClient restClient) {
        this.productRepository = productRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.restClient = restClient;
    }

    @Bean
    public ProductSearchRepository productSearchRepository(){
        return new SpringDataElasticSearchProductRepository(elasticsearchOperations, restClient);
    }
    @Bean
    public IkeaClient ikeaClient(){
        return new IkeaClient(restTemplate());
    }
    @Bean
    public AiApiClient aiApiClient(){
        return new AiApiClient(restTemplate());
    }
    @Bean
    public ProductService productService(){
        return new ProductService(productRepository, productSearchRepository(), ikeaClient(), aiApiClient());
    }
    @Bean
    public SearchingService searchingService(){
        return new SearchingService(productRepository, productSearchRepository(), aiApiClient());
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
