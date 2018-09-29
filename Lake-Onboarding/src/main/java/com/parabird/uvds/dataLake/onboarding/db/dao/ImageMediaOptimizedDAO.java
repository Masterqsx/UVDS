package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.db.model.ImageMedia;
import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class ImageMediaOptimizedDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public ImageMedia findOneByUid(ImageMedia image, EntityManager em) {

        Query query = em.createNativeQuery("select distinct * from Media m where m.uid = (:uid) and m.dtype= 'ImageMedia' for update", ImageMedia.class);
        query.setParameter("uid", image.getUid());
        try {
            return (ImageMedia) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public BigInteger findMediaIdByUid (ImageMedia image, EntityManager em) {
        Query query = em.createNativeQuery("select distinct data_id from Media m where m.uid = (:uid) and m.dtype= 'ImageMedia' for update");
        query.setParameter("uid", image.getUid());
        try {
            return (BigInteger) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void save(ImageMedia image, EntityManager em) {
        BigInteger retrieved = findMediaIdByUid(image, em);

        if (retrieved == null) {
            em.persist(image);
        } else {
            Query queryUpdate = em.createNativeQuery("update media set file_name=(:fileName), file_path=(:filePath), insert_time=(:insertTime), format=(:format), height=(:height), width=(:width) where data_id=(:dataId)");
            queryUpdate.setParameter("fileName", image.getFileName());
            queryUpdate.setParameter("filePath", image.getFilePath());
            queryUpdate.setParameter("insertTime", image.getInsertTime());
            queryUpdate.setParameter("format", image.getFormat());
            queryUpdate.setParameter("height", image.getHeight());
            queryUpdate.setParameter("width", image.getWidth());
            queryUpdate.setParameter("dataId", retrieved);
            queryUpdate.executeUpdate();

            for (Map.Entry<String, String> entry: image.getTags().entrySet()) {
                Query queryTags = em.createNativeQuery("insert into tags (media_data_id, tag_name, tag_value) values((:dataId) , (:tagName), (:tagValue)) on duplicate key update tag_value = (:tagName)");
                queryTags.setParameter("dataId", retrieved);
                queryTags.setParameter("tagName", entry.getKey());
                queryTags.setParameter("tagValue", entry.getValue());
                queryTags.executeUpdate();
            }
        }
    }

    @Transactional
    public void fillManagedSource(ImageMedia record) {
        if (record.getSource() == null || record.getSource().getSourceName() == null) return;
        String sqlInsert = "insert into Source (source_name, description)" +
                "values (?, ?)" +
                "on duplicate key update description = ?";

        jdbcTemplate.update(sqlInsert, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, record.getSource().getSourceName());
                ps.setString(2, record.getSource().getDescription());
                ps.setString(3, record.getSource().getDescription());
            }
        });

        String sqlSelect = "select * from Source where source_name = ?";

        Source source = (Source)jdbcTemplate.queryForObject(
            sqlSelect, new Object[] { record.getSource().getSourceName() },
            new BeanPropertyRowMapper<>(Source.class)
        );

        if (source != null) {
            record.getSource().setSourceId(source.getSourceId());
            record.getSource().setDescription(source.getDescription());
        }
    }

    @Transactional
    public void saveTags(List<ImageMedia> images) {
        String tagSql = "insert into Tags (media_data_id, tag_name, tag_value)" +
         "select data_id, ?, ? from Media where uid = ?" +
          "on duplicate key update tag_value = ?";

        List<List<String>> tags = new ArrayList<>();
        for (ImageMedia image: images) {
            if (image.getTags() ==  null) continue;
            for (Map.Entry<String, String> tag : image.getTags().entrySet()) {
                tags.add(Arrays.asList(tag.getKey(), tag.getValue(), image.getUid()));
            }
        }

        jdbcTemplate.batchUpdate(tagSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int count = 1;
                List<String> tuple = tags.get(i);
                ps.setString(count++, tuple.get(0));
                ps.setString(count++, tuple.get(1));
                ps.setString(count++, tuple.get(2));
                ps.setString(count++, tuple.get(1));
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
    }

    @Transactional
    public void saveImageMediaBatch (List<ImageMedia> images) {

        for (ImageMedia image : images) {
            fillManagedSource(image);
        }

        String mediaSql = "insert into Media " +
         "(file_name, file_path, insert_time, source_source_id, source_uid, uid, format, height, width, dtype) " +
          "values " +
           "(?, ?, ?, ?, ?, ?, ?, ?, ?, 'ImageMedia')" +
           "on duplicate key update " +
            "file_name = ?, file_path = ?, insert_time = ?, source_source_id = ?, source_uid = ?, format = ?, height = ?, width = ?";

        jdbcTemplate.batchUpdate(mediaSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int count = 1;
                ImageMedia image = images.get(i);
                ps.setString(count++, image.getFileName());
                ps.setString(count++, image.getFilePath());
                ps.setTimestamp(count++, image.getInsertTime());
                if (image.getSource() == null || image.getSource().getSourceId() == null) ps.setNull(count++, Types.BIGINT);
                else ps.setLong(count++, image.getSource().getSourceId());
                ps.setString(count++, image.getSourceUid());
                ps.setString(count++, image.getUid());
                ps.setString(count++, image.getFormat());
                if (image.getHeight() == null) ps.setNull(count++, java.sql.Types.INTEGER);
                else ps.setInt(count++, image.getHeight());
                if (image.getWidth() == null) ps.setNull(count++, java.sql.Types.INTEGER);
                else ps.setInt(count++, image.getWidth());
                ps.setString(count++, image.getFileName());
                ps.setString(count++, image.getFilePath());
                ps.setTimestamp(count++, image.getInsertTime());
                if (image.getSource() == null || image.getSource().getSourceId() == null) ps.setNull(count++, Types.BIGINT);
                else ps.setLong(count++, image.getSource().getSourceId());
                ps.setString(count++, image.getSourceUid());
                ps.setString(count++, image.getFormat());
                if (image.getHeight() == null) ps.setNull(count++, java.sql.Types.INTEGER);
                else ps.setInt(count++, image.getHeight());
                if (image.getWidth() == null) ps.setNull(count++, java.sql.Types.INTEGER);
                else ps.setInt(count++, image.getWidth());

            }

            @Override
            public int getBatchSize() {
                return images.size();
            }
        });

        saveTags(images);
    }


}
