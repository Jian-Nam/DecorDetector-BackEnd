package hongik.graduationproject.decordetectorbackend;

import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.List;

@Configuration
public class SpringConfig {
    private final ProductRepository productRepository;

    @Autowired
    public SpringConfig(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
        return new ProductService(productRepository, ikeaClient(), aiApiClient());
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
