package com.xinyuan.haze.lucene.file.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Path;

/**
 * Created by Sofar on 2016/10/20.
 */
public interface FileReader {

    StringReader getContent(Path path) throws IOException;
}
