package com.xinyuan.haze.lucene;

import com.xinyuan.haze.lucene.file.filter.FileIndexFilter;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * Created by Sofar on 2016/10/20.
 */
public class IndexCreatorTest {
    public void createIndex() throws Exception {

        String file = "d:\\aaa";
        IndexCreator indexCreator = new IndexCreator();
        indexCreator.createIndex(file, new FileIndexFilter() {
            @Override
            public boolean index(Path path) {
                return true;
            }
        });
       // Searcher.search("贵州");
    }

}