package hongik.graduationproject.decordetectorbackend.domain;

import org.springframework.core.io.Resource;

import java.util.List;

public class SearchResult {
    private String segmentedImage;
    private List<SimilarProduct> similarProducts;

    public String getSegmentedImage() {
        return segmentedImage;
    }

    public void setSegmentedImage(String segmentedImage) {
        this.segmentedImage = segmentedImage;
    }

    public List<SimilarProduct> getSimilarProducts() {
        return similarProducts;
    }

    public void setSimilarProducts(List<SimilarProduct> similarProducts) {
        this.similarProducts = similarProducts;
    }
}
