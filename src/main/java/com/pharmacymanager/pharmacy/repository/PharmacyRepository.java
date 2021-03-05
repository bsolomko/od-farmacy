package com.pharmacymanager.pharmacy.repository;

import com.pharmacymanager.pharmacy.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository  extends JpaRepository<Pharmacy,Long> {



}
