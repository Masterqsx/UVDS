package com.parabird.uvds.dataLake.onboarding.db.service;

import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
import com.parabird.uvds.dataLake.onboarding.db.dao.ImageMediaDAO;
import com.parabird.uvds.dataLake.onboarding.db.dao.SourceDAO;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakeOnboardingApp.class)
public class TestSimpleLoader {

    private static Logger logger = LoggerFactory.getLogger(TestSimpleLoader.class);

    @Autowired
    SimpleLoader loader;

    @Autowired
    ImageMediaDAO imageDao;

    @Autowired
    SourceDAO sourceDao;

    ImageMedia imageMedia1;

    ImageMedia imageMedia2;

    @Before
    public void setUp() {
        Source sourceRecord = Source.newSourceBuilder()
                .setSourceName("sourceName")
                .setDescription("sourceDesc")
                .build();

        imageMedia1 = ImageMedia.newImageMediaBuilder()
                .setInsertTime(new Timestamp(new Date().getTime()))
                .setSourceUid("sourceUid")
                .setUid("hashValue1")
                .setSource(sourceRecord)
                .setFileName("fileName1")
                .setFilePath("filePath1")
                .setTags(new HashMap<String, String>() {{
                    put("tagKey1","tagValue1");
                }})
                .setFormat("format1")
                .build();
        imageMedia2 = ImageMedia.newImageMediaBuilder()
                .setInsertTime(new Timestamp(new Date().getTime()))
                .setSourceUid("sourceUid")
                .setSource(sourceRecord)
                .setFileName("fileName2")
                .setFilePath("filePath2")
                .setTags(new HashMap<String, String>() {{
                    put("tagKey2","tagValue2");
                }})
                .setFormat("format2")
                .build();
    }

    @Test
    @Transactional
    public void testUpdateImageMediaBySourceUid() {
        loader.saveImageMediaBySourceUid(imageMedia1);
        loader.saveImageMediaBySourceUid(imageMedia2);


        List<ImageMedia> retrievedImageMedia = imageDao.findAll();
        assertEquals(1, retrievedImageMedia.size());
        logger.info(retrievedImageMedia.get(0).toString());

        List<Source> retrievedSource = sourceDao.findAll();
        assertEquals(1, retrievedSource.size());

    }
}
