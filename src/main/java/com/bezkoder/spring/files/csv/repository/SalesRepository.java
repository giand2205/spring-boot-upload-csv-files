package com.bezkoder.spring.files.csv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.files.csv.model.Sales;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
}
