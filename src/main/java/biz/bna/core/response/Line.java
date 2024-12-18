package biz.bna.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Line {

    private BoundingBox boundingBox;

    private String text;

    private List<Word> words;

    public Line() {
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

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
