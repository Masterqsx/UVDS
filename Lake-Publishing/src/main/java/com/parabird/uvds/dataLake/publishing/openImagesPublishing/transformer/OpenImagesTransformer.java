package com.parabird.uvds.dataLake.publishing.openImagesPublishing.transformer;

import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceFile;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceMetaData;
import com.parabird.uvds.dataLake.publishing.transformer.ITransformer;
import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OpenImagesDatasetTransformer implements ITransformer {

    /** Transformer is data set specific
    *   Given File Path "*file.txt"
    *   FileName is "file.txt"
    *   SourceUid is "file" which is specific for Open Images data set
    **/
    // TODO be able to fill image related fields from DiskSourceFile
    public static ImageMedia transformSourceImageMediaOpenImages(DiskSourceFile sourceFile, String SourceName, String SourceDesc) {
        ImageMedia trasformed = ImageMedia.newImageMediaBuilder()
            .setInsertTime(new Timestamp(new Date().getTime()))
            .setFilePath(sourceFile.getFileAbsolutePath())
            .setFileName(FilenameUtils.getName(sourceFile.getFileAbsolutePath()))
            .setSourceUid(FilenameUtils.getBaseName(sourceFile.getFileAbsolutePath()))
            .setTags(sourceFile.getTags())
            .setSource(Source.newSourceBuilder()
                    .setSourceName(SourceName)
                    .setDescription(SourceDesc)
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
                .setTags(new HashMap<>())
                .build();

            for (int i = 1; i < line.size(); i++) {
                cur.getTags().put(sourceMetaData.getSchema().get(i), line.get(i));
            }
            transformed.add(cur);
        }

        return transformed;
    }
}