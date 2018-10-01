package com.parabird.uvds.dataLake.publishing.openImagesPublishing.transformer;

import com.parabird.uvds.common.enums.MediaType;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.DiskExtractor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class TestOpenImagesTransformer {

    private static Logger logger = LoggerFactory.getLogger(TestOpenImagesTransformer.class);

    @Test
    public void testTransformSourceImageMediaOpenImages() throws IOException {
        ImageMedia media = OpenImagesTransformer.transformSourceImageMediaOpenImages(
                DiskExtractor.extractSourceMedia("src/test/resources/test.jpg", MediaType.IMAGE, null));

        logger.info(media.toString());
    }

    @Test
    public void testTransformSourceMetaDataOpenImages() throws IOException {
        List<ImageMedia> medias = OpenImagesTransformer.transformSourceMetaDataOpenImages(
            DiskExtractor.extractSourceMetaData("src/test/resources/testCSV1.csv", MediaType.TEXT_CSV)
        );

        for (ImageMedia media : medias) logger.info(media.toString());
    }

}
