package com.accelerator.services.implementations;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRst;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Pattern;

public class ExampleEventUserModel {
    private static final String FILE_2D_PATH = "src/main/resources/PentUNFOLD.xlsx";

    public void processOneSheet(String filename) throws Exception {
//        FileInputStream in = new FileInputStream(new File(filename));
        OPCPackage opcpackage = OPCPackage.open(filename);

        PackagePart sharedstringstablepart = opcpackage.getPartsByName(Pattern.compile("/xl/sharedStrings.xml")).get(0);
        SharedStringsTable sharedstringstable = new SharedStringsTable();
        sharedstringstable.readFrom(sharedstringstablepart.getInputStream());
        PackagePart sheetpart = opcpackage.getPartsByName(Pattern.compile("/xl/worksheets/sheet1.xml")).get(0);
        XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(sheetpart.getInputStream());
        XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(sheetpart.getOutputStream());
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        int rowsCount = 0;
        int colsCount = 0;
        boolean cellAfound = false;
        boolean cellBfound = false;

        while(reader.hasNext()){ //loop over all XML in sheet1.xml
            XMLEvent event = (XMLEvent)reader.next();
            if(event.isStartElement()) {
                StartElement startElement = (StartElement)event;
                QName startElementName = startElement.getName();
                if(startElementName.getLocalPart().equalsIgnoreCase("row")) { //start element of row
                    rowsCount++;
                    colsCount = 0;
                } else if (startElementName.getLocalPart().equalsIgnoreCase("c")) { //start element of cell
                    colsCount++;
                    cellAfound = false;
                    cellBfound = false;
                    if (rowsCount % 5 == 0) { // every 5th row
                        if (colsCount == 1) { // cell A
                            cellAfound = true;
                        } else if (colsCount == 2) { // cell B
                            cellBfound = true;
                        }
                    }
                } else if (startElementName.getLocalPart().equalsIgnoreCase("v")) { //start element of value
                    if (cellAfound) {
                        // create new rich text content for cell A
                        CTRst ctstr = CTRst.Factory.newInstance();
                        ctstr.setT("changed String Value A" + (rowsCount));
                        //int sRef = sharedstringstable.addEntry(ctstr);
                        int sRef = sharedstringstable.addSharedStringItem(new XSSFRichTextString(ctstr));
                        // set the new characters for A's value in the XML
                        if (reader.hasNext()) {
                            writer.add(event); // write the old event
                            event = (XMLEvent)reader.next(); // get next event - should be characters
                            if (event.isCharacters()) {
                                Characters value = eventFactory.createCharacters(Integer.toString(sRef));
                                event = value;
                            }
                        }
                    } else if (cellBfound) {
                        // set the new characters for B's value in the XML
                        if (reader.hasNext()) {
                            writer.add(event); // write the old event
                            event = (XMLEvent)reader.next(); // get next event - should be characters
                            if(event.isCharacters()) {
                                double oldValue = Double.valueOf(((Characters)event).getData()); // old double value
                                Characters value = eventFactory.createCharacters(Double.toString(oldValue * rowsCount));
                                event = value;
                            }
                        }
                    }
                }
            }
            writer.add(event); //by default write each read event
        }
        writer.flush();

        //write the SharedStringsTable
        OutputStream out = sharedstringstablepart.getOutputStream();
        sharedstringstable.writeTo(out);
        out.close();
        opcpackage.close();




    }
//        XSSFReader r = new XSSFReader( pkg );
//        SharedStringsTable sst = r.getSharedStringsTable();
//        XMLReader parser = fetchSheetParser(sst);
////         To look up the Sheet Name / Sheet Order / rID,
////          you need to process the core Workbook stream.
////         Normally it's of the form rId# or rSheet#
//        InputStream sheet2 = r.getSheet("rId2");
//        InputSource sheetSource = new InputSource(sheet2);
//        parser.parse(sheetSource);
//        sheet2.close();
    public void processAllSheets(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader( pkg );
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while(sheets.hasNext()) {
            System.out.println("Processing new sheet:\n");
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
            System.out.println("");
        }
    }
    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
//        XMLReader parser = SAXHelper.newXMLReader();
//        ContentHandler handler = new SheetHandler(sst);
//        parser.setContentHandler(handler);
        return null;
    }
    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private static class SheetHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;
        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
            // c => cell
            if(name.equals("c")) {
                // Print the cell reference
                System.out.print(attributes.getValue("r") + " - ");
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if(cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }
            // Clear contents cache
            lastContents = "";
        }
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = sst.getEntryAt(idx).getT();
                nextIsString = false;
            }
            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v")) {
                System.out.println(lastContents);
            }
        }
        public void characters(char[] ch, int start, int length) {
            lastContents += new String(ch, start, length);
        }
    }
    public static void main(String[] args) throws Exception {
        ExampleEventUserModel example = new ExampleEventUserModel();
        example.processOneSheet(FILE_2D_PATH);
        example.processAllSheets(args[0]);
    }
}

