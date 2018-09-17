package com.parabird.uvds.common.utils;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestFileUtils {
    private static Logger logger = LoggerFactory.getLogger(TestFileUtils.class);

    @Test
    public void testLoadFile() throws IOException {
        FileUtils.loadFile("src/test/resources/test.jpg");
    }

    @Test
    public void testLoadFileAndMetaInfo() throws IOException {
        Map<String, String> tags = FileUtils.loadFileAndMetaInfo(FileUtils.loadFile("src/test/resources/test.jpg"));

        for (Map.Entry<String, String> entry : tags.entrySet()) {
            logger.info("[File Meta Info] " + entry.getKey() + " : " + entry.getValue());
        }
    }

    @Test
    public void testLoadImageMetaInfo() throws IOException{
        Map<String, String> tags = FileUtils.loadImageMetaInfo(FileUtils.loadFile("src/test/resources/test.jpg"));

        for (Map.Entry<String, String> entry : tags.entrySet()) {
            logger.info("[File Meta Info] " + entry.getKey() + " : " + entry.getValue());
        }
    }

    @Test
    public void testValidLoadCSV() throws IOException {
        List<List<String>> lines = FileUtils.loadCSV(FileUtils.loadFile("src/test/resources/testCSV1.csv"), ",");

        assertEquals(lines.size(), 3);
        assertEquals(lines.get(0).get(0), "id");
        assertEquals(lines.get(0).get(1), "text");
        assertEquals(lines.get(1).get(0), "1");
        assertEquals(lines.get(1).get(1), "abc");
        assertEquals(lines.get(2).get(0), "2");
        assertEquals(lines.get(2).get(1), "sdf");
    }

    @Test
    public void testLongerLoadCSV() throws IOException {
        List<List<String>> lines = FileUtils.loadCSV(FileUtils.loadFile("src/test/resources/testCSV2.csv"), ",");

        assertEquals(lines.size(), 2);
        assertEquals(lines.get(0).get(0), "id");
        assertEquals(lines.get(0).get(1), "text");
        assertEquals(lines.get(1).get(0), "1");
        assertEquals(lines.get(1).get(1), "abc");
    }

    @Test
    public void testShorterLoadCSV() throws IOException {
        List<List<String>> lines = FileUtils.loadCSV(FileUtils.loadFile("src/test/resources/testCSV3.csv"), ",");

        assertEquals(lines.size(), 2);
        assertEquals(lines.get(0).get(0), "id");
        assertEquals(lines.get(0).get(1), "text");
        assertEquals(lines.get(1).get(0), "1");
        assertEquals(lines.get(1).get(1), "");
    }
}
