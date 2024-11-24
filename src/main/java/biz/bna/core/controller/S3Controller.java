package biz.bna.core.controller;

import biz.bna.core.dto.BucketDTO;
import biz.bna.core.response.StandardResponse;
import biz.bna.core.service.S3Service;
import biz.bna.core.view.BucketView;
import biz.bna.core.view.FileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/api/v1/apps",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @RequestMapping(value = "bucket/getlist", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public List<BucketView> getBucketNames(){
        return s3Service.getBuckets();
    }

    @RequestMapping(value = "bucket/add", method = RequestMethod.POST)
    public String add(@RequestBody BucketDTO bucketDTO){
        s3Service.addBucket(bucketDTO.getName().toLowerCase(Locale.ROOT));
        return StandardResponse.SUCCESS;
    }

    @RequestMapping(value = "bucket/delete", method = RequestMethod.POST)
    public String delete(@RequestBody List<String> names){
        names.stream().forEach(s3Service::deleteBucket);
        return StandardResponse.SUCCESS;
    }

    @RequestMapping(value = "bucket/file/getlist/{bucket}", method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE)
    public List<FileView> getFiles(@PathVariable("bucket") String bucket){
        return s3Service.files(bucket);
    }

    @RequestMapping(value = "bucket/file/upload/{bucket}", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestBody MultipartFile file,
                         @PathVariable(name = "bucket") String bucket){
        try (InputStream is = file.getInputStream()){
            s3Service.uploadFile(bucket, file.getOriginalFilename(), is, file.getSize());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
        return StandardResponse.SUCCESS;
    }

    @RequestMapping(value = "bucket/file/delete/{bucket}", method = RequestMethod.POST)
    public String delete(@RequestBody List<String> names,
                         @PathVariable(name = "bucket") String bucket){
        names.stream()
                .forEach(name -> s3Service.delete(bucket, name));
        return StandardResponse.SUCCESS;
    }
}
