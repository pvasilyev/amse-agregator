package ru.amse.agregator.searcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/*
 * Author: Bondarev Timofey
 * Date: Oct 23, 2010
 * Time: 6:05:11 PM
 */

public class Searcher {
    public static void search(File indexDir, String q) {
        try {
            Directory fsDirectory = new NIOFSDirectory(indexDir);
            IndexSearcher is = new IndexSearcher(fsDirectory);

             
            QueryParser qParser = new QueryParser(Version.LUCENE_30,
                                                  "contents", //contents //filename
                                                  new StandardAnalyzer(Version.LUCENE_30));
            Query query= qParser.parse(q);
            System.out.println(query);

            TopFieldCollector tFC = TopFieldCollector.create(Sort.RELEVANCE, 20, true, true, true, false);

            is.search(query, tFC);
            TopDocs docs = tFC.topDocs();
            System.out.println(docs.getMaxScore());
            ScoreDoc[] sDocs = docs.scoreDocs;
            for (int i = 0; i < sDocs.length; ++i) {
                System.out.println(is.doc(sDocs[i].doc).getField("filename"));
            }
            //System.out.println(is.doc(1).getField("contents"));

        } catch (IOException e) {
            System.out.println("Error in search!"); 
        } catch (ParseException e) {
            System.out.println("Error in parse!");
        }
    }
}
