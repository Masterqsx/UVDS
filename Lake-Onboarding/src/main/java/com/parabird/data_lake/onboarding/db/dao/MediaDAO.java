package com.parabird.data_lake.onboarding.db.dao;

import com.parabird.data_lake.onboarding.db.model.Media;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class MediaDAO implements IDAO<Media>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Media mediaRecord) {
        entityManager.persist(mediaRecord);
    }

    @Override
    public void update(Media mediaRecord) {

    }

    @Override
    public void deleteArticle(int id) {
        entityManager.remove(getById(id));
    }

    @Override
    public Media getById(int id) {
        return entityManager.find(Media.class, id);
    }

    @Override
    public List<Media> retrieveAll() {
        List<Media> mediaRecords = entityManager.createQuery("FROM Media").getResultList();
        return mediaRecords;
    }
}
