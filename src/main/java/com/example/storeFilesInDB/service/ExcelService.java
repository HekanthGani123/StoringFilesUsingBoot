package com.example.storeFilesInDB.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.storeFilesInDB.entity.StudentData;
import com.example.storeFilesInDB.repository.ExcelRepository;
import com.example.storeFilesInDB.utils.ExcelHandler;

@Service
public class ExcelService {
	
	@Autowired
	private ExcelRepository excelRepository;
	
	public void saveStudentExcelData(MultipartFile file) throws IOException {
		List<StudentData> students = ExcelHandler.excelToStudents(file.getInputStream());
		excelRepository.saveAll(students);
	}

	public List<StudentData> getAllStudentData(){
		List<StudentData> studentLists=excelRepository.findAll();
		return studentLists;
	}
}
