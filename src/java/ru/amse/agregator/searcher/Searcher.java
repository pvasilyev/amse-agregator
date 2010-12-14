package ru.amse.agregator.searcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.bson.types.ObjectId;
import ru.amse.agregator.storage.DBWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        query = addTilda(query);
        try {
            return search(INDEX_DIR, query, "name");
        } catch (Exception e) {
            System.out.println("Exception! Message: " + e.getMessage());
            return null;
        }
    }

    private static UserQuery addTilda(UserQuery query) {
        StringBuffer expr = new StringBuffer(query.getQueryExpression().replaceAll("[~-]", " "));
        Pattern word = Pattern.compile("[a-zA-Zа-яА-Я]+");
        Matcher matcher = word.matcher(expr);
        StringBuffer endExpr = new StringBuffer();
        int lastEnd = 0;

        while (matcher.find()) {
            endExpr.append(expr.substring(lastEnd, matcher.end() - matcher.groupCount()));
            endExpr.append("~");
            lastEnd = matcher.end();
        }

        query.setQueryExpression(endExpr.toString());
        return query;
    }

    private static ArrayList<DBWrapper> search(File indexDir, UserQuery q, String fieldForSearch) throws IOException, ParseException {
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
        
        ArrayList<DBWrapper> listOfWrapper = new ArrayList<DBWrapper>();
        ArrayList<String> labels = q.getLabels();
        for (ScoreDoc currentScoreDoc : sDocs) {
            String type = is.doc(currentScoreDoc.doc).getField("type").stringValue();
            // [] => f
            // [t1, t2] => t && f (if contains) => add
            //                  t && t (if not contains) => !add
            if (labels.size() != 0 && !labels.contains(type)) {
                continue;
            }
            DBWrapper wrapper = new DBWrapper();
            wrapper.setId(new ObjectId(is.doc(currentScoreDoc.doc).getField("id").stringValue()));
            if (is.doc(currentScoreDoc.doc).getField("name") != null) {
                wrapper.setName(is.doc(currentScoreDoc.doc).getField("name").stringValue());
            }
            if (is.doc(currentScoreDoc.doc).getField("type") != null) {
                wrapper.setType(is.doc(currentScoreDoc.doc).getField("type").stringValue());

            }
            if (is.doc(currentScoreDoc.doc).getField("description") != null) {
                wrapper.setDescription(is.doc(currentScoreDoc.doc).getField("description").stringValue());
            }
            listOfWrapper.add(wrapper);
        }
        return listOfWrapper;
    }
}
