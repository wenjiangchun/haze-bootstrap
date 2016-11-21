package com.xinyuan.haze.lucene.file.filter;

import java.nio.file.Path;

/**
 * Created by Sofar on 2016/10/20.
 */
public interface FileIndexFilter {
    boolean index(Path path);
}
