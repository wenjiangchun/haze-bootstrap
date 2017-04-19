package com.xinyuan.haze.demo.algo.sort;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sofar on 2016/10/8.
 */
public class SelectSortTest {
    @Test
    public void sort() throws Exception {
        String[] c = new String[]{"T","A", "E", "I", "O", "Q", "P",  "G", "B"};
        //AbstractSort selectSort = new SelectSort();
        AbstractSort selectSort = new BubbleSort();
        selectSort.sort(c);
    }

}