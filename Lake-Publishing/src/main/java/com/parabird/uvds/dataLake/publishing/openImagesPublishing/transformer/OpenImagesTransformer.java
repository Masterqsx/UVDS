package com.parabird.uvds.dataLake.publishing.openImagesPublishing.transformer;

import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceFile;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceMetaData;
import com.parabird.uvds.dataLake.publishing.transformer.ITransformer;
import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.util.*;

public class OpenImagesTransformer implements ITransformer {

    // TODO validate uid makes from sourceUid and sourceName

    private static final String SOURCE_NAME = "OpenImagesV4";

    private static final String SOURCE_DESC = "Open Images V4 at https://storage.googleapis.com/openimages/web/download.html";
    /** Transformer is data set specific
    *   Given File Path "*file.txt"
    *   FileName is "file.txt"
    *   SourceUid is "file" which is specific for Open Images data set
    **/
    // TODO be able to fill image related fields from DiskSourceFile
    public static ImageMedia transformSourceImageMediaOpenImages(DiskSourceFile sourceFile) {
        ImageMedia trasformed = ImageMedia.newImageMediaBuilder()
            .setInsertTime(new Timestamp(new Date().getTime()))
            .setFilePath(sourceFile.getFileAbsolutePath())
            .setFileName(FilenameUtils.getName(sourceFile.getFileAbsolutePath()))
            .setSourceUid(FilenameUtils.getBaseName(sourceFile.getFileAbsolutePath()))
            .setUid(FilenameUtils.getBaseName(sourceFile.getFileAbsolutePath()) + SOURCE_NAME)
            .setTags(sourceFile.getTags())
            .setSource(Source.newSourceBuilder()
                    .setSourceName(SOURCE_NAME)
                    .setDescription(SOURCE_DESC)
                    .build()
            )
            .build();

        return trasformed;
    }

    public static List<ImageMedia> transformSourceMetaDataOpenImages(DiskSourceMetaData sourceMetaData) {
        List<ImageMedia> transformed = new ArrayList<>();
        for (List<String> line : sourceMetaData.getRecords()) {
            ImageMedia cur = ImageMedia.newImageMediaBuilder()
                .setSourceUid(line.get(0))
                .setUid(line.get(0) + SOURCE_NAME)
                .setTags(new HashMap<>())
                .setSource(Source.newSourceBuilder()
                        .setSourceName(SOURCE_NAME)
                        .setDescription(SOURCE_DESC)
                        .build()
                )
                .build();

            for (int i = 1; i < line.size(); i++) {
                cur.getTags().put(sourceMetaData.getSchema().get(i), line.get(i));
            }
            transformed.add(cur);
        }

        return transformed;
    }

    public static ImageMedia transformSourceMetaDataOpenImages(List<Map.Entry<String, String>> line) {
        ImageMedia cur = ImageMedia.newImageMediaBuilder()
            .setSourceUid(line.get(0).getValue())
            .setUid(line.get(0).getValue() + SOURCE_NAME)
            .setTags(new HashMap<>())
            .setSource(Source.newSourceBuilder()
                .setSourceName(SOURCE_NAME)
                .setDescription(SOURCE_DESC)
                .build()
            )
            .build();

        for (int i = 1; i < line.size(); i++) {
            cur.getTags().put(line.get(i).getKey(), line.get(i).getValue());
        }

        return cur;
    }
}
