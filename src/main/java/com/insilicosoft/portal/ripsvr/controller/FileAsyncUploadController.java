package com.insilicosoft.portal.ripsvr.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.insilicosoft.portal.ripsvr.service.InputProcessorService;

@Controller
public class FileAsyncUploadController {

  @Autowired
  InputProcessorService inputProcessorService;

  @PostMapping("/uploadAsync")
  public CompletableFuture<ResponseEntity<String>> handleFileUpload(final @RequestParam("file")
                                                                          MultipartFile file)
                                                                    throws IOException {
    if (file == null) {
      return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("No files submitted"));
    } else {
      inputProcessorService.process(file);
      return CompletableFuture.completedFuture(ResponseEntity.ok(file.getOriginalFilename()));
    }
  }

}