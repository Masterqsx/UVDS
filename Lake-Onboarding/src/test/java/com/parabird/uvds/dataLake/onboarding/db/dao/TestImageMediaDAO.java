package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import com.parabird.uvds.dataLake.onboarding.db.model.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakeOnboardingApp.class)
public class TestImageMediaDAO {

    private Logger logger = LoggerFactory.getLogger(TestImageMediaDAO.class);

    @Autowired
    ImageMediaDAO dao;

    private ImageMedia mediaRecord1;
    private ImageMedia mediaRecord2;
    private Source sourceRecord;

    @Before
    public void setUp() {
        sourceRecord = Source.newSourceBuilder()
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
                .setTags(new HashSet<Tag>() {{
                    add(Tag.newTagBuilder()
                        .setTagSource("source1")
                        .setTagId(1)
                        .setTagName("tagName1")
                        .setTagValue("tagValue1")
                        .build());
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
                .setTags(new HashSet<Tag>() {{
                    add(Tag.newTagBuilder()
                            .setTagSource("source1")
                            .setTagId(2)
                            .setTagName("tagName2")
                            .setTagValue("tagValue2")
                            .build());
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

        Set<Tag> addTags1 = new HashSet<Tag>() {{
            add(Tag.newTagBuilder()
                    .setTagSource("source2")
                    .setTagId(2)
                    .setTagName("tagName3")
                    .setTagValue("tagValue3")
                    .build());
        }};

        List<ImageMedia> targetMedia = dao.findBySourceUid(mediaRecord1.getSourceUid());

        for (ImageMedia media : targetMedia) {
            ImageMedia addMedia1 = ImageMedia.newImageMediaBuilder()
                .setDataId(media.getDataId())
                .setTags(addTags1)
                .build();

            addMedia1.merge(mediaRecord1);

            addMedia1.getTags().addAll(media.getTags());

            dao.saveAndFlush(addMedia1);
        }

        targetMedia = dao.findBySourceUid(mediaRecord1.getSourceUid());

        targetMedia.forEach((ImageMedia media) -> logger.info(media.toString()));

        targetMedia = dao.findBySourceUidAndSource_SourceName(mediaRecord1.getSourceUid(), sourceRecord.getSourceName());

        targetMedia.forEach((ImageMedia media) -> logger.info(media.toString()));
    }
}
