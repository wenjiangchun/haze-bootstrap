package com.xinyuan.haze.demo.algo.sort;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * 选择排序
 */
public class SelectSort extends AbstractSort {

    @Override
    public void sort(Comparable[] c) {
        int n = 0;
        for (int i = 0; i < c.length; i++) {
            int min = i;
            for (int j = i + 1; j < c.length; j++) {
                if (less(c[j], c[min])) {
                    min = j;
                }
            }
            if (min != i) {
                logger.info("执行第" + ++n + "次交换");
                logger.info("交换前。。。" + Arrays.toString(c));
                exchange(c, i, min);
                logger.info("交换后。。。" + Arrays.toString(c));
            }
        }
    }
}
