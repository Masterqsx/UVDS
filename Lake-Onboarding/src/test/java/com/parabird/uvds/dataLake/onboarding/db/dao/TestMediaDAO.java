package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.db.model.Media;
import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TestMediaDAO {

    @Autowired
    private MediaDAO dao;

    @Autowired
    private SourceDAO sourceDAO;

    private static Media mediaRecord1;
    private static Media mediaRecord2;

    @Before
    public void setUp() {
        Source sourceRecord = Source.newSourceBuilder()
            .setSourceName("sourceName")
            .setDescription("sourceDesc")
            .build();

        sourceDAO.add(sourceRecord);

        mediaRecord1 = Media.newMediaBuilder()
            .setInsertTime(new Timestamp(new Date().getTime()))
            .setUid("hashValue1")
            .setSource(sourceRecord)
            .setFileName("fileName1")
            .setFilePath("filePath1")
            .setTags(new HashMap<String, String>() {{
                put("tagKey1","tagValue1");
            }})
            .build();
        mediaRecord2 = Media.newMediaBuilder()
            .setInsertTime(new Timestamp(new Date().getTime()))
            .setUid("hashValue2")
            .setSource(sourceRecord)
            .setFileName("fileName2")
            .setFilePath("filePath2")
            .setTags(new HashMap<String, String>() {{
                put("tagKey2","tagValue2");
            }})
            .build();
    }

    @Test
    @Transactional
    public void testAddAndRetrieveAll1() {
        dao.add(mediaRecord1);
        dao.add(mediaRecord2);

        List<Media> allRetrieved = dao.retrieveAll();

        assertEquals(2, allRetrieved.size());
        assertEquals(allRetrieved.get(0), mediaRecord1);
        assertEquals(allRetrieved.get(1), mediaRecord2);
    }

    @Test
    @Transactional
    public void testAddAndDelete2() {
        dao.add(mediaRecord1);
        dao.add(mediaRecord2);

        List<Media> allRetrieved = dao.retrieveAll();
        assertEquals(allRetrieved.size(), 2);

        dao.delete(allRetrieved.get(0).getDataId());
        dao.delete(allRetrieved.get(1).getDataId());

        List<Media> afterDelete = dao.retrieveAll();
        assertEquals(0, afterDelete.size());
    }

    @Test
    @Transactional
    public void testAddAndGetById3() {
        dao.add(mediaRecord1);
        dao.add(mediaRecord2);

        assertEquals(mediaRecord1, dao.getById(mediaRecord1.getDataId()));
        assertEquals(mediaRecord2, dao.getById(mediaRecord2.getDataId()));
    }

    @Test
    @Transactional
    public void testUpdate4() {
        dao.add(mediaRecord1);

        mediaRecord2.setDataId(mediaRecord1.getDataId());
        dao.update(mediaRecord2);

        List<Media> allRetrieved = dao.retrieveAll();
        assertEquals(1, allRetrieved.size());
        assertEquals(mediaRecord2, allRetrieved.get(0));
    }

}
