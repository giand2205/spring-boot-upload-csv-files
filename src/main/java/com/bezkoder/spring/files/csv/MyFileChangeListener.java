package com.bezkoder.spring.files.csv;

import com.bezkoder.spring.files.csv.config.ApplicationProperties;
import com.bezkoder.spring.files.csv.service.CSVService;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

@Component
public class MyFileChangeListener implements FileChangeListener {

    private static CSVService fileService;

    public static CSVService getFileService() {
        return fileService;
    }

    public static void setFileService(CSVService fileService) {
        MyFileChangeListener.fileService = fileService;
    }

    private static ApplicationProperties appProperties;

    public static ApplicationProperties getApplicationProperties() {
        return appProperties;
    }

    public static void setApplicationProperties(ApplicationProperties appProperties) {
        MyFileChangeListener.appProperties = appProperties;
    }

    private String nameFile = "";

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for (ChangedFiles cfiles : changeSet) {
            for (ChangedFile cfile : cfiles.getFiles()) {
                if ( /* (cfile.getType().equals(Type.MODIFY)
                     || cfile.getType().equals(Type.ADD)
                     || cfile.getType().equals(Type.DELETE) ) && */ !isLocked(cfile.getFile().toPath())) {
                    System.out.println("Operation: " + cfile.getType()
                            + " On file: " + cfile.getFile().getName() + " is done");

                    if (cfile.getType().equals(ChangedFile.Type.ADD)) {
                        try {
                            if (cfile.getFile().getName().equals(appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtZip())) {
                                nameFile = appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtCsv();
                                fileService.save(appProperties.getFilePathDownloaded() + appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtZip(), appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtCsv(), appProperties.getFilePathExtracted() + appProperties.getFile01() + "_" + appProperties.getLocalDate() + appProperties.getExtCsv());
                            }

                            System.out.println("Uploaded the file successfully: " + nameFile);
                        } catch (Exception e) {
                            System.out.println("Could not upload the file: " + nameFile + "!");
                            System.out.println(e.getMessage());
                        } finally {

                        }
                    }

                }
            }
        }
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }

}
