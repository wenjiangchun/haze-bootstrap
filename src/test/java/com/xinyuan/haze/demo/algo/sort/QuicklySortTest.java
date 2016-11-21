package com.xinyuan.haze.demo.algo.sort;

import org.apache.lucene.queryparser.xml.QueryBuilderFactory;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Sofar on 2016/10/21.
 */
public class QuicklySortTest {
    @Test
    public void sort() throws Exception {

        String[] c = {"I", "O", "S", "R", "B", "T", "W", "C"};
        QuicklySort sort = new QuicklySort();
        sort.sort(c);
        System.out.println(Arrays.toString(c));
    }

}