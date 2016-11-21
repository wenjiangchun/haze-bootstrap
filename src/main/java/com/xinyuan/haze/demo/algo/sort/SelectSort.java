package com.xinyuan.haze.demo.algo.sort;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * Created by Sofar on 2016/10/8.
 */
public class SelectSort extends AbstractSort {

    @Override
    public void sort(Comparable[] c) {
        for (int i = 0; i < c.length; i++) {
            for (int j = i; j < c.length; j++) {
                if (less(c[j], c[i])) {
                    logger.log(Level.INFO, "第" + i + "次排序，交换" + c[j] + " ; " + c[i]);
                    exchange(c, i, j);
                }
                logger.log(Level.INFO, Arrays.toString(c));
            }
        }
    }
}
