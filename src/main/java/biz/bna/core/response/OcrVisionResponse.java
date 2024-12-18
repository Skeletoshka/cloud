package biz.bna.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OcrVisionResponse {

    private TextAnnotation textAnnotation;

    private String page;

    public OcrVisionResponse() {
    }

    public TextAnnotation getTextAnnotation() {
        return textAnnotation;
    }

    public void setTextAnnotation(TextAnnotation textAnnotation) {
        this.textAnnotation = textAnnotation;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
