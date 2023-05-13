package com.example.demo5;

public class Adjacent {
    private GraphNode graphNode;
    private double distance;

    public Adjacent(GraphNode graphNode, double distance) {
        this.setGraphNode(graphNode);
        this.distance = distance;

    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public GraphNode getGraphNode() {
        return graphNode;
    }

    public void setGraphNode(GraphNode graphNode) {
        this.graphNode = graphNode;
    }


}
