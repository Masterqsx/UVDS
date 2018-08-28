package com.parabird.data_lake.onboarding.db.dao;

import com.parabird.data_lake.onboarding.db.model.Source;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class SourceDAO implements IDAO<Source> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Source sourceRecord) {
        entityManager.persist(sourceRecord);
    }

    @Override
    public void update(Source sourceRecord) {

    }

    @Override
    public void delete(int id) {
        entityManager.remove(getById(id));
    }

    @Override
    public Source getById(int id) {
        return entityManager.find(Source.class, id);
    }

    @Override
    public List<Source> retrieveAll() {
        List<Source> sourceRecords = entityManager.createQuery("FROM Source").getResultList();
        return sourceRecords;
    }
}
