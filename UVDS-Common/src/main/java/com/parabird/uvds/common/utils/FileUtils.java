package com.parabird.uvds.common.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileUtils {

    public static File loadFile(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw  new IOException("Input file path is not valid!");
        }

        return file;
    }

    public static Map<String, String> loadFileAndMetaInfo(File file) throws IOException {
        Map<String, String> tags = new HashMap<>();

        tags.put("fileAbsolutePath", file.getCanonicalPath());

        tags.put("fileSize", Long.toString(file.length()));

        tags.put("fileExtension", FilenameUtils.getExtension(file.getCanonicalPath()));

        return tags;
    }

    public static Map<String, String> loadImageMetaInfo(File file) throws IOException{
        Map<String, String> tags = new HashMap<>();

        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException e) {
            throw new IOException("The meta data of image file can not be parsed!");
        }
        for ( Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                tags.put(directory.getName() + "_" + tag.getTagName(), tag.getDescription());
            }
        }

        return tags;
    }

    /** The delimiter here is regex string
    * Assume the first line is schema definition
    * Each line is a tuple
    * If # of column > schema length, it will be trimmed to schema length
    * If # of column < schema length, it will fill with empty string at the end
    */
    public static List<List<String>> loadCSV(File file, String delimiter) throws FileNotFoundException {
        List<List<String>> lines = new ArrayList<>();
        int schemaLength = -1;

        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String curLine = scan.nextLine();

            if (curLine.length() == 0) continue;

            List<String> values = new ArrayList<>(Arrays.asList(curLine.split(delimiter)));

            if (schemaLength > 0) {
                if (values.size() > schemaLength) {
                    values = values.subList(0, schemaLength);
                } else {
                    for (int i = values.size(); i < schemaLength; i++) {
                        values.add("");
                    }
                }
            }

            lines.add(values);

            if (schemaLength < 0) schemaLength = values.size();
        }
        return lines;
    }
}
