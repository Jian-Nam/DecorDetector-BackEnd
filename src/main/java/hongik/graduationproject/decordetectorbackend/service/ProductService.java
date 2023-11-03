package hongik.graduationproject.decordetectorbackend.service;


import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
import hongik.graduationproject.decordetectorbackend.controller.SearchForm;
import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.ProductDocument;
import hongik.graduationproject.decordetectorbackend.domain.SearchResult;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.repository.ProductSearchRepository;
import hongik.graduationproject.decordetectorbackend.repository.SpringDataElasticSearchProductRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    //private final ProductSearchRepository productSearchRepository;
    private final IkeaClient ikeaClient;
    private final AiApiClient aiApiClient;
    private final ProductSearchRepository productSearchRepository;


    public ProductService(ProductRepository productRepository, ProductSearchRepository productSearchRepository, IkeaClient ikeaClient, AiApiClient aiApiClient) {

        this.productRepository = productRepository;
        //this.productSearchRepository = productSearchRepository;
        this.ikeaClient = ikeaClient;
        this.aiApiClient = aiApiClient;
        this.productSearchRepository = productSearchRepository;
    }

    public Long addProduct(Product product){
        try {
            URL url = new URL(product.getImage());
            Resource resource = new UrlResource(url);
            Product savedProduct = productRepository.save(product);

            ProductDocument productDocument = new ProductDocument();
            productDocument.setId(savedProduct.getId());
            productDocument.setImageVector(aiApiClient.convertToVector(resource));
            ProductDocument savedDocument = productSearchRepository.save(productDocument);
            System.out.println(savedDocument.getId());
        }catch (Exception e){
            System.out.println("이미지 벡터화 OR Elasticsearch 연결 실패");
            e.printStackTrace();
        }
        return product.getId();
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findProduct(Long productId){
        return productRepository.findById(productId);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public List<String> updateAllProducts(String category, String start, String end){
        List<Product> productList = ikeaClient.getProductData(category, start, end);
        List<String> updatedList = updateByList(productList);
        return updatedList;
    }
    private List<String> updateByList(List<Product> productList){
        List<String> updatedList = new ArrayList<>();

        for(Product product: productList){
            String externalId = product.getExternalId();

            if(productRepository.findByExternalId(externalId).isEmpty()){
                addProduct(product);
                updatedList.add(externalId);
            }
        }
        return updatedList;
    }



}
