package com.xinyuan.haze.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by Sofar on 2016/10/20.
 */
public class Searcher {

    public static void search(String keyword) throws IOException, ParseException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("d:\\lucene")));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new SmartChineseAnalyzer();
        QueryParser parser = new QueryParser("contents", analyzer);
        Query query = parser.parse(keyword);
        doPagingSearch(searcher, query, 10);
    }

    private static void doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage) throws IOException {
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
          ScoreDoc[] hits = results.scoreDocs;
           int numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");
        for (ScoreDoc scoreDoc : results.scoreDocs) {
            int docNum = scoreDoc.doc;
            float score = scoreDoc.score;
            System.out.println( " 当前得分"  + score);
            Document document = searcher.doc(docNum);
            document.getFields().forEach(f -> {
                System.out.println(f.name() + "||" + f.stringValue());
            });
        }
    }
}
