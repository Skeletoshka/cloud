package biz.bna.core.dto;

import software.amazon.awssdk.services.s3.model.Bucket;

import java.time.Instant;

public class BucketDTO {
    private String name;

    public BucketDTO() {
    }

    public BucketDTO(String name) {
        this.name = name;
    }

    public BucketDTO(Bucket bucket){
        this.name = bucket.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
