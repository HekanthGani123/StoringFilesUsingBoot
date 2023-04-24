package com.example.storeFilesInDB.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.storeFilesInDB.entity.FileEntry;
import com.example.storeFilesInDB.message.ResponseFile;
import com.example.storeFilesInDB.message.ResponseMessage;
import com.example.storeFilesInDB.service.FileService;

@RestController
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("/go")
	public String getForm() {
		return "form.html";
	}
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		
		String message = "";

		try {
			fileService.uploadFile(file);
			message = "File upload successfully " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "File not uploaded Successfully " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}

	}
	
	@PostMapping("/uploadMultiFiles")
	public ResponseEntity<String> storeMultiFiles(@RequestParam("files") MultipartFile[] files) throws IOException{
		List<FileEntry> uploadMultiFiles = fileService.uploadMultiFiles(files);
		System.out.println(uploadMultiFiles);
		return new ResponseEntity<String>("Multiple files uploaded", HttpStatus.OK);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<ByteArrayResource> getByFileById(@PathVariable Long id){
		FileEntry fileEntry = fileService.fetchById(id);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fileEntry.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileEntry.getFileName() + "\"")
				.body(new ByteArrayResource(fileEntry.getData()));
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		
	    List<ResponseFile> files = fileService.getAllFiles().map(dbFile -> {
	    	
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/download/")
	          .path(dbFile.getId().toString())
	          .toUriString();

	      return new ResponseFile(
	              dbFile.getFileName(),
	              fileDownloadUri,
	              dbFile.getContentType(),
	              dbFile.getData().length);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }
}
	


