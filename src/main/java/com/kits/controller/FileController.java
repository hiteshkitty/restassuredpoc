package com.kits.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kits.model.Document;
import com.kits.model.FileUploadResponse;
import com.kits.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService service;

	@PostMapping("/uploadfile")
	public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		long size = file.getSize();

		Document documnent = service.store(file);

		FileUploadResponse response = new FileUploadResponse();
		response.setFileName(fileName);
		response.setSize(size);
		response.setDownloadUri("/downloadfile/" + documnent.getId());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//		@GetMapping("/files")
//		public ResponseEntity<List<ResponseFile>> getListFiles() {
//			List<ResponseFile> files = service.getAllFiles().map(dbFile -> {
//				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
//						.path(dbFile.getId()).toUriString();
//				return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
//			}).collect(Collectors.toList());
//			return ResponseEntity.status(HttpStatus.OK).body(files);
//		}

	@GetMapping("/downloadfile/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
		Document document = service.getFile(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
				.body(document.getFile());
	}
}