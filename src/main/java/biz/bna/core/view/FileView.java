package biz.bna.core.view;

import software.amazon.awssdk.services.s3.model.S3Object;

import java.time.Instant;

public class FileView {
    private String name;
    private long size;
    private Instant modify;

    public FileView() {
    }

    public FileView(String name, long size, Instant modify) {
        this.name = name;
        this.size = size;
        this.modify = modify;
    }

    public FileView(S3Object object){
        this.name = object.key();
        this.modify = object.lastModified();
        this.size = object.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Instant getModify() {
        return modify;
    }

    public void setModify(Instant modify) {
        this.modify = modify;
    }
}
