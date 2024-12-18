package biz.bna.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Word {
    private BoundingBox boundingBox;

    private String text;

    @JsonProperty("entity_index")
    private String entityIndex;

    public Word() {
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEntityIndex() {
        return entityIndex;
    }

    public void setEntityIndex(String entityIndex) {
        this.entityIndex = entityIndex;
    }
}
