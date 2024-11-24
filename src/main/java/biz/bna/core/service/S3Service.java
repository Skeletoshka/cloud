package biz.bna.core.service;

import biz.bna.core.config.S3Config;
import biz.bna.core.view.BucketView;
import biz.bna.core.view.FileView;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {

    @Autowired
    private S3Config config;

    private S3Client client;

    @PostConstruct
    private void init(){
        client = S3Client.builder()
                .region(Region.of(config.regionName))
                .credentialsProvider(StaticCredentialsProvider.create(config.getCredentials()))
                .build();
    }

    public List<BucketView> getBuckets(){
        ListBucketsResponse response = client.listBuckets();
        return response.buckets().stream().map(BucketView::new).collect(Collectors.toList());
    }

    public void addBucket(String bucketName){
        client.createBucket(request -> request.bucket(bucketName));
    }

    public void deleteBucket(String bucketName){
        client.deleteBucket(request -> request.bucket(bucketName));
    }

    public void uploadFile(String bucketName, String fileName, InputStream is, long length){
        client.putObject(request -> request
                        .bucket(bucketName)
                        .key(fileName)
                        .ifNoneMatch("*"),
                RequestBody.fromInputStream(is, length));
    }

    public void delete(String bucketName, String fileName){
        client.deleteObject(request -> request
                        .bucket(bucketName)
                        .key(fileName));
    }

    public List<FileView> files(String bucketName) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        return client.listObjectsV2(listObjectsV2Request).contents()
                .stream()
                .map(FileView::new)
                .collect(Collectors.toList());
    }

}
