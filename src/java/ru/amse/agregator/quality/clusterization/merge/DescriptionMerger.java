package ru.amse.agregator.quality.clusterization.merge;

import java.util.ArrayList;
import java.util.List;
import ru.amse.agregator.quality.clusterization.clusterstorage.Cluster;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.UniqueId;

public class DescriptionMerger extends AttributeMerger {

    // parameter that adjusts how similar descriptions should be to be merged in one
    static final double DEFAULT_MIN_SIMILARITY = 0.5;
    
    final double minSimilarity;
    private DescriptionFingerprinter fingerprinter;

    public DescriptionMerger(DescriptionFingerprinter fingerprinter, double minSimilarity) {
        assert(fingerprinter != null);
        this.fingerprinter = fingerprinter;
        this.minSimilarity = minSimilarity;
    }

    public DescriptionMerger(DescriptionFingerprinter fingerprinter) {
        this(fingerprinter, DEFAULT_MIN_SIMILARITY);
    }

    @Override
    public void mergeAttributes
            (final String attributeName, final Cluster cluster, DBWrapper resultingObject) {
        //form a list of all descriptions for this cluster
        List<String> descriptionList = new ArrayList<String>();

        for (UniqueId id : cluster.getObjectList()) {
            DBWrapper obj = Database.getByUniqueId(id);

            ArrayList<String> descriptions = obj.getDescriptionArray();
            if (descriptions != null) {
                descriptionList.addAll(obj.getDescriptionArray());
            }
        }

        // use fingerprinter to create prints for all descriptions
        List<Fingerprint> fingerprintList = new ArrayList<Fingerprint>();
        // this list represents indexes from descriptionList
        // we'll delete those of similar descriptions and add others
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i != descriptionList.size(); ++i) {
            String description = descriptionList.get(i);
            fingerprintList.add(fingerprinter.getTextFingerprint(description));
            indexes.add(i);
        }

        assert(fingerprintList.size() == descriptionList.size());
        assert(indexes.size() == fingerprintList.size());

        for (int i = 0; i != fingerprintList.size(); ++i) {
            for (int j = 0; j != fingerprintList.size(); ++j) {
                if (j <= i) {
                    continue;
                }

                //compare fingerprints
                Fingerprint fingerprintI = fingerprintList.get(i);
                Fingerprint fingerprintJ = fingerprintList.get(j);
                if (Fingerprint.distance(fingerprintI, fingerprintJ) < minSimilarity) {
                    //output similar descriptions
//                    System.out.println("Dist: " + Fingerprint.distance(fingerprintI, fingerprintJ));
//                    System.out.println(descriptionList.get(i));
//                    System.out.println(fingerprintI);
//                    System.out.println(descriptionList.get(j));
//                    System.out.println(fingerprintJ);

                    //discard one of the descriptions
                    String descriptionI = descriptionList.get(i);
                    String descriptionJ = descriptionList.get(j);
                    if (descriptionI.length() > descriptionJ.length()) {
                        // discard jth description
                        indexes.remove(new Integer(j));
                    } else {
                        // discard ith description
                        indexes.remove(new Integer(i));
                    }
                }
            }
        }

        //now indexes contain indexes from descriptionList we want to add to resulting object
        ArrayList<String> resultingList = new ArrayList<String>();
        for (int index : indexes) {
            resultingList.add(descriptionList.get(index));
        }

        resultingObject.setDescriptionArray(resultingList);

    }

}
