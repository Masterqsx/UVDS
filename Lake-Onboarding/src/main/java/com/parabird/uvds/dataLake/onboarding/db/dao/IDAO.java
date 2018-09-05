package com.parabird.uvds.dataLake.onboarding.db.dao;

import java.util.List;

public interface IDAO<T> {
    void add(T mappingObject);
    void update(T mappingObject);
    void delete(Long id);
    T getById(Long id);
    List<T> retrieveAll();
}
