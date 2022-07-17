package com.kits.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kits.model.Document;
import com.kits.model.FileUploadResponse;
import com.kits.service.FileService;
 
@RestController
public class FileUploadController {
     
	@Autowired
	private FileService service;
	
    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file)
                    throws IOException {
         
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();
         
        Document documnent = service.store(file);
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + documnent.getId());
         
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}