package com.parabird.uvds.dataLake.onboarding.db.service;

import com.parabird.uvds.dataLake.onboarding.db.dao.ImageMediaDAO;
import com.parabird.uvds.dataLake.onboarding.db.dao.SourceDAO;
import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SimpleLoader {

    @Autowired
    ImageMediaDAO imageDao;

    @Autowired
    SourceDAO sourceDAO;


    private void fillManagedSource(ImageMedia record) {
        List<Source> sources = sourceDAO.findBySourceName(
                Optional.ofNullable(record.getSource())
                        .map(Source::getSourceName)
                        .orElse(null)
        );

        //The sourceName field in Source is unique, we only need the first one
        if (!sources.isEmpty()) {
            record.setSource(sources.get(0));
        }
    }

    /** This method will replace the current fields with the fields in new one other than tags.
     * Tags will be merged.
     * @param record
     */
    public void saveImageMediaBySource(ImageMedia record) {
        List<ImageMedia> imageMedias = Optional
            .ofNullable(
                imageDao.findBySourceUidAndSource_SourceName(record.getSourceUid()
                    , Optional.ofNullable(
                        record.getSource()
                        ).map(Source::getSourceName).orElse(null))
                )
            .orElse(new ArrayList<>());

        fillManagedSource(record);

        if (imageMedias.isEmpty()) {
            imageDao.save(record);
        }
        else {
            for (ImageMedia media : imageMedias) {
                ImageMedia temp = record.clone();

                temp.merge(media);

                temp.getTags().putAll(media.getTags());
                temp.setDataId(media.getDataId());

                imageDao.save(temp);
            }
        }
    }

    public void saveImageMediaByUid(ImageMedia record) {
        ImageMedia imageMedia = imageDao.findOneByUid(record.getUid());

        //fillManagedSource();
    }

    public void saveImageMediaBySourceBatch(List<ImageMedia> records) {
        List<ImageMedia> imageMediaList = records.stream().flatMap(
            (ImageMedia record) -> {
                List<ImageMedia> imageMedias = Optional
                    .ofNullable(
                        imageDao.findBySourceUidAndSource_SourceName(record.getSourceUid()
                            , Optional.ofNullable(
                                record.getSource()
                                    ).map(Source::getSourceName).orElse(null))
                            )
                            .orElse(new ArrayList<>());
                return imageMedias.stream();
            }
        ).collect(Collectors.toList());

        for (ImageMedia media : records) {
            fillManagedSource(media);
        }


    }


}
