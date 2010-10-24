package ru.amse.agregator.indexer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import ru.amse.agregator.searcher.Searcher;
import ru.amse.agregator.storage.City;
import ru.amse.agregator.storage.DataBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Oct 23, 2010
 * Time: 5:58:32 PM
 */

public class Indexer {

    private static class DataToIndex {
        public static void writeDataToFile(File dataDir) {
            try {
                //connectToDB();
                ArrayList<City> cites = DataBase.getAllSity(); // функция будет реализована позже от хранилища
                File cityDir = new File(dataDir.getAbsolutePath() + "/City/");

                if (!cityDir.exists()) {
                    if (!cityDir.mkdirs()) {
                        System.out.println("Cann't create subdirectory /City/");
                        return;
                    }
                }
                File current;
                String name = "Рим";
                for (int i = 0; i < cites.size(); ++i) {
                    current = new File(cityDir.getAbsolutePath() + name);// cites.get(i).getName());
                    if (!current.exists()) {
                        current.createNewFile();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error in writeDataToBase");                
            }
        }

        private static void connectToDB() {
            DataBase.connectToMainBase();
            // TODO убрать после того, как появятся данные в базе
            City someCity = new City();
            someCity.setName("Rome");
            someCity.setDescription("wqe ertty ytryertwer wertwertwete tyuerwewq rteryrty rtwert ewrtwertrwey trr");
            someCity.setPhoto("RomeInPhoto");
            DataBase.add(someCity);
            // TODO до этого места
        }
    }

    public static void makeIndex(File indexDir, File dataDir) {
        cleanDirectory(indexDir);
        DataToIndex.writeDataToFile(dataDir);
        index(indexDir, dataDir);
    }

     private static void cleanDirectory(File indexDir) {
        File[] files = indexDir.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (!files[i].isDirectory()) {
                files[i].delete();
            } else {
                cleanDirectory(files[i]);
                files[i].delete();
            }
        }
    }
    
    private static void index(File indexDir, File dataDir) {
        try {
            Searcher.setIndexDir(indexDir);                                
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

            writer.addDocument(doc);
        } catch (IOException e) {
            System.out.println("Error in indexFile");
        }
    }
}
