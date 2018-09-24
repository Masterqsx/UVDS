package com.parabird.uvds.dataLake.onboarding.db.service;

import com.parabird.uvds.dataLake.onboarding.db.dao.ImageMediaDAO;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SimpleLoader {

    @Autowired
    ImageMediaDAO imageDao;

    /** This method will replace the current fields with the fields in new one other than tags.
     * Tags will be merged.
     * @param record
     */
    public void saveImageMediaBySourceUid(ImageMedia record) {
        List<ImageMedia> imageMedias = Optional
            .ofNullable(
                imageDao.findBySourceUid(record.getSourceUid())
                )
            .orElse(new ArrayList<>());

        if (imageMedias.isEmpty()) {
            imageDao.saveAndFlush(record);
        }
        else {
            for (ImageMedia media : imageMedias) {
                ImageMedia temp = record.clone();

                temp.merge(media);

                temp.getTags().putAll(media.getTags());
                temp.setDataId(media.getDataId());

                imageDao.saveAndFlush(temp);
            }
        }
    }


}
