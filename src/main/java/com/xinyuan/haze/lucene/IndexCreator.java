package com.xinyuan.haze.lucene;

import com.xinyuan.haze.lucene.file.filter.FileIndexFilter;
import com.xinyuan.haze.lucene.file.reader.CommonFileReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Sofar on 2016/10/20.
 */
public class IndexCreator {

    private static final String INDEX_PATH = "d:\\lucene";

    /**
     * 获取索引目录
     */
    public Path getIndexDirectory() throws IOException {
        Path p = Paths.get(INDEX_PATH);
        if (!Files.exists(p)) {
            Files.createDirectory(p);
        }
        if (!Files.isDirectory(p)) {
            throw new IOException("当前路径不是目录,path=" + INDEX_PATH);
        }
        if (!Files.isWritable(p)) {
            throw new IOException("当前文件夹不可写,path=" + INDEX_PATH);
        }
        return p;
    }

    /**
     * 创建索引
     * @param path 需要创建索引的文件或文件夹路径
     * @throws IOException
     */
    public void createIndex(String path, FileIndexFilter filter) throws IOException {
        Path indexPath = getIndexDirectory();
        Directory dir = FSDirectory.open(indexPath);
        Analyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try(IndexWriter writer = new IndexWriter(dir, config);) {
            indexDocs(writer, Paths.get(path), filter);
        }
    }

    private void indexDocs(final IndexWriter writer, Path path, FileIndexFilter filter) throws IOException {
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (filter != null && filter.index(path)) {
                        indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            if (filter != null && filter.index(path)) {
                indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
            }
        }
    }

    private void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
        System.out.println("正在处理文件file=" + file);
            Document document = new Document();
            Field pathField = new StringField("path", file.toString(), Field.Store.YES);
            document.add(pathField);
            document.add(new LongPoint("modified", lastModified));
        BufferedReader in = new BufferedReader(new CommonFileReader().getContent(file));
        StringBuffer buffer = new StringBuffer();
        String line = " ";
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
            document.add(new TextField("contents", buffer.toString(), Field.Store.YES));
            if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
                writer.addDocument(document);
            } else {
                writer.updateDocument(new Term("path", file.toString()), document);
            }
    }
}
