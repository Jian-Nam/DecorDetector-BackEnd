package hongik.graduationproject.decordetectorbackend;

import hongik.graduationproject.decordetectorbackend.repository.SpringDataElasticSearchProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.Duration;


@Configuration
public class ElasticSearchClientConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    private String elasticUrl;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticUrl)
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(5))
                .build();
    }
}
