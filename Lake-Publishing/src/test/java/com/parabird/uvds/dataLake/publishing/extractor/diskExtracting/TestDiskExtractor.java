package com.parabird.uvds.dataLake.publishing.extractor.diskExtracting;

import com.parabird.uvds.common.enums.MediaType;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceFile;
import com.parabird.uvds.dataLake.publishing.extractor.diskExtracting.sourceStructure.DiskSourceMetaData;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestDiskExtractor {

    private static Logger logger = LoggerFactory.getLogger(TestDiskExtractor.class);

    @Test
    public void testExtractSourceMedia() throws IOException {
        DiskSourceFile sourceFile = DiskExtractor.extractSourceMedia("src/test/resources/test.jpg", MediaType.IMAGE, null);

        logger.info(sourceFile.toString());
    }

    @Test
    public void testExtractSourceMetaData() throws IOException {
        DiskSourceMetaData sourceMetaData = DiskExtractor.extractSourceMetaData("src/test/resources/testCSV1.csv", MediaType.TEXT_CSV);

        logger.info(sourceMetaData.toString());
    }
}
