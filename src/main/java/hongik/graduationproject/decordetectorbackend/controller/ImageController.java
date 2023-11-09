package hongik.graduationproject.decordetectorbackend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;


@RestController
public class ImageController {
    @Value("${sourceimage.root}")
    String rootPath;
    @GetMapping("/images/segmented/{date}/{fileName}")
    public Resource downloadSegmentedImage(@PathVariable("date")String date, @PathVariable("fileName")String fileName)throws MalformedURLException {
        String filePath = rootPath + "/downloads/"+ date + "/" + fileName;
        return new UrlResource("file:"+ filePath);
    }
    @GetMapping("/images/ikea")
    public Resource downloadIkeaImage(@RequestParam("url")String url)throws MalformedURLException{
        return new UrlResource(url);
    }

}
