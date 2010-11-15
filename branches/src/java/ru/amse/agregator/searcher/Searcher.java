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

    public static void setIndexDir(File iDir) {
        try {
            if (!iDir.exists()) {
                throw new FileNotFoundException("Directory " + iDir.getAbsolutePath() + " is not exists");
            }
            INDEX_DIR = iDir;
        } catch (Exception e) {
            System.out.println("Exception! Message " + e.getMessage());            
        }
    }

    public static ArrayList<DBWrapper> search(UserQuery query) {
        try {
            return search(INDEX_DIR, query);
        } catch (Exception e) {
            System.out.println("Exception! Message: " + e.getMessage());
            return null;
        }
    }

    private static ArrayList<DBWrapper> search(File indexDir, UserQuery query) throws IOException, ParseException {
        return search(indexDir, query.getQueryExpression());
        // todo Нужно бужет добавить реализацию для поиска с метками
    }

    private static ArrayList<DBWrapper> search(File indexDir, String q) throws IOException, ParseException {
        String fieldForSearch = "name";
        Directory fsDirectory = new NIOFSDirectory(indexDir);
        IndexSearcher is = new IndexSearcher(fsDirectory);
        QueryParser qParser = new QueryParser(Version.LUCENE_30,
                                  fieldForSearch,
                                  new StandardAnalyzer(Version.LUCENE_30));
        Query query = qParser.parse(q);

        TopFieldCollector tfc = TopFieldCollector.create(Sort.RELEVANCE, 20, true, true, true, false);
        is.search(query, tfc);
        TopDocs docs = tfc.topDocs();

        System.out.println(docs.getMaxScore());
        ScoreDoc[] sDocs = docs.scoreDocs;
        ArrayList<DBWrapper> list = new ArrayList<DBWrapper>();
        for (ScoreDoc currentScoreDoc : sDocs) {
            DBWrapper wrapper = new DBWrapper();
            wrapper.setType(DBWrapper.TYPE_CITY);
            wrapper.setName(is.doc(currentScoreDoc.doc).getField("name").stringValue());
            wrapper.setDescription(is.doc(currentScoreDoc.doc).getField("description").stringValue());
            //wrapper.setAddress(is.doc(currentScoreDoc.doc).getField("address").stringValue());
            list.add(wrapper);
        }
        return list;
    }
}
