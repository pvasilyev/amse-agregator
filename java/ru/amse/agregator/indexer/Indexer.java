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
        connectToDB(); // необходима запущенная на компьютере база
        ArrayList<DBWrapper> allObjects = DataBase.getAllDBObjects();
        for (DBWrapper currentObject : allObjects) {
            indexObject(writer, currentObject);
        }
    }

    private static void connectToDB() {
        DataBase.connectToDirtyBase();
    }

    private static void indexObject(IndexWriter writer, DBWrapper object) throws Exception {
        System.out.println(object.getName());
        Document documentForCurrentObject;
        if (object.getType().equals(DBWrapper.TYPE_CITY)) {
            documentForCurrentObject = getDocumentForCurrentCity(object);
        } else if (object.getType().equals(DBWrapper.TYPE_ARCH_ATTRACTION)) {
            documentForCurrentObject = getDocumentForCurrentArchitecturalAttraction(object);
        } else if (object.getType().equals(DBWrapper.TYPE_NATURAL_ATTRACTION)) {
            documentForCurrentObject = getDocumentForCurrentNaturalAttraction(object);
        } else if (object.getType().equals(DBWrapper.TYPE_HOTEL)) {
            documentForCurrentObject = getDocumentForCurrentHotel(object);
        } else if (object.getType().equals(DBWrapper.TYPE_CAFE)) {
            documentForCurrentObject = getDocumentForCurrentCafe(object);           
        } else {
            documentForCurrentObject = null;
        }

        // todo дописать для всех типов
        writer.addDocument(documentForCurrentObject);
    }

    private static Document getDocumentForCurrentCity(DBWrapper city) {
        IndexDocument indexDocument = new IndexDocument(city);

        indexDocument.addTypicalFields();

        return indexDocument.getDocument();
    }

    private static Document getDocumentForCurrentArchitecturalAttraction(DBWrapper attraction) {
        IndexDocument indexDocument = new IndexDocument(attraction);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();
        indexDocument.addObjectDate();
        indexDocument.addObjectArchitect();
        indexDocument.addObjectCost();
        indexDocument.addObjectAddress();

        return indexDocument.getDocument();
    }

    private static Document getDocumentForCurrentNaturalAttraction(DBWrapper attraction) {
        IndexDocument indexDocument = new IndexDocument(attraction);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();

        return indexDocument.getDocument();
    }

    private static Document getDocumentForCurrentHotel(DBWrapper hotel) {
        IndexDocument indexDocument = new IndexDocument(hotel);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();
        indexDocument.addObjectWebsite();
        indexDocument.addObjectRooms();

        return indexDocument.getDocument();
    }

    private static Document getDocumentForCurrentCafe(DBWrapper cafe) {
        IndexDocument indexDocument = new IndexDocument(cafe);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();
        indexDocument.addObjectWebsite();
        indexDocument.addObjectMusic();

        return indexDocument.getDocument();
    }

    private static class IndexDocument {
        private final Document document;
        private final DBWrapper object;
        private final Field.Store fieldStore = Field.Store.YES;
        private final Field.Index fieldIndex = Field.Index.ANALYZED;

        IndexDocument(DBWrapper object) {
            document = new Document();
            this.object = object;
        }

        Document getDocument() {
            return document;
        }

        public void addTypicalFields() {
            addObjectId();
            addObjectName();
            addObjectDescription();
            addObjectCoordinates();
            addObjectPhotos();
            addObjectKeyWord();
        }

        public void addObjectId() {
            String id = "" + object.getId();
            addField("id", id);
        }

        private void addField(String field, String value) {
            document.add(new Field(field, value, fieldStore, fieldIndex));
        }

        public void addObjectName() {
            String objectName = object.getName();
            if (objectName.equals("") || objectName.isEmpty()) {
                return;
            }
            addField("name", objectName);
        }

        public void addObjectDescription() {
            String objectDescription = object.getName();
            if (objectDescription.equals("") || objectDescription.isEmpty()) {
                return;
            }
            addField("description", object.getDescription());
        }

        public void addObjectCoordinates() {

        }

        public void addObjectKeyWord() {

        }

        public void addObjectPhotos() {

        }

        public void addObjectCity() {

        }

        public void addObjectDate() {

        }

        public void addObjectArchitect() {

        }

        public void addObjectCost() {

        }

        public void addObjectAddress() {
            
        }

        public void addObjectWebsite() {

        }

        public void addObjectRooms() {
            
        }

        public void addObjectMusic() {
            
        }
    }
}
