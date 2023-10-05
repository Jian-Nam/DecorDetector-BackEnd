package hongik.graduationproject.decordetectorbackend.controller;

import org.springframework.web.multipart.MultipartFile;

public class SearchForm {
    private String pointX;
    private String pointY;
    private MultipartFile image;

    public String getPointX() {
        return pointX;
    }

    public void setPointX(String pointX) {
        this.pointX = pointX;
    }

    public String getPointY() {
        return pointY;
    }

    public void setPointY(String pointY) {
        this.pointY = pointY;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
