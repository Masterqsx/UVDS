package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.model.Media;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakeOnboardingApp.class)
public class TestSourceDAO {

    private static Logger logger = LoggerFactory.getLogger(TestSourceDAO.class);

    @Autowired
    private SourceDAO sourceDao;

    private static Source sourceRecord;

    private static List<Media> mediaList;

    @Before
    public void setUp() {
        mediaList = new ArrayList<>();
        mediaList.add(ImageMedia.newImageMediaBuilder()
            .setFileName("image1")
            .setFormat("jpg")
            .build());

        mediaList.add(ImageMedia.newImageMediaBuilder()
            .setFileName("image2")
            .setFileName("png")
            .build());

        sourceRecord = Source.newSourceBuilder()
            .setSourceName("sourceName")
            .setDescription("sourceDesc")
            .setMedias(mediaList)
            .build();
    }

    @Test
    @Transactional
    public void testAddAndRetrieveAll1() {
        sourceDao.add(sourceRecord);

        List<Source> allSources = sourceDao.retrieveAll();

        assertEquals(1, allSources.size());
        assertEquals(sourceRecord, allSources.get(0));

        List<Media> medias = allSources.get(0).getMedias();
        assertEquals(2, medias.size());
        assertEquals(mediaList.get(0), medias.get(0));
        assertEquals(mediaList.get(1), medias.get(1));

        logger.info(medias.get(0).toString());
    }

    @Test
    @Transactional
    public void testAddAndDelete2() {
        sourceDao.add(sourceRecord);

        List<Source> allSources = sourceDao.retrieveAll();
        assertEquals(1, allSources.size());

        sourceDao.delete(allSources.get(0).getSourceId());

        List<Source> afterDelete = sourceDao.retrieveAll();
        assertEquals(0, afterDelete.size());
    }

    @Test
    @Transactional
    public void testAddAndGetById3() {
        sourceDao.add(sourceRecord);

        assertEquals(sourceRecord, sourceDao.getById(sourceRecord.getSourceId()));
    }

    @Test
    @Transactional
    public void testAddAndUpdate4() {
        sourceDao.add(sourceRecord);

        sourceRecord.setDescription("sourceDesc_updated");

        sourceDao.update(sourceRecord);

        List<Source> afterUpdate = sourceDao.retrieveAll();
        assertEquals(1, afterUpdate.size());
        assertEquals(sourceRecord, afterUpdate.get(0));
    }

}
