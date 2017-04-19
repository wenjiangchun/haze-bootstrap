package com.xinyuan.haze.demo.algo.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort extends AbstractSort {
    @Override
    public void sort(Comparable[] c) {
        for (int i = 0; i < c.length - 1; i++) {
           for (int j = 0; j < c.length - i - 1 ; j++) {
               if (!less(c[j], c[j + 1])) {
                  exchange(c, j, j + 1);
               }
           }
        }
        logger.info(Arrays.toString(c));
    }
}
