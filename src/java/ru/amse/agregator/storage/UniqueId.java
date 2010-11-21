package ru.amse.agregator.storage;

import org.bson.types.ObjectId;

/**
 *
 * @author pavel
 *
 * An id that adresses the single object in the whole database
 */
final public class UniqueId {

    final private ObjectId id;
    private String databaseName;
    final private String collectionName;

    //constructs an id for this object
    public UniqueId(DBWrapper object, String databaseName) {

        this.id = object.getId();
        this.collectionName = object.getType();
        this.databaseName = databaseName;

    }

    ObjectId getId() {
        return id;
    }

    String getCollectionName() {
        return collectionName;
    }

    String getDatabaseName() {
        return databaseName;
    }

}
