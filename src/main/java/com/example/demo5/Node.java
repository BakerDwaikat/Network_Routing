package com.example.demo5;

public class Node {
    private GraphNode currentNode;
    private boolean known;
    private double distance;
    private GraphNode src;

    public Node(GraphNode currentNode, boolean known, double distance, GraphNode src) {
        super();
        this.currentNode = currentNode;
        this.known = known;
        this.distance = distance;
        this.src = src;
    }

    public GraphNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(GraphNode currentNode) {
        this.currentNode = currentNode;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public GraphNode getSrc() {
        return src;
    }

    public void setSrc(GraphNode sourceCity) {
        this.src = sourceCity;
    }

    @Override
    public String toString() {
        return "Table [currentCity=" + currentNode + ", known=" + known + ", distance=" + distance + ", sourceCity="
                + src + "]";
    }
}
