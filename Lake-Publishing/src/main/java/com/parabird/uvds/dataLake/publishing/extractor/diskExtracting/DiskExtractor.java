package com.parabird.uvds.dataLake.publishing.diskPublishing;

import com.parabird.uvds.common.enums.MediaType;
import com.parabird.uvds.common.utils.FileUtils;
import com.parabird.uvds.dataLake.publishing.diskPublishing.sourceStructure.DiskSourceFile;
import com.parabird.uvds.dataLake.publishing.diskPublishing.sourceStructure.DiskSourceMetaData;
import com.parabird.uvds.dataLake.publishing.extractor.IExtractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiskExtractor implements IExtractor {

    public static DiskSourceFile extractSourceMedia(String filePath, MediaType mediaType, Map<String, String> customTags) throws IOException {
        File file = FileUtils.loadFile(filePath);

        Map<String, String> fileTags = FileUtils.loadFileAndMetaInfo(file);

        /** customTags can be provided by caller itself*/
        if (customTags != null) fileTags.putAll(customTags);

        switch(mediaType) {
            /** add image meta info into tags such as resolution */
            case IMAGE:
                Map<String, String> imageTags = FileUtils.loadImageMetaInfo(file);
                if (imageTags != null) {
                    fileTags.putAll(FileUtils.loadImageMetaInfo(file));
                }
                break;

            default:
                break;
        }

        return DiskSourceFile.newDiskSourceFileBuilder()
                .setFileAbsolutePath(fileTags.get(FileUtils.FILE_PATH))
                .setTags(fileTags)
                .build();
    }

    /** This method load Meta data file as a table */
    public static DiskSourceMetaData extractSourceMetaData(String filePath, MediaType mediaType) throws IOException {
        File file = FileUtils.loadFile(filePath);

        Map<String, String> fileTags = FileUtils.loadFileAndMetaInfo(file);

        List<List<String>> lines = null;

        switch (mediaType) {
            /** parse csv file with comma delimiter*/
            case TEXT_CSV:
                lines = FileUtils.loadCSV(file, ",");
                break;

            default:
                break;
        }

        if (lines == null || lines.size() < 1) {
            throw new IOException("The source meta data file can not be parsed!");
        }

        return DiskSourceMetaData.newDiskSourceMetaDataBuilder()
                .setFileAbsolutePath(fileTags.get(FileUtils.FILE_PATH))
                .setTags(fileTags)
                .setSchema(lines.get(0))
                .setRecords(lines.size() > 1 ? lines.subList(1, lines.size()) : new ArrayList<>())
                .build();
    }

}
