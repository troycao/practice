package com.troy.practice.lock;

import java.util.Arrays;

public class ConditionDemo {

    public static void main(String[] args) {
        TroyBlockQueue troyBlockQueue = new TroyBlockQueue();
        troyBlockQueue.offer("troy");
        troyBlockQueue.offer("cao");

        System.out.println(troyBlockQueue);

    }


}

class TroyBlockQueue{

    volatile int len = 1;

    Object[] objects = new Object[len];

    public void offer(Object o){
        for (int i = 0; i < len; i++) {
            if (objects[i] == null){
                objects[i] = o;
            }
        }

    }

    @Override
    public String toString() {
        return "TroyBlockQueue{" +
                "len=" + len +
                ", objects=" + Arrays.toString(objects) +
                '}';
    }
}
