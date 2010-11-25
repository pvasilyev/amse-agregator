package ru.amse.agregator.utils;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author guest
 */
public final class Tools {

    //method eliminates all the duplicates in the collection
    static public <E extends Object> void eliminateDuplicates(Collection<E> collection) {

        HashSet<E> hashSet = new HashSet<E>();
        hashSet.addAll(collection);
        collection.clear();
        collection.addAll(hashSet);
    }

}
