package hongik.graduationproject.decordetectorbackend.service;

import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.form.SearchForm;
import hongik.graduationproject.decordetectorbackend.domain.*;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.repository.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
public class SearchingService {
    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final AiApiClient aiApiClient;

    @Value("${sourceimage.root}")
    private String rootPath;
    @Value("${sourceimage.upload.path}")
    private String uploadFolderPath;
    @Value("${sourceimage.download.path}")
    private String downloadFolderPath;

    @Value("${my.address}")
    private String myRootAddress;

    public SearchingService( ProductRepository productRepository, ProductSearchRepository productSearchRepository,  AiApiClient aiApiClient) {
        this.productRepository = productRepository;
        this.aiApiClient = aiApiClient;
        this.productSearchRepository = productSearchRepository;
    }

    private String getFormattedDateString(){
        //날짜 String 얻기
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    private String createResourceFilePath(String filePath, String title)throws IOException{
        ClassPathResource resource = new ClassPathResource(filePath + title);
        // IOException
        Path path = Paths.get(resource.getURI());
        return path.toString();
    }

    private void makeDirectoryIfNotExist(String directoryPath){
        File dir = new File(directoryPath);
        if (!dir.exists()) dir.mkdirs();
    }



    public SearchResult searchProduct(SearchForm form){
        SearchResult searchResult = new SearchResult();

        //날짜 String 얻기
        String fileDate = getFormattedDateString();

        //업로드 폴더 내 날짜 폴더 생성
        String uploadPath =  rootPath + uploadFolderPath + "/" + fileDate;
        String downloadPath = rootPath + downloadFolderPath + "/" +  fileDate;

        //폴더 없을 시 생성
        makeDirectoryIfNotExist(uploadPath);
        makeDirectoryIfNotExist(downloadPath);

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

            searchResult.setSegmentedImage(myRootAddress + "/images/segmented/" + fileDate +  "/" + randomFileName + ".jpg");
            List<Float> vector = aiApiClient.convertToVector(segmentedResource);
            keyVector = vector;

        } catch (Exception e){
            System.out.println("이미지 변환 실패");
            e.printStackTrace();
        }

        try {
            List<ProductAndSimilarity> productAndSimilarityList = productSearchRepository.getByImageVector(keyVector);
            for(ProductAndSimilarity p: productAndSimilarityList){
                Optional<Product> optionalProduct = productRepository.findById(p.getId());
                if(optionalProduct.isPresent()){
                    Product product = optionalProduct.get();
                    p.setName(product.getProductName());
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
