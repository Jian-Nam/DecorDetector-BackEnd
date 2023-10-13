package hongik.graduationproject.decordetectorbackend.service;

import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.controller.SearchForm;
import hongik.graduationproject.decordetectorbackend.domain.*;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.repository.ProductSearchRepository;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
public class SearchingService {
    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final AiApiClient aiApiClient;

    public SearchingService( ProductRepository productRepository, ProductSearchRepository productSearchRepository,  AiApiClient aiApiClient) {
        this.productRepository = productRepository;
        this.aiApiClient = aiApiClient;
        this.productSearchRepository = productSearchRepository;
    }

    public SearchResult searchProduct(SearchForm form){
        SearchResult searchResult = new SearchResult();

        //날짜 String 얻기
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileDate = sdf.format(date);

        //업로드 폴더 내 날짜 폴더 생성
        String rootPath = "D:/NJA/Project/hise_GraduationProject/DecorDetector-BackEnd/decordetector-backend/src/main/resources/static/";
        String responseRootPath = "http://localhost:8080/";
        String uploadPath = rootPath+ "uploads/" + fileDate;
        String downloadPath = rootPath+ "downloads/" + fileDate;
        File uploadDir = new File(uploadPath);
        File downloadDir = new File(downloadPath);

        //폴더 없을 시 생성
        if (!uploadDir.exists()) uploadDir.mkdir();
        if (!downloadDir.exists()) downloadDir.mkdir();

        //랜덤으로 파일명 지정
        String randomFileName = UUID.randomUUID().toString();
        String uploadFilePath =  uploadPath + "/" + randomFileName + ".jpg";
        String downloadFilePath = downloadPath + "/" + randomFileName +".jpg";

        List<Float> keyVector = new ArrayList<Float>();
        try {
            saveBytesToFile(uploadFilePath, form.getImage().getBytes());
            Resource originalResource = new PathResource(uploadFilePath);
            Resource segmentedImage = aiApiClient.segmentImage(originalResource, form.getPointX(), form.getPointY());
            saveBytesToFile(downloadFilePath, segmentedImage.getContentAsByteArray());
            Resource segmentedResource = new PathResource(downloadFilePath);

            searchResult.setSegmentedImage(responseRootPath + "images/segmented/" + fileDate +  "/" + randomFileName + ".jpg");
            List<Float> vector = aiApiClient.convertToVector(segmentedResource);
            keyVector = vector;

        } catch (Exception e){
            System.out.println("이미지 변환 실패");
        }

        try {
            List<ProductAndSimilarity> productAndSimilarityList = productSearchRepository.getByImageVector(keyVector);
            for(ProductAndSimilarity p: productAndSimilarityList){
                Optional<Product> optionalProduct = productRepository.findById(p.getId());
                if(optionalProduct.isPresent()){
                    Product product = optionalProduct.get();
                    p.setName(product.getName());
                    p.setImage(product.getImage());
                    p.setLink(product.getLink());
                }
            }
            searchResult.setSimilarProducts(productAndSimilarityList);
        }catch(Exception e){
            System.out.println("ES search 실패");
            e.printStackTrace();
        }

        //searchKeyRepository.findBySimilarity();


        return searchResult;
    }
    private String saveBytesToFile(String path, byte[] bytes) throws Exception{
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
        return path;
    }
}
