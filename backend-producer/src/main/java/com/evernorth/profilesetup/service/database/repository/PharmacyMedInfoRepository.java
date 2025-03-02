package com.evernorth.profilesetup.service.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evernorth.profilesetup.service.database.entity.Medication;
import com.evernorth.profilesetup.service.database.entity.PharmMedInfo;

import io.lettuce.core.dynamic.annotation.Param;

public interface PharmacyMedInfoRepository extends JpaRepository<PharmMedInfo, Long> {

    @Query("SELECT p FROM PharmMedInfo p ORDER BY p.medication.name ASC, p.unitPrice ASC")
    List<PharmMedInfo> findAll();

    @Query("SELECT p FROM PharmMedInfo p WHERE soundex(p.medication.name) = soundex(:query)")
    List<PharmMedInfo> findMedicationsBySearch(@Param("query") String query);

    @Query("SELECT p FROM PharmMedInfo p WHERE p.medication.type = :type")
    List<PharmMedInfo> findMedicationByType(@Param("type") Medication.Type type );
    
} 