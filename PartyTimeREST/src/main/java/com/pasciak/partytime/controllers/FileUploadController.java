package com.pasciak.partytime.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

	// private static final String UPLOAD_DIR = "uploads/";

	@Value("${file.upload-dir}")
	private String uploadDir;

	@PostMapping("/uploadfiles")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Please select a file to upload.");
		}

		try {
			// Create the upload directory if it doesn't exist
			// Path uploadPath = Paths.get(UPLOAD_DIR);
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Get the file's original name and target location
			String fileName = file.getOriginalFilename();
			Path targetPath = uploadPath.resolve(fileName);

			// NOTE: Test case for now, only if file name = pt-erd.png
			// NOTE: Then if target path exists, remove the file
			if (file.getOriginalFilename().equals("pt-erd.png")) {
				if (Files.exists(targetPath)) {
					Files.delete(targetPath);
				}
			}

			// Copy the file to the target location
			Files.copy(file.getInputStream(), targetPath);

			return ResponseEntity.ok("File uploaded successfully: " + fileName);
		} catch (

		IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to upload file.");
		}
	}
}
