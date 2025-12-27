package com.example.GSTechSecuritySystem.service;

import com.example.GSTechSecuritySystem.model.Product;
import com.example.GSTechSecuritySystem.repository.ProductRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;

    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> getProductsByCompany(String companyName) {
        return productRepo.findByCompanyName(companyName);
    }

    public List<Product> getAllProducts() {
        System.out.println("In getAllProducts method...");
        return productRepo.findAll();
    }

    public String uploadProducts(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return "Invalid file name!";
        }

        if (fileName.endsWith(".csv")) {
            return processCSV(file);
        } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            return processExcel(file);
        } else {
            return "Unsupported file type! Please upload CSV or Excel.";
        }
    }

    // Process CSV files
    private String processCSV(MultipartFile file) {
        int insertedCount = 0;
        int updatedCount = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isHeader = true;
            Set<String> seen = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                if (isHeader) { // skip header
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 3)
                    continue;

                String productName = data[0].trim();
                if (!seen.add(productName)) {
                    System.out.println("Duplicate in file skipped: " + productName);
                    continue;
                }
                String companyName = data[1].trim();
                double productPrice = Double.parseDouble(data[2].trim());

                if (upsertProduct(productName, companyName, productPrice)) {
                    updatedCount++;
                } else {
                    insertedCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing CSV: " + e.getMessage();
        }

        return "Upload completed (CSV). Inserted: " + insertedCount + ", Updated: " + updatedCount;
    }

    // Process Excel files
    private String processExcel(MultipartFile file) {
        int insertedCount = 0;
        int updatedCount = 0;

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);

            boolean isHeader = true;
            Set<String> seen = new HashSet<>();

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String productName = row.getCell(0).getStringCellValue().trim();

                if (!seen.add(productName)) {
                    System.out.println("Duplicate in file skipped: " + productName);
                    continue;
                }

                String companyName = row.getCell(1).getStringCellValue().trim();
                double price = row.getCell(2).getNumericCellValue();

                if (upsertProduct(productName, companyName, price))
                    updatedCount++;
                else
                    insertedCount++;
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing Excel: " + e.getMessage();
        }

        return "Upload completed (Excel). Inserted: " + insertedCount + ", Updated: " + updatedCount;
    }

    // Insert or update existing product
    private boolean upsertProduct(String productName, String companyName, double productPrice) {

        String cleaned = productName.trim();

        Product existing = productRepo.findByName(cleaned);

        if (existing != null) {
            existing.setCompanyName(companyName.trim());
            existing.setProductPrice(productPrice);
            productRepo.save(existing);
            return true;
        }

        Product p = new Product(companyName.trim(), cleaned, productPrice);
        productRepo.save(p);
        return false;
    }

}
