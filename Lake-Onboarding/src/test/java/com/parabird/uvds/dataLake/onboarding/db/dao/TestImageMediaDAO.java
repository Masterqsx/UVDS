package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakeOnboardingApp.class)
public class TestImageMediaDAO {

    private Logger logger = LoggerFactory.getLogger(TestImageMediaDAO.class);

    @Autowired
    ImageMediaDAO dao;

    private ImageMedia mediaRecord1;
    private ImageMedia mediaRecord2;

    @Before
    public void setUp() {
        Source sourceRecord = Source.newSourceBuilder()
                .setSourceName("sourceName")
                .setDescription("sourceDesc")
                .build();

        mediaRecord1 = ImageMedia.newImageMediaBuilder()
                .setInsertTime(new Timestamp(new Date().getTime()))
                .setSourceUid("sourceUid1")
                .setUid("hashValue1")
                .setSource(sourceRecord)
                .setFileName("fileName1")
                .setFilePath("filePath1")
                .setTags(new HashMap<String, String>() {{
                    put("tagKey1","tagValue1");
                }})
                .setFormat("format1")
                .build();
        mediaRecord2 = ImageMedia.newImageMediaBuilder()
                .setInsertTime(new Timestamp(new Date().getTime()))
                .setSourceUid("sourceUid2")
                .setUid("hashValue2")
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
    public void testAddAndRetrieveAll1() {
        dao.saveAndFlush(mediaRecord1);
        dao.saveAndFlush(mediaRecord2);

        List<ImageMedia> allRetrieved = dao.findAll();

        assertEquals(2, allRetrieved.size());
        assertEquals(allRetrieved.get(0), mediaRecord1);
        logger.info(allRetrieved.get(0).toString());
        assertEquals(allRetrieved.get(1), mediaRecord2);
        logger.info(allRetrieved.get(1).toString());
    }

    @Test
    @Transactional
    public void testAddAndUpdate4() {
        dao.saveAndFlush(mediaRecord1);

        mediaRecord2.setDataId(mediaRecord1.getDataId());
        dao.saveAndFlush(mediaRecord2);

        List<ImageMedia> allRetrieved = dao.findAll();
        assertEquals(1, allRetrieved.size());
        assertEquals(mediaRecord2, allRetrieved.get(0));
    }

    @Test
    @Transactional
    public void testAddAndUpdateBySourceUid5() {
        dao.saveAndFlush(mediaRecord1);

        Map<String, String> addTags1 = new HashMap<String, String>() {{
            put("addTag1","addTagValue1");
            put("addTag2", "addTagValue2");
        }};

        List<ImageMedia> targetMedia = dao.findBySourceUid(mediaRecord1.getSourceUid());

        for (ImageMedia media : targetMedia) {
            ImageMedia addMedia1 = ImageMedia.newImageMediaBuilder()
                .setDataId(media.getDataId())
                .setTags(addTags1)
                .build();

            addMedia1.getTags().putAll(media.getTags());

            dao.saveAndFlush(addMedia1);
        }

        targetMedia = dao.findBySourceUid(mediaRecord1.getSourceUid());

        targetMedia.forEach((ImageMedia media) -> logger.info(media.toString()));
    }
}
