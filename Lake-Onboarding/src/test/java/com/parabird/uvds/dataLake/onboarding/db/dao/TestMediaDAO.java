package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.db.model.Media;
import com.parabird.uvds.dataLake.onboarding.LakeOnboardingApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LakeOnboardingApp.class)
public class TestMediaDAO {

    @Autowired
    private MediaDAO dao;

    @Test
    public void testAddAndRetrieveAll() {
        Media mediaRecord1 = Media.newBuilder()
            //.setDataId(0)
            .setInsertTime(new Date())
            .build();
        Media mediaRecord2 = Media.newBuilder()
            //.setDataId(1)
            .setInsertTime(new Date())
            .build();

        dao.add(mediaRecord1);
        dao.add(mediaRecord2);

        List<Media> allRetrieved = dao.retrieveAll();

        assertEquals(allRetrieved.size(), 2);
        assertEquals(allRetrieved.get(0), mediaRecord1);
        assertEquals(allRetrieved.get(1), mediaRecord2);
    }

    @Test
    public void testAddAndDelete() {
    }

}
