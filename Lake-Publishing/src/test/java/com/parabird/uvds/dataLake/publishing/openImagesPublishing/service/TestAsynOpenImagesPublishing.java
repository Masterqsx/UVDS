package com.parabird.uvds.dataLake.publishing.openImagesPublishing.service;

import com.parabird.uvds.dataLake.publishing.LakePublishingApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakePublishingApp.class)
public class TestAsynOpenImagesPublishing {
    @Autowired
    AsynOpenImagesPublishing publishing;

    @Test
    @Transactional
    public void testSynPublish() {
        publishing.setSourceMediaFolder(Arrays.asList(new String[]{"src/test/resources"}));
        publishing.setSourceMetadata(Arrays.asList(new String[]{"src/test/resources/testCSV1.csv"}));
        publishing.synPublishForTestOnly();
    }

    @Test
    @Transactional
    public void testAsynPublish() {
        publishing.setSourceMediaFolder(Arrays.asList(new String[]{"src/test/resources"}));
        publishing.setSourceMetadata(Arrays.asList(new String[]{"src/test/resources/testCSV1.csv", "src/test/resources/testCSV2.csv"}));
        publishing.mqInitialize(2, 4L, 2, 4L, 2, 4L);
        publishing.asynPublish();

        publishing.waitAsynPublishFinish();
    }

}
