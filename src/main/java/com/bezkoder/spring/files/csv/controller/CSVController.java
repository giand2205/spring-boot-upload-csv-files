package com.bezkoder.spring.files.csv.controller;

import com.bezkoder.spring.files.csv.config.ApplicationProperties;
import com.bezkoder.spring.files.csv.message.ResponseMessage;
import com.bezkoder.spring.files.csv.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    CSVService fileService;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ApplicationProperties appProperties;

    Resource resource;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile() {
        String message = "";

        try {
            resource = resourceLoader.getResource("file:"+ appProperties.getFilePathDownloaded() + appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtZip());
            if (resource.exists()) {
                fileService.save(appProperties.getFilePathDownloaded() + appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtZip(), appProperties.getFile01() + appProperties.getExtCsv(), appProperties.getFilePathExtracted() + appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtCsv());
            }
//        resource = resourceLoader.getResource("file:"+ appProperties.getFilePathDownloaded() + appProperties.getFile02() + "_" + appProperties.getLocalDate() + appProperties.getExtZip());
//        if (resource.exists()) {
//          fileService.save(appProperties.getFilePathDownloaded() + appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtZip(), appProperties.getFile02() + appProperties.getExtCsv(), appProperties.getFilePathExtracted() + appProperties.getFile02() + "_" + appProperties.getLocalDate() + appProperties.getExtCsv());
//        }

            message = "Uploaded the file successfully: " + "Sales_Records_20210110.csv";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + "Sales_Records_20210110.csv" + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
