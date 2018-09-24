package com.parabird.uvds.dataLake.onboarding.db.dao;

import com.parabird.uvds.dataLake.onboarding.db.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@NoRepositoryBean
@Transactional
public interface MediaBaseDAO<T extends Media> extends JpaRepository<T, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<T> findBySourceUid(String sourceUid);
}
