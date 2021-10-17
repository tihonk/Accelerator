package com.accelerator.services.implementations;

import com.accelerator.services.XlsxFillingFacade;
import com.accelerator.services.XlsxService;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@Service("xlsxFillingFacade")
public class XlsxFillingFacadeImpl implements XlsxFillingFacade {

    @Resource
    XlsxService xlsxService;

    private static final String FILE_2D_PATH = "src/main/resources/test1.xlsx";
    private XMLEventWriter writer;
    private int rowsCount;
    private boolean rowStart;
    private XMLEventFactory eventFactory;
    private SharedStringsTable sharedstringstable;


    public void processOneSheet(List<String> values, int sheet, String filePath) throws Exception {
//        FileInputStream in = new FileInputStream(new File(filename));
        OPCPackage opcpackage = OPCPackage.open(filePath);

        PackagePart sharedstringstablepart = opcpackage.getPartsByName(Pattern.compile("/xl/sharedStrings.xml")).get(0);
        sharedstringstable = new SharedStringsTable();
        sharedstringstable.readFrom(sharedstringstablepart.getInputStream());
        PackagePart sheetpart = opcpackage.getPartsByName(Pattern.compile("/xl/worksheets/sheet" + sheet + ".xml")).get(0);
        XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(sheetpart.getInputStream());
        writer = XMLOutputFactory.newInstance().createXMLEventWriter(sheetpart.getOutputStream());
        eventFactory = XMLEventFactory.newInstance();

        rowsCount = 0;
        int colsCount = 0;
        boolean cellAfound = false;
        boolean cellBfound = false;
        int rowNumber = 0;

        while(reader.hasNext()){ //loop over all XML in sheet1.xml
            XMLEvent event = (XMLEvent)reader.next();
            writer.add(event); //by default write each readed event

            if(event.isStartElement()){
                StartElement startElement = (StartElement)event;
                QName startElementName = startElement.getName();
                if(startElementName.getLocalPart().equalsIgnoreCase("row")) { //start element of row
                    rowStart = true;
                    rowsCount++;
                    do {
                        event = (XMLEvent)reader.next(); //find this row's end
                        writer.add(event); //by default write each readed event
                        if(event.isEndElement()){
                            putOtherValuesToNewRows(reader, event, values);
                        }
                    } while (rowStart);
                }
            }
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
//    private static class SheetHandler extends DefaultHandler {
//        private SharedStringsTable sst;
//        private String lastContents;
//        private boolean nextIsString;
//        private SheetHandler(SharedStringsTable sst) {
//            this.sst = sst;
//        }
//        public void startElement(String uri, String localName, String name,
//                                 Attributes attributes) throws SAXException {
//            // c => cell
//            if(name.equals("c")) {
//                // Print the cell reference
//                System.out.print(attributes.getValue("r") + " - ");
//                // Figure out if the value is an index in the SST
//                String cellType = attributes.getValue("t");
//                if(cellType != null && cellType.equals("s")) {
//                    nextIsString = true;
//                } else {
//                    nextIsString = false;
//                }
//            }
//            // Clear contents cache
//            lastContents = "";
//        }
//        public void endElement(String uri, String localName, String name)
//                throws SAXException {
//            // Process the last contents as required.
//            // Do now, as characters() may be called more than once
//            if(nextIsString) {
//                int idx = Integer.parseInt(lastContents);
//                lastContents = sst.getEntryAt(idx).getT();
//                nextIsString = false;
//            }
//            // v => contents of a cell
//            // Output after we've seen the string contents
//            if(name.equals("v")) {
//                System.out.println(lastContents);
//            }
//        }
//        public void characters(char[] ch, int start, int length) {
//            lastContents += new String(ch, start, length);
//        }
//    }

    @Override
    public void test() throws Exception {

        List<String> arrayList = new ArrayList<>();
        arrayList.add("Hello");
        arrayList.add("What is your name?");
        arrayList.add("How old are you?");
        arrayList.add("Bye?");

        processOneSheet(arrayList, 1, FILE_2D_PATH);
    }



//    ============================================

    private void putOtherValuesToNewRows(XMLEventReader reader, XMLEvent event, List<String> values)
            throws XMLStreamException {
        EndElement endElement = (EndElement)event;
        QName endElementName = endElement.getName();
        if(endElementName.getLocalPart().equalsIgnoreCase("row")) {
            putValues(reader, values);
        }
    }

    private void putValues(XMLEventReader reader, List<String> values) throws XMLStreamException {
        rowStart = false;
        XMLEvent nextElement = (XMLEvent)reader.peek();
        QName nextElementName = defineNextElementName(nextElement);
        if(!nextElementName.getLocalPart().equalsIgnoreCase("row")) {
            writer = xlsxService.writeValuesInNewRows(eventFactory, sharedstringstable, writer, values);
        }
    }

    private QName defineNextElementName(XMLEvent nextElement) {
        if (nextElement.isStartElement()) {
            return ((StartElement)nextElement).getName();
        } else if (nextElement.isEndElement()) {
            return  ((EndElement)nextElement).getName();
        }
        return null;
    }
}

