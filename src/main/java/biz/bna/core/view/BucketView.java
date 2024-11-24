package biz.bna.core.view;

import software.amazon.awssdk.services.s3.model.Bucket;

import java.time.Instant;
import java.time.LocalDate;

public class BucketView {
    private String name;
    private Instant dateCreate;

    public BucketView() {
    }

    public BucketView(String name, Instant dateCreate) {
        this.name = name;
        this.dateCreate = dateCreate;
    }

    public BucketView(Bucket bucket){
        this.name = bucket.name();
        this.dateCreate = bucket.creationDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Instant dateCreate) {
        this.dateCreate = dateCreate;
    }
}
