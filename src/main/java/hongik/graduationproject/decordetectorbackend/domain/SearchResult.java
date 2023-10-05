package hongik.graduationproject.decordetectorbackend.domain;

import org.springframework.core.io.Resource;

import java.util.List;

public class SearchResult {
    private Resource segmentedImage;
    private List<Product> similarProducts;

    public Resource getSegmentedImage() {
        return segmentedImage;
    }

    public void setSegmentedImage(Resource segmentedImage) {
        this.segmentedImage = segmentedImage;
    }

    public List<Product> getSimilarProducts() {
        return similarProducts;
    }

    public void setSimilarProducts(List<Product> similarProducts) {
        this.similarProducts = similarProducts;
    }
}
