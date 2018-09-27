package com.parabird.uvds.dataLake.onboarding.db.dao;


import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;

import javax.persistence.LockModeType;
import java.util.List;

@Component
public  interface SourceDAO extends JpaRepository<Source, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<Source> findBySourceName(String sourceName);
}
