package com.example.storeFilesInDB.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.storeFilesInDB.entity.FileEntry;
import com.example.storeFilesInDB.repository.FileRepository;

@Service
public class FileService {
	
	@Autowired
	private FileRepository fileRepository;

	public FileEntry uploadFile(MultipartFile file) throws IOException {
		FileEntry entry=new FileEntry();
		entry.setFileName(file.getOriginalFilename());
		entry.setContentType(file.getContentType());
		entry.setData(file.getBytes());
		
		FileEntry save = fileRepository.save(entry);
		return save;
		
	}
	
	public FileEntry fetchById(Long id) {
		return fileRepository.findById(id).get();
	}
	
	public Stream<FileEntry> getAllFiles(){
		return fileRepository.findAll().stream();
	}
	
	public List<FileEntry> uploadMultiFiles(MultipartFile[] files){
		return Arrays.asList(files)
				.stream()
				.map(file->{
					try {
						return uploadFile(file);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					return null;
					
				})
				.collect(Collectors.toList());
	}
	
}
