package com.example.storeFilesInDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.storeFilesInDB.entity.StudentData;

@Repository
public interface ExcelRepository extends JpaRepository<StudentData, Long> {

}
