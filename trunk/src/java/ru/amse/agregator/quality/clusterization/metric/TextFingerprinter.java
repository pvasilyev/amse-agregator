package ru.amse.agregator.quality.clusterization.metric;

/**
 *
 * @author pavel
 */

import java.util.Map;
import java.util.TreeMap;

final public class TextFingerprinter {
    
    Map<String, Integer> frequencyDictionary = null;

    public TextFingerprinter() {
        resetDirctionary();
    }

    // sets dictionary to empty dictionarys
    public void resetDirctionary() {
        frequencyDictionary = new TreeMap<String, Integer>();
    }

    // add an example to fingerprinter current dictionary
    public void addTextExampleToDictionary(String textExample)  {
        //
    }

    // gets a finger for this text with the current dictionary
    public Fingerprint getTextFingerprint(String text) {

        return null;
    }
}
