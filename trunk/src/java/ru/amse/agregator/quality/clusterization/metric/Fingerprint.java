package ru.amse.agregator.quality.clusterization.metric;

import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

/**
 *
 * @author pavel
 *
 * Fingerprint represents a part of the text's frequency dictionary used to compare
 * pieces of text. This helps faster computation of metric between object's
 * descriptions and to determine near-duplicate descriptions during merge process.
 *
 */
final public class Fingerprint {

    // fingerprint representation
    private Map<String, Integer> values  = new TreeMap<String, Integer>();

    // constructs a fingerprint for textList with dictionaty List
    Fingerprint(FrequencyList textList, FrequencyList dictionary) {

        Set<String> vocabulary = textList.getVocabulary();
        for (String word : vocabulary) {
            values.put(word, textList.getCount(word));
        }
    }

    public Fingerprint(String text, FrequencyList dictionary) {
        this(new FrequencyList(text), dictionary);
    }

    /*
     * returns a value between 0.0 and 1.0 representing distance
     * between two fingerprints. 0.0 represents equal fingerprints
     */
    public static double compare(Fingerprint firstFingerprint, Fingerprint secondFingerprint) {
        return 0.0;
    }

}
