package biz.bna.core.response;

import java.util.List;

public class BoundingBox {
    private List<Coordinate> vertices;

    public BoundingBox() {
    }

    public BoundingBox(List<Coordinate> vertices) {
        this.vertices = vertices;
    }

    public List<Coordinate> getVertices() {
        return vertices;
    }

    public void setVertices(List<Coordinate> vertices) {
        this.vertices = vertices;
    }
}
