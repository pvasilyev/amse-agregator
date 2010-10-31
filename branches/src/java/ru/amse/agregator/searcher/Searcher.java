package ru.amse.agregator.searcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import ru.amse.agregator.storage.DBWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Oct 23, 2010
 * Time: 6:05:11 PM
 */

public class Searcher {
    private static File INDEX_DIR;

    public static void setIndexDir(File iDir) throws FileNotFoundException {
        if (!iDir.exists()) {
            throw new FileNotFoundException("Directory " + iDir.getAbsolutePath() + " is not exists");            
        }
        INDEX_DIR = iDir;
    }

    public static ArrayList<DBWrapper> search(UserQuery query) throws IOException, ParseException {
        return search(INDEX_DIR, query); 
    }

    private static ArrayList<DBWrapper> search(File indexDir, UserQuery query) throws IOException, ParseException {
        return search(indexDir, query.getQueryExpression());
        // todo Нужно бужет добавить реализацию для поиска с метками
    }

    private static ArrayList<DBWrapper> search(File indexDir, String q) throws IOException, ParseException {
        Directory fsDirectory = new NIOFSDirectory(indexDir);
        IndexSearcher is = new IndexSearcher(fsDirectory);
        String fieldForSearch = "name";

        QueryParser qParser = new QueryParser(Version.LUCENE_30,
                                              fieldForSearch,
                                              new StandardAnalyzer(Version.LUCENE_30));
        Query query = qParser.parse(q);

        TopFieldCollector tfc = TopFieldCollector.create(Sort.RELEVANCE, 20, true, true, true, false);

        is.search(query, tfc);
        TopDocs docs = tfc.topDocs();
        System.out.println(docs.getMaxScore());
        ScoreDoc[] sDocs = docs.scoreDocs;
        for (ScoreDoc currentScoreDoc : sDocs) {
            System.out.println(is.doc(currentScoreDoc.doc).getField("name"));
            System.out.println(is.doc(currentScoreDoc.doc).getField("description"));
        }
        // Поиск по другому полю
        fieldForSearch = "description";
        qParser = new QueryParser(Version.LUCENE_30, fieldForSearch, new StandardAnalyzer(Version.LUCENE_30));
        query = qParser.parse(q);
        tfc = TopFieldCollector.create(Sort.RELEVANCE, 20, true, true, true, false);

        is.search(query, tfc);
        docs = tfc.topDocs();
        System.out.println(docs.getMaxScore());
        sDocs = docs.scoreDocs;
    	ArrayList<DBWrapper> objects = new ArrayList<DBWrapper>();

        for (ScoreDoc currentScoreDoc : sDocs) {
            DBWrapper object = new DBWrapper();
            object.setName(is.doc(currentScoreDoc.doc).getField("name").stringValue());
            object.setDescription(is.doc(currentScoreDoc.doc).getField("description").stringValue());
            objects.add(object);
        }        
                        
        return objects;
    }
}
