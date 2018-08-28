package com.parabird.data_lake.onboarding.db.dao;

import java.util.List;

public interface IDAO<T> {
    void add(T mappingObject);
    void update(T mappingObject);
    void delete(int id);
    T getById(int id);
    List<T> retrieveAll();
}
