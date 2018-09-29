package com.parabird.uvds.dataLake.onboarding.db.service;

import com.parabird.uvds.dataLake.onboarding.db.dao.ImageMediaOptimizedDAO;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchJdbcLoader {

    @Autowired
    ImageMediaOptimizedDAO imageDao;

    public void saveImageMediaByUid(List<ImageMedia> images) {
        imageDao.saveImageMediaBatch(images);
    }
}
