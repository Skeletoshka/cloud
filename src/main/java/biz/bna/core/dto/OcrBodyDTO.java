package biz.bna.core.dto;

import java.util.List;

public class OcrBodyDTO {
    private String mimeType;
    private List<String> languageCodes;
    private String model;
    private byte[] content;

    public OcrBodyDTO() {
    }

    public OcrBodyDTO(String mimeType,
                      List<String> languageCodes,
                      String model,
                      byte[] content) {
        this.mimeType = mimeType;
        this.languageCodes = languageCodes;
        this.model = model;
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public List<String> getLanguageCodes() {
        return languageCodes;
    }

    public void setLanguageCodes(List<String> languageCodes) {
        this.languageCodes = languageCodes;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
