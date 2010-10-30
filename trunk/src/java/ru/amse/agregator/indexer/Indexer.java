package ru.amse.agregator.indexer;

import org.apache.lucene.document.*;
import org.apache.lucene.store.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.Version;

import ru.amse.agregator.storage.*;
import ru.amse.agregator.utils.ToolsForWorkWithFiles;

import java.io.*;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Oct 23, 2010
 * Time: 5:58:32 PM
 */

public class Indexer {
    public static void makeNewIndex(File indexDir) throws Exception {
        ToolsForWorkWithFiles.cleanDirectory(indexDir);
        addToIndex(indexDir);
    }

    public static void addToIndex(File indexDir) throws Exception {
        index(indexDir);
    }

    private static void index(File indexDir) throws Exception {
        Directory indexDirectory = new NIOFSDirectory(indexDir);
        Version useVersion = Version.LUCENE_30;
        IndexWriter writer = new IndexWriter(indexDirectory,
                new StandardAnalyzer(useVersion),
                IndexWriter.MaxFieldLength.LIMITED);
        // writer.setUseCompoundFile(false);  - todo разобраться для чего
        IndexAllObjects(writer);
        writer.optimize();
        writer.close();
    }

    private static void IndexAllObjects(IndexWriter writer) throws Exception {
        connectToDB(); // Не забыть запустить базу на компьютере!
        indexAllCities(writer);
        //indexAndWriteToFileAllCityElements(writer, dataDir);
        //indexAndWriteToFileAllAttractions(writer, dataDir);
    }

    private static void connectToDB() {
        //DataBase.connectToDirtyBase();
    }

    private static void indexAllCities(IndexWriter writer) throws Exception {
        ArrayList<City> cites = DataBase.getAllCities();
        //String currentDirectoryName = getCurrentDirectoryForCites(dataDir);
        for (City currentCity : cites) {
            Document currentDoc = getDocumentForCurrentCity(currentCity);
            System.out.println("Indexed city with id: " + currentCity.getId());
            writer.addDocument(currentDoc);
            //writeCityToFile(currentCity, currentDirectoryName); решили не использовать
        }
    }

    private static String getCurrentDirectoryForCites(File dataDir) {
        return dataDir.getAbsolutePath() + "/city/";
    }

    private static Document getDocumentForCurrentCity(City city) {
        Document doc = new Document();

        //String fileName = directoryName + city.getId();  не используется
        //doc.add(new Field("filename", fileName, Field.Store.YES, Field.Index.ANALYZED));
        String cityId = "" + city.getId();
        doc.add(new Field("id", "1"/*cityId*/, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("name", "cityname"/*city.getName()*/, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("description", "cityname is bla-bla-bla. Text for test"/*city.getDescription()*/, Field.Store.YES, Field.Index.ANALYZED));
        //doc.add(new Field("keywords", fileName, Field.Store.YES, Field.Index.ANALYZED));

        return doc;
    }

    private static void writeCityToFile(City city, String directoryName) throws Exception {
        // todo разобраться, что лучше передавать - файл или строку, проверить ошибки
        File file = new File(directoryName + "/" + city.getId());
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
        out.writeObject(city);
        out.flush();
        out.close();
    }

    private static void indexAndWriteToFileAllCityElements(IndexWriter writer, File dataDir) {
        // todo Реализовать
    }

    private static void indexAndWriteToFileAllAttractions(IndexWriter writer, File dataDir) {
        // todo Реализовать
    }

    private static void indexDirectory(IndexWriter writer, File dataDir) throws IOException {
        File[] filesInThisDirectory = dataDir.listFiles();

        for (File currentFile : filesInThisDirectory) {
            if (currentFile.isDirectory()) {
                indexDirectory(writer, currentFile);
            } else {
                indexFile(writer, currentFile);
            }
        }
    }

    private static void indexFile(IndexWriter writer, File f) throws IOException {
        System.out.println("Indexing " + f.getCanonicalPath());
        Document doc = new Document();

        doc.add(new Field("contents", new FileReader(f)));
        doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES, Field.Index.ANALYZED));

        writer.addDocument(doc);
    }
}
