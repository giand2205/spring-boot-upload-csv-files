package com.bezkoder.spring.files.csv.service;

import com.bezkoder.spring.files.csv.helper.CSVHelper;
import com.bezkoder.spring.files.csv.model.Sales;
import com.bezkoder.spring.files.csv.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
  @Autowired
  SalesRepository repository;

  public void save(String filePathOrigin, String fileName, String filePathFinal) throws IOException {
    List<Sales> sales = CSVHelper.csvToDatabase(filePathOrigin, fileName, filePathFinal);
    repository.saveAll(sales);
    //CSVHelper.deleteFile(filePathFinal);
  }

}
