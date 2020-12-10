package com.luv2code.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luv2code.demo.model.UploadFileResponse;
import com.luv2code.demo.services.AmazonS3ClientServiceImpl;

@RestController
@RequestMapping("/kyc")
public class FileHandlerController {

	@Autowired
	AmazonS3ClientServiceImpl s3Factory;

	@PostMapping(path = "/upload")
	public UploadFileResponse uploadFile(@RequestPart(value = "file", required = false) MultipartFile files) throws IOException {

		String url =  s3Factory.uploadFile(files);
		
		String contentType=files.getContentType();
		
		long fileSize=files.getSize();

		return new UploadFileResponse(url,
				contentType, fileSize);

		//return ResponseEntity.status(200).build();

	}	
	

	@GetMapping(path = "/download")
	public String generateUrl( @RequestParam String folderName) {

		String url=null;

		url=s3Factory.generatePresignedUrl(folderName);
		
		return url;

	}

}