package com.parabird.uvds.dataLake.onboarding.db.dao;


import com.parabird.uvds.dataLake.onboarding.db.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public  interface SourceDAO extends JpaRepository<Source, Long> {
}
