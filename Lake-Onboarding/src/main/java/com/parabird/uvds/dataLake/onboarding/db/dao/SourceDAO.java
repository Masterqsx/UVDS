package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SourceDAO implements IDAO<Source> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Source sourceRecord) {
        entityManager.persist(sourceRecord);
    }

    @Override
    @Transactional
    public void update(Source sourceRecord) {
        entityManager.merge(sourceRecord);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(getById(id));
    }

    @Override
    public Source getById(Long id) {
        return entityManager.find(Source.class, id);
    }

    @Override
    public List<Source> retrieveAll() {
        List<Source> sourceRecords = entityManager.createQuery("FROM Source").getResultList();
        return sourceRecords;
    }
}
