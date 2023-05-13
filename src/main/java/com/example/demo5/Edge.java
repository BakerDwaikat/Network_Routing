package com.example.demo5;

public class Edge {
    private GraphNode src;
    private GraphNode des;
    private double distance;

    public Edge(GraphNode src, GraphNode des, double distance) {
        super();
        this.src = src;
        this.des = des;
        this.distance = distance;
    }

    public GraphNode getSrc() {
        return src;
    }

    public void setSrc(GraphNode src) {
        this.src = src;
    }

    public GraphNode getDes() {
        return des;
    }

    public void setDes(GraphNode des) {
        this.des = des;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Edge [sourceCity=" + src + ", targetCity=" + des + ", distance=" + distance + "]";
    }
}
