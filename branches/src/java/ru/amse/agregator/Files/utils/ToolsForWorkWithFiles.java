package ru.amse.agregator.utils;

import java.io.File;
import java.io.IOException;

/*
 * Author: Bondarev Timofey
 * Date: Oct 25, 2010
 * Time: 2:12:51 AM
 */

public class ToolsForWorkWithFiles {
    public static void cleanDirectory(File directory) {
        if (!directory.exists()) {
            return;
        }
        File[] filesInCurrentDirectory = directory.listFiles();

        try {
            for (File currentFile: filesInCurrentDirectory) {
                if (currentFile.isDirectory()) {
                    cleanDirectory(currentFile);
                }
                deleteFileOrEmptyDirectory(currentFile);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteFileOrEmptyDirectory(File deletionFileOrEmptyDirectory) throws IOException {
        boolean deleted = deletionFileOrEmptyDirectory.delete();
        if (!deleted) {
            throw new IOException("Cann't delete file " + deletionFileOrEmptyDirectory.getAbsolutePath());
        }
    }
}
