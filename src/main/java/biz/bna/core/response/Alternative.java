package biz.bna.core.response;

import java.util.List;

public class Alternative {

    private String text;

    private List<Word> words;

    public Alternative() {
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
