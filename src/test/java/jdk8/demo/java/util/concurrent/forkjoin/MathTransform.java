package jdk8.demo.java.util.concurrent.forkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by sofar on 16-1-24.
 */
public class MathTransform extends RecursiveAction {

    private final int seqThreshold = 5000;

    private double[] data;

    private int start, end;

    public MathTransform(double[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        /*for (int i = start; i < end; i++) {
            data[i] = Math.cos(data[i]);
        }*/
        if ((end - start) < seqThreshold) {
            for (int i = start; i < end; i++) {
                data[i] = Math.sqrt(data[i]);
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new MathTransform(data, start, middle), new MathTransform(data, middle, end));
        }
    }

}
