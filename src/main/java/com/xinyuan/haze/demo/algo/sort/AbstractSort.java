package com.xinyuan.haze.demo.algo.sort;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 抽象排序类 具体实现在不同排序实现类中
 *
 * Created by Sofar on 2016/10/8.
 */
public abstract class AbstractSort {

    Logger logger = Logger.getAnonymousLogger();

   public abstract void sort(Comparable[] c);

    public boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public void exchange(Comparable[] c , int i, int j) {
        Comparable t = c[i];
        c[i] = c[j];
        c[j] = t;
        //logger.log(Level.ALL, c.toString());
    }
}
