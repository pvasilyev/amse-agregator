package ru.amse.agregator.indexer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * Author: Bondarev Timofey
 * Date: Oct 23, 2010
 * Time: 5:58:32 PM
 */

public class Indexer {
    public static void makeIndex(File indexDir, File dataDir) {
        index(indexDir, dataDir);
    }

    private static void index(File indexDir, File dataDir) {
        try {
            Directory indexDirectory = new NIOFSDirectory(indexDir);
            IndexWriter writer = new IndexWriter(indexDirectory,
                                                 new StandardAnalyzer(Version.LUCENE_30),
                                                 IndexWriter.MaxFieldLength.LIMITED);
            writer.setUseCompoundFile(false);
            indexDirectory(writer, dataDir);
            writer.optimize();
            writer.close();
        } catch (IOException e){
            System.out.println("Error in index!");
        }
    }

    private static void indexDirectory(IndexWriter writer, File dataDir) {
        File[] files = dataDir.listFiles();

        //for (File f : files)
        for (int i = 0; i < files.length; ++i) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(writer, f);
            } else {
                indexFile(writer, f);
            }
        }

    }

    private static void indexFile(IndexWriter writer, File f) {
        try {
            System.out.println("Indexing " + f.getCanonicalPath());
            Document doc = new Document();

            doc.add(new Field("contents", new FileReader(f)));
            doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES, Field.Index.ANALYZED));

            //doc.add(new Field("filename", f.getCanonicalPath()));
            writer.addDocument(doc);
        } catch (IOException e) {
            System.out.println("Error in indexFile");
        }
    }
}
