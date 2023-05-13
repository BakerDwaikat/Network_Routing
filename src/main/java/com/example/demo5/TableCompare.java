package com.example.demo5;

import java.util.Comparator;

public class TableCompare implements Comparator<Node> {

    @Override
    public int compare(Node t1, Node t2) {
        System.out.println(t1.getSrc().getName()+t1.getCurrentNode().getName()+" d"+t1.getDistance());
        System.out.println(t2.getSrc().getName()+t2.getCurrentNode().getName()+" d"+t2.getDistance());
        System.out.println( (t1.getDistance() - t2.getDistance()));


        return (int) (t1.getDistance() - t2.getDistance());
    }

}

