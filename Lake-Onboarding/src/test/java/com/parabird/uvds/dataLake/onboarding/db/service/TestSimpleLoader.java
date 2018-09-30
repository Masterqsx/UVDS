package com.parabird.uvds.dataLake.onboarding.db.service;

import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
import com.parabird.uvds.dataLake.onboarding.db.dao.ImageMediaDAO;
import com.parabird.uvds.dataLake.onboarding.db.dao.SourceDAO;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

    ImageMedia imageMedia3;

    ImageMedia imageMedia4;

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
                .setTags(new HashSet<Tag>() {{
                    add(Tag.newTagBuilder()
                            .setTagSource("source1")
                            .setTagId(2)
                            .setTagName("tagName2")
                            .setTagValue("tagValue2")
                            .build());
                }})
                .setFormat("format1")
                .build();
        imageMedia2 = ImageMedia.newImageMediaBuilder()
                .setInsertTime(new Timestamp(new Date().getTime()))
                .setSourceUid("sourceUid")
                .setSource(Source.newSourceBuilder()
                    .setSourceName(sourceRecord.getSourceName())
                    .build())
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
        imageMedia3 = ImageMedia.newImageMediaBuilder()
                .setInsertTime(new Timestamp(new Date().getTime()))
                .setSourceUid("sourceUid")
                .setFileName("fileName3")
                .setFilePath("filePath3")
                .setTags(new HashSet<Tag>() {{
                    add(Tag.newTagBuilder()
                            .setTagSource("source1")
                            .setTagId(2)
                            .setTagName("tagName2")
                            .setTagValue("tagValue2")
                            .build());
                }})
                .setFormat("format3")
                .build();
        imageMedia4 = ImageMedia.newImageMediaBuilder()
                .setInsertTime(new Timestamp(new Date().getTime()))
                .setSourceUid("sourceUid")
                .setSource(Source.newSourceBuilder()
                    .setSourceName("otherSourceName")
                    .build())
                .setFileName("fileName4")
                .setFilePath("filePath4")
                .setTags(new HashSet<Tag>() {{
                    add(Tag.newTagBuilder()
                            .setTagSource("source1")
                            .setTagId(2)
                            .setTagName("tagName2")
                            .setTagValue("tagValue2")
                            .build());
                }})
                .setFormat("format4")
                .build();

    }

    @Test
    @Transactional
    public void testUpdateImageMediaBySourceUid() {
        loader.saveImageMediaBySource(imageMedia1);
        loader.saveImageMediaBySource(imageMedia2);
        loader.saveImageMediaBySource(imageMedia3);
        loader.saveImageMediaBySource(imageMedia4);


        List<ImageMedia> retrievedImageMedia = imageDao.findAll();
        assertEquals(3, retrievedImageMedia.size());
        for (ImageMedia media : retrievedImageMedia) {
            logger.info(media.toString());
        }

        List<Source> retrievedSource = sourceDao.findAll();
        assertEquals(2, retrievedSource.size());

    }
}
