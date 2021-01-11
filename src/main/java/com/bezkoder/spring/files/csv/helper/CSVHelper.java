package com.bezkoder.spring.files.csv.helper;

import com.bezkoder.spring.files.csv.exception.ServiceException;
import com.bezkoder.spring.files.csv.model.Sales;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Id", "Title", "Description", "Published"};

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Sales> csvToDatabase(String filePathOrigin, String fileName, String filePathFinal) throws IOException {
        unzipFile(filePathOrigin, filePathFinal, fileName);
        MultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(filePathFinal));
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Sales> salesList = new ArrayList<Sales>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            for (CSVRecord csvRecord : csvRecords) {
                Sales sales = new Sales(
                        csvRecord.get("Region"),
                        csvRecord.get("Country"),
                        csvRecord.get("Item Type"),
                        csvRecord.get("Sales Channel"),
                        csvRecord.get("Order Priority"),
                        df.parse(csvRecord.get("Order Date")),
                        Integer.parseInt(csvRecord.get("Order ID")),
                        df.parse(csvRecord.get("Ship Date")),
                        Integer.parseInt(csvRecord.get("Units Sold")),
                        Double.parseDouble(csvRecord.get("Unit Price")),
                        Double.parseDouble(csvRecord.get("Unit Cost")),
                        Double.parseDouble(csvRecord.get("Total Revenue")),
                        Double.parseDouble(csvRecord.get("Total Cost")),
                        Double.parseDouble(csvRecord.get("Total Profit"))
                );

                salesList.add(sales);
            }

            return salesList;
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private static String unzipFile(final String filePathOrigin, final String filePathFinal, String fileName) {
        String newFile;

        try (ZipInputStream zipInputStream
                     = new ZipInputStream(Files.newInputStream(Paths.get(filePathOrigin)))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry != null) {
                newFile = FilenameUtils.normalize(newFile(filePathFinal, fileName).getAbsolutePath());
                try (OutputStream outputStream = Files.newOutputStream(Paths.get(newFile))) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zipInputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            } else {
                zipInputStream.closeEntry();
                throw new ServiceException("Not exists files in zip");
            }
        } catch (IOException ex) {
            log.error("Error in unzip file", ex);
            throw new ServiceException("Error in unzip file", ex);
        }
        return newFile;
    }

    private static File newFile(final String filePath, String fileName) {
        String file = new File(filePath).getParent();
        return new File(file, fileName);
    }

    public static void deleteFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
            System.out.println("File or directory deleted successfully");
        } catch (NoSuchFileException ex) {
            System.out.printf("No such file or directory: %s\n", path);
        } catch (DirectoryNotEmptyException ex) {
            System.out.printf("Directory %s is not empty\n", path);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
