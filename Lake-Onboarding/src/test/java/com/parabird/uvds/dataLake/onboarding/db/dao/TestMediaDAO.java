package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.db.model.Media;
import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakeOnboardingApp.class)
public class TestMediaDAO {

    @Autowired
    private MediaDAO dao;

    private static Media mediaRecord1;
    private static Media mediaRecord2;

    @Before
    public void setUp() {
        mediaRecord1 = Media.newBuilder()
            .setInsertTime(new Timestamp(new Date().getTime()))
            .build();
        mediaRecord2 = Media.newBuilder()
            .setInsertTime(new Timestamp(new Date().getTime()))
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
        mediaRecord2.setDataId(new Long(1));
        dao.update(mediaRecord1);
        dao.update(mediaRecord2);

        List<Media> allRetrieved = dao.retrieveAll();
        assertEquals(1, allRetrieved.size());
        assertEquals(mediaRecord2, allRetrieved.get(0));
    }

}
