package ru.amse.agregator.quality.clusterization.metric;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;
import ru.amse.agregator.utils.Tools;

/**
 *
 * @author pavel
 *
 * Fingerprint represents a part of the text's frequency dictionary used to compare
 * pieces of text. This helps faster computation of metric between object's
 * descriptions and to determine near-duplicate descriptions during merge process.
 *
 * Current implementation basically takes the middle part of the text frequency list
 * 
 */
final public class Fingerprint {

    // these values indicate what part of vocabulary is actually considered
    // "the middle"
    private static double middleStart = 0.4;
    private static double middleLength = 0.2;

    // fingerprint representation
    private Set<String> words;

    // constructs a fingerprint for textList with dictionaty List
    Fingerprint(FrequencyList list) {

        words = new TreeSet<String>();
        
        Set<String> vocabulary = list.getSortedVocabulary();
        int vocabularySize = vocabulary.size();
        int lookedThroughCount = 0;
        //get just the part of the vocabulary
        //@todo can be optimized
        for (String word : vocabulary) {
            ++lookedThroughCount;
            // the word is in the middle of the dictionary
            if ((vocabularySize * middleStart <= lookedThroughCount) &&
                (vocabularySize * (middleStart + middleLength) <= lookedThroughCount)) {
                words.add(word);
            }
        }
    }

    public Fingerprint(String text) {
        this(new FrequencyList(text));
    }

    /*
     * returns a value between 0.0 and 1.0 representing distance
     * between two fingerprints. 0.0 represents equal fingerprints
     */
    public static double distance(Fingerprint fingerprint1, Fingerprint fingerprint2) {
        Set<String> allWords  = new TreeSet<String>();
        allWords.addAll(fingerprint1.words);
        allWords.addAll(fingerprint2.words);
        double distance = (double)(fingerprint1.words.size() + fingerprint2.words.size() - allWords.size())
                / (double)Math.abs(fingerprint1.words.size() - fingerprint2.words.size());

        return distance;
    }

}
