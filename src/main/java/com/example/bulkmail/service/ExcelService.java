package com.example.bulkmail.service;

import com.example.bulkmail.model.MailBatch;
import com.example.bulkmail.model.MailItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {
    public MailBatch readExcel(MultipartFile file) throws IOException {
        MailBatch mailBatch = new MailBatch();
        List<MailItem> mailItems = new ArrayList<>();

        // Store the Excel file bytes
        mailBatch.setExcelFile(file.getBytes());

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            var sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                MailItem mailItem = new MailItem();
                mailItem.setEmail(row.getCell(0).getStringCellValue());
                mailItem.setSubject(row.getCell(1).getStringCellValue());
                mailItem.setMessage(row.getCell(2).getStringCellValue());
                mailItem.setAttachmentFile(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);
                mailItems.add(mailItem);
            }
        }

        mailBatch.setMailItems(mailItems);
        return mailBatch;
    }
}
