package biz.bna.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Block {

    private BoundingBox boundingBox;

    private List<Line> lines;

    private List<Language> languages;

    public Block() {
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
