package cn.codesheep.springbt_watermark.controller;

import cn.codesheep.springbt_watermark.model.dto.ImageInfo;
import cn.codesheep.springbt_watermark.service.ImageUploadService;
import cn.codesheep.springbt_watermark.service.WatermarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@RestController
public class WatermarkController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private WatermarkService watermarkService;

    /**
     * MultipartFile  这个类一般是用来接受前台传过来的文件，做个导入功能。
     * getOriginalFilename（）方法是得到原来的文件名在客户机的文件系统名称
     * @param image
     * @return
     */
    @RequestMapping(value = "/watermarktest", method = RequestMethod.POST)
    public ImageInfo watermarkTest( @RequestParam("file") MultipartFile image ) {

        ImageInfo imgInfo = new ImageInfo();

        String uploadPath = "static/images/";  // 服务器上上传文件的相对路径
        String physicalUploadPath = getClass().getClassLoader().getResource(uploadPath).getPath();  // 服务器上上传文件的物理路径

        String imageURL = imageUploadService.uploadImage( image, uploadPath, physicalUploadPath );
        File imageFile = new File(physicalUploadPath + image.getOriginalFilename() );

        String watermarkAddImageURL = watermarkService.watermarkAdd(imageFile, image.getOriginalFilename(), uploadPath, physicalUploadPath);

        imgInfo.setImageUrl(imageURL);
        imgInfo.setLogoImageUrl(watermarkAddImageURL);
        System.out.println("完成");
        return imgInfo;
    }
}
