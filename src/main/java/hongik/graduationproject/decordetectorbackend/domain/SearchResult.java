package hongik.graduationproject.decordetectorbackend.domain;

import org.springframework.core.io.Resource;

import java.util.List;

public class SearchResult {
    private String segmentedImage;
    private List<ProductAndSimilarity> productAndSimilarityList;

    public String getSegmentedImage() {
        return segmentedImage;
    }

    public void setSegmentedImage(String segmentedImage) {
        this.segmentedImage = segmentedImage;
    }

    public List<ProductAndSimilarity> getSimilarProducts() {
        return productAndSimilarityList;
    }

    public void setSimilarProducts(List<ProductAndSimilarity> productAndSimilarityList) {
        this.productAndSimilarityList = productAndSimilarityList;
    }
}
