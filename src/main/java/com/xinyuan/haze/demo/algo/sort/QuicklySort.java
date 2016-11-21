package com.xinyuan.haze.demo.algo.sort;

import java.util.Arrays;

/**
 * 快速排序
 * Created by Sofar on 2016/10/21.
 */
public class QuicklySort extends AbstractSort {

    @Override
    public void sort(Comparable[] c) {
        sort(c, 0, c.length - 1);
    }

    private void sort(Comparable[] c, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(c, lo, hi);
        sort(c, lo, j - 1);
        sort(c, j + 1, hi);
    }

    private int partition(Comparable[] c, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = c[lo];
        while (true) {
            while (less(c[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, c[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exchange(c, i, j);
        }
        exchange(c, lo, j);



        return j;
























































    }
}
