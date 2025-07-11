package com.example.msfinancialmanagement.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/files")
public class FileUploadController {

    private final Path rootLocation = Paths.get("upload-dir");

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("paymentId") Long paymentId) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload");
            }

            // Create directory if it doesn't exist
            Files.createDirectories(rootLocation);

            // Generate unique filename
            String filename = paymentId + "_" + file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();

            // Copy file to destination
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Here you would update the Payment entity with the file path
            // paymentService.updateProofOfPayment(paymentId, destinationFile.toString());

            return ResponseEntity.ok("You successfully uploaded " + filename + "!");

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", Files.walk(rootLocation, 1)
                .filter(path -> !path.equals(rootLocation))
                .map(rootLocation::relativize)
                .map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }
}
