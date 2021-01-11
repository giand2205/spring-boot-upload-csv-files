package com.bezkoder.spring.files.csv;

import com.bezkoder.spring.files.csv.config.ApplicationProperties;
import com.bezkoder.spring.files.csv.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class FileInitRun implements CommandLineRunner {

    @Autowired
    CSVService fileService;

    @Autowired
    ApplicationProperties appProperties;

    @Override
    public void run(String... args) throws Exception {
        MyFileChangeListener.setFileService(fileService);
        MyFileChangeListener.setApplicationProperties(appProperties);
    }
}
