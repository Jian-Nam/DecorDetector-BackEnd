package hongik.graduationproject.decordetectorbackend.form;

import org.springframework.web.multipart.MultipartFile;

public class SearchForm {
    private int pointX;
    private int pointY;
    private MultipartFile image;

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
