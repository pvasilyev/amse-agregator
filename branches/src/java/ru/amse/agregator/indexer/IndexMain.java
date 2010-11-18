package ru.amse.agregator.indexer;

import java.io.File;
import java.util.Calendar;

/*
 * Author: Bondarev Timofey
 * Date: Nov 4, 2010
 * Time: 8:06:41 PM
 */

public class IndexMain {
    public static void main(String[] args) {
        try {
        // todo Разобрать вывод в XML файла-состояния.
            Calendar time = Calendar.getInstance();
            long beginIndexTime = time.getTimeInMillis();
            File indexDir = null;
            boolean makeNewIndex = true;
            boolean errorInArgs = false;
            if (args.length <= 1) {
                indexDir = new File("index");
                if (args.length == 1 && args[0].equals("-n")) {
                    makeNewIndex = true;
                } else if (args.length == 1 && args[0].equals("-a")) {
                    makeNewIndex = false;
                } else {
                    errorInArgs = true;
                }
            } else if (args.length == 2) {
                if (args[0].equals("--dir")) {
                    indexDir = new File(args[1]);
                    makeNewIndex = true;
                } else {
                    errorInArgs = true;
                }
            } else if (args.length == 3) {
                if (args[1].equals("--dir")) {
                    if (args[0].equals("-n")) {
                        makeNewIndex = true;
                    } else if (args[0].equals("-a")) {
                        makeNewIndex = false;
                    } else {
                        errorInArgs = true;
                    }
                    indexDir = new File(args[2]);
                } else if (args[0].equals("--dir")) {
                    if (args[2].equals("-n")) {
                        makeNewIndex = true;
                    } else if (args[2].equals("-a")) {
                        makeNewIndex = false;
                    } else {
                        errorInArgs = true;
                    }
                    indexDir = new File(args[1]);
                } else {
                    errorInArgs = true;
                }

            } else {
                errorInArgs = true;
            }
            if (errorInArgs) {
                System.out.println("Usage: IndexMain [-a|-n] [--dir <indexDirectory>]");
                return;
            } else {
                if (makeNewIndex)  {
                    Indexer.makeNewIndex(indexDir);
                } else {
                    Indexer.addToIndex(indexDir);
                }
            }

            time = Calendar.getInstance();
            long endIndexTime = time.getTimeInMillis();
            long timeForIndexing = endIndexTime - beginIndexTime;
            System.out.println(Indexer.countIndexedFiles + " files indexed by " + timeForIndexing + " milli seconds.");
        } catch (Exception e) {
            System.out.println("Error in function makeNewIndex. Message: " + e.getMessage());            
        }
    }
}
