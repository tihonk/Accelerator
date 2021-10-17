package com.accelerator.services.implementations;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.services.XlsxFileFillingService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("xlsxFileFillingService")
public class XlsxFileFillingServiceImpl implements XlsxFileFillingService {

    private static final String FILE_2D_PATH = "src/main/resources/PentUNFOLD.xlsx";

    @Override
    public void fillFile(PentUNFOLDModel pentUNFOLDModel) throws IOException, InvalidFormatException {
        createNewXlsxFile(new ArrayList<String>(Collections.singletonList("TestValue")));
    }

    public static File createNewXlsxFile(List<String> report) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(FILE_2D_PATH));
        System.out.println("test0");
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//        Workbook workbook = WorkbookFactory.create(inputStream);
        System.out.println("test1");
        XSSFSheet worksheet = workbook.getSheetAt(0);
//        Sheet sheet = workbook.getSheetAt(0);
//        fillSheetWithData(sheet, report);
        System.out.println("test2");
        return null;
    }

    private static File convertToFile(Workbook workbook) throws IOException {
        File fileToAttachment = new File(FILE_2D_PATH);
        try (OutputStream outStream = new FileOutputStream(fileToAttachment)) {
            workbook.write(outStream);
        }
        return fileToAttachment;
    }

    private static void fillSheetWithData(Sheet sheet, List<String> report) {
        for(int i = 0; i < report.size(); i++){
            Row row = sheet.createRow(1);
            Cell cell = row.createCell(0);
            cell.setCellValue(report.get(i));
        }
    }
}
