package ru.amse.agregator.indexer;

import org.apache.lucene.document.*;
import org.apache.lucene.store.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.Version;

import ru.amse.agregator.storage.*;
import ru.amse.agregator.utils.ToolsForWorkWithFiles;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Oct 23, 2010
 * Time: 5:58:32 PM
 */

public class OriginalIndexer {
    protected static int countIndexedFiles;

    public static void makeNewIndex(File indexDir) throws IOException {
        ToolsForWorkWithFiles.cleanDirectory(indexDir);
        addToIndex(indexDir);
    }

    public static void addToIndex(File indexDir) throws IOException {
        if (!indexDir.exists()) {
            if (!indexDir.mkdirs()) {
                throw new IOException("Cann't create directory " + indexDir.getCanonicalPath());
            }
        }
        countIndexedFiles = 0;
        index(indexDir);
    }

    public static int getCountIndexedFiles() {
        return countIndexedFiles;
    }

    protected static void setInfoAboutIndexToFile() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
    }

    protected static void index(File indexDir) throws IOException {
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

    protected static void IndexAllObjects(IndexWriter writer) throws IOException {
        connectToDB(); // необходима запущенная на компьютере база
        ArrayList<DBWrapper> allObjects = Database.getAllDBObjects();
        for (DBWrapper currentObject : allObjects) {
            indexObject(writer, currentObject);
        }
    }

    protected static void connectToDB() {
        Database.connectToMainBase();
    }

    protected static void indexObject(IndexWriter writer, DBWrapper object) throws IOException {
        //System.out.println("\nIndexed object " + object.getName());
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

        if (documentForCurrentObject != null) {
            writer.addDocument(documentForCurrentObject);
            countIndexedFiles++;
        } else {
            //System.out.println("Error in object. Object isn't indexed.");    // Выводится, если объект не может быть
        }
    }

    protected static Document getDocumentForCurrentCity(DBWrapper city) {
        IndexDocument indexDocument = new IndexDocument(city);

        indexDocument.addTypicalFields();

        return indexDocument.getDocument();
    }

    protected static Document getDocumentForCurrentArchitecturalAttraction(DBWrapper attraction) {
        IndexDocument indexDocument = new IndexDocument(attraction);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();
        indexDocument.addObjectArchitect();

        return indexDocument.getDocument();
    }

    protected static Document getDocumentForCurrentNaturalAttraction(DBWrapper attraction) {
        IndexDocument indexDocument = new IndexDocument(attraction);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();

        return indexDocument.getDocument();
    }

    protected static Document getDocumentForCurrentHotel(DBWrapper hotel) {
        IndexDocument indexDocument = new IndexDocument(hotel);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();

        return indexDocument.getDocument();
    }

    protected static Document getDocumentForCurrentCafe(DBWrapper cafe) {
        IndexDocument indexDocument = new IndexDocument(cafe);

        indexDocument.addTypicalFields();
        indexDocument.addObjectCity();
        indexDocument.addObjectMusic();

        return indexDocument.getDocument();
    }

    protected static class IndexDocument {
        private final String FIELD_TYPE = "type";
        private final String FIELD_ID = "id";
        private final String FIELD_NAME = "name";
        private final String FIELD_DESCRIPTIONS = "descriptions";
        private final String FIELD_DESCRIPTION = "description";
        private final String FIELD_KEYWORDS = "keywords";
        private final String FIELD_CITY = "city";
        private final String FIELD_MUSIC = "music";
        private final String FIELD_ARCHITECT = "architect";

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
            addObjectType();
            addObjectId();
            addObjectName();
            addObjectDescriptions();
            addObjectDescription();
            addObjectKeyWord();
        }

        protected void addObjectType() {
            String objectType = object.getType();
            addField(FIELD_TYPE, objectType);
        }

        public void addObjectId() {
            String id = "" + object.getId();
            addField(FIELD_ID, id);
        }

        protected void addField(String field, String value) {
            if (value == null || value.isEmpty()) {
                return;
            }
            document.add(new Field(field, value, fieldStore, fieldIndex));
            //System.out.println(value);  // если нужен вывод на консоль индексируемого значения
        }

        public void addObjectName() {
            String objectName = object.getName();
            addField(FIELD_NAME, objectName);
        }

        public void addObjectDescriptions() {
            ArrayList<String> objectDescriptions = object.getDescriptionArray();
            if (objectDescriptions != null && !objectDescriptions.isEmpty()) {
                StringBuffer objectDescription = new StringBuffer();
                for (String description: objectDescriptions) {
                    objectDescription.append(" ");
                    objectDescription.append(description);
                }
                addField(FIELD_DESCRIPTIONS, objectDescription.toString());
            }
        }

        private void addObjectDescription() {
            ArrayList<String> objectDescriptions = object.getDescriptionArray();
            if (objectDescriptions != null && !objectDescriptions.isEmpty()) {
                String objectDescription = objectDescriptions.get(0);
                addField(FIELD_DESCRIPTION, objectDescription);
            }
        }

        protected void deleteLastChar(StringBuffer stringBuffer) {
            if (stringBuffer.length() > 0) {
                stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length() - 1);
            }
        }

        public void addObjectKeyWord() {
            ArrayList<String> keyWords = object.getKeyWordsArray();
            if (keyWords == null) {
                return;
            }
            
            StringBuffer allKeyWords = new StringBuffer();
            for (String currentWord : keyWords) {
                allKeyWords.append(currentWord);
                allKeyWords.append(", ");
            }
            deleteLastChar(allKeyWords);
            addField(FIELD_KEYWORDS, allKeyWords.toString());
        }

        public void addObjectCity() {
            String cityId = "" + object.getCityId();
            addField(FIELD_CITY, cityId);
        }

        public void addObjectArchitect() {
            String architect = object.getArchitect();
            addField(FIELD_ARCHITECT, architect);
        }

        public void addObjectMusic() {
            String music = object.getMusic();
            addField(FIELD_MUSIC, music);
        }
    }
}
