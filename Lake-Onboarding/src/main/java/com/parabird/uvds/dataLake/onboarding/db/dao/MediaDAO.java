package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.db.model.Media;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MediaDAO implements IDAO<Media>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Media mediaRecord) {
        entityManager.persist(mediaRecord);
    }

    @Override
    @Transactional
    public void update(Media mediaRecord) {
        entityManager.merge(mediaRecord);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    public Media getById(Long id) {
        return entityManager.find(Media.class, id);
    }

    @Override
    public List<Media> retrieveAll() {
        List<Media> mediaRecords = entityManager.createQuery("FROM Media", Media.class).getResultList();
        return mediaRecords;
    }
}
