package com.example.storeFilesInDB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.storeFilesInDB.entity.StudentData;
import com.example.storeFilesInDB.service.ExcelService;
import com.example.storeFilesInDB.utils.ExcelHandler;

@RestController
public class ExcelController {
	
	@Autowired
	private ExcelService excelService;
	
//	Http request method for posting the data/file
	 @PostMapping("/uploadExcelSheet")
	    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
	        String message = "";
	        if (ExcelHandler.hasExcelFile(file)) {
	            try {
	                excelService.saveStudentExcelData(file);
	                message = "Uploaded the file successfully: " + file.getOriginalFilename();
	                return ResponseEntity.status(HttpStatus.OK).body(message);
	            } catch (Exception e) {
	                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	            }
	        }
	        message = "Please upload an excel file!";
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	    }
	 
	 @GetMapping("/getExcelData")
	 public ResponseEntity<List<StudentData>> getStudentsData(){
		 List<StudentData> allStudentData = excelService.getAllStudentData();
		 return new ResponseEntity<List<StudentData>>(allStudentData, HttpStatus.OK);
	 }
	 
	 @GetMapping("/excelData/id/{id}")
	 public ResponseEntity<StudentData> getById(@PathVariable Long id) throws Exception{
		 StudentData studentDataById = excelService.getStudentDataById(id);
		 return new ResponseEntity<StudentData>(studentDataById, HttpStatus.OK);
	 }

}
