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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakeOnboardingApp.class)
public class TestImageMediaOptimizedDAO {

    private Logger logger = LoggerFactory.getLogger(TestImageMediaOptimizedDAO.class);


    @Autowired
    ImageMediaDAO dao;

    @Autowired
    ImageMediaOptimizedDAO extendedDAO;

    @PersistenceContext
    EntityManager em;

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
                            .setTagId(2)
                            .setTagName("tagName2")
                            .setTagValue("tagValue2")
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
                            .setTagId(4)
                            .setTagName("tagName3")
                            .setTagValue("tagValue3")
                            .build());
                }})
                .setFormat("format2")
                .build();
    }

    @Test
    @Transactional
    public void testFindOneByUid() {
        dao.saveAndFlush(mediaRecord1);
        dao.saveAndFlush(mediaRecord2);

        ImageMedia media = extendedDAO.findOneByUid(ImageMedia.newImageMediaBuilder()
                .setUid(mediaRecord1.getUid())
                .build()
            , em);

        logger.info(media.toString());
    }

    @Test
    @Transactional
    public void testFindMediaIdByUid() {
        dao.saveAndFlush(mediaRecord1);
        dao.saveAndFlush(mediaRecord2);

        logger.info(extendedDAO.findMediaIdByUid(ImageMedia.newImageMediaBuilder()
                        .setUid(mediaRecord1.getUid())
                        .build()
                , em).toString());

    }

    @Test
    @Transactional
    public void testSave() {
        extendedDAO.save(mediaRecord1, em);

        mediaRecord2.setUid(mediaRecord1.getUid());
        extendedDAO.save(mediaRecord2, em);
        em.flush();
        em.refresh(mediaRecord1);

        logger.info(extendedDAO.findOneByUid(mediaRecord1, em).toString());
    }

    @Test
    @Transactional
    public void testSaveBatch() {
        extendedDAO.saveImageMediaBatch(Arrays.asList(mediaRecord1));

        mediaRecord2.setUid(mediaRecord1.getUid());
        extendedDAO.saveImageMediaBatch(Arrays.asList(mediaRecord2));

        logger.info(extendedDAO.findOneByUid(mediaRecord1, em).toString());
    }

    @Test
    @Transactional
    public void testFillManagedSource() {
        extendedDAO.fillManagedSource(mediaRecord1);
        extendedDAO.fillManagedSource(mediaRecord2);

        logger.info(mediaRecord1.getSource().toString());
        logger.info(mediaRecord2.getSource().toString());
    }
}
