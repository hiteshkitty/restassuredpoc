package com.kits.service;

import java.io.IOException;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kits.model.Document;
import com.kits.repository.FileRepository;

@Service
@Transactional
public class FileService {
	@Autowired
	private FileRepository fileRepository;

	public Document store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Document document = new Document();
		document.setName(fileName);
		document.setFile(file.getBytes());
		return fileRepository.save(document);
	}

	public Document getFile(Integer id) {
		return fileRepository.findById(id).get();
	}

	public Stream<Document> getAllFiles() {
		return fileRepository.findAll().stream();
	}
}
