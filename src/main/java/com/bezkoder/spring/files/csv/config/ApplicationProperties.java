package com.bezkoder.spring.files.csv.config;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties implements InitializingBean {

    @Value("${file01.downloaded.name}")
    private String file01;

    @Value("${file02.downloaded.name}")
    private String file02;

    @Value("${extension.zip}")
    private String extZip;

    @Value("${extension.csv}")
    private String extCsv;

    @Value("${filepath.downloaded}")
    private String filePathDownloaded;

    @Value("${filepath.extracted}")
    private String filePathExtracted;

    private String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    private Map<String, String> mapErrors;

    @Override
    public void afterPropertiesSet() throws Exception {
        mapErrors = new HashMap<>();
        mapErrors.put("500", "Error General");
    }
}
