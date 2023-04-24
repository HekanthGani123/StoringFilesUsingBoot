package com.example.storeFilesInDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.storeFilesInDB.entity.FileEntry;

public interface FileRepository extends JpaRepository<FileEntry, Long> {

}
