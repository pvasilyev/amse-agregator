package ru.amse.agregator.searcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.bson.types.ObjectId;

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

    public static ArrayList<ObjectId> search(UserQuery query) {
        try {
            return search(INDEX_DIR, query, "name");
        } catch (Exception e) {
            System.out.println("Exception! Message: " + e.getMessage());
            return null;
        }
    }

    private static ArrayList<ObjectId> search(File indexDir, UserQuery q, String fieldForSearch) throws IOException, ParseException {
        Directory fsDirectory = new NIOFSDirectory(indexDir);
        IndexSearcher is = new IndexSearcher(fsDirectory);
        QueryParser qParser = new QueryParser(Version.LUCENE_30,
                                  fieldForSearch,
                                  new StandardAnalyzer(Version.LUCENE_30));
        Query query = qParser.parse(q.getQueryExpression());

        TopFieldCollector tfc = TopFieldCollector.create(Sort.RELEVANCE, 20, true, true, true, false);
        is.search(query, tfc);
        TopDocs docs = tfc.topDocs();

        System.out.println(docs.getMaxScore());
        ScoreDoc[] sDocs = docs.scoreDocs;
        ArrayList<ObjectId> listOfId = new ArrayList<ObjectId>();
        ArrayList<String> labels = q.getLabels();
        for (ScoreDoc currentScoreDoc : sDocs) {
            ObjectId id = new ObjectId(is.doc(currentScoreDoc.doc).getField("id").stringValue());
            String type = is.doc(currentScoreDoc.doc).getField("type").stringValue();
            // [] => f
            // [t1, t2] => t && f (if contains) => add
            //                  t && t (if not contains) => !add
            if (labels.size() != 0 && !labels.contains(type)) {
                continue;
            }
            listOfId.add(id);
        }
        return listOfId;
    }
}
