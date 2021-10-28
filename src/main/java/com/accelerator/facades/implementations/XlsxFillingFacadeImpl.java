package com.accelerator.facades.implementations;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.facades.XlsxFillingFacade;
import com.accelerator.services.FileProcessingService;
import com.accelerator.services.XlsxService;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Service("xlsxFillingFacade")
public class XlsxFillingFacadeImpl implements XlsxFillingFacade {

    @Resource
    XlsxService xlsxService;
    @Resource
    FileProcessingService fileProcessingService;

    private static final String FILE_2D_PATH = "src/main/resources/user-files/%s.xlsx";
    private static final String FILE_3D_PATH = "src/main/resources/user-files/%s3D.xlsx";
    private static final String MOTHER_FILE_2D_PATH = "src/main/resources/PentUNFOLD.xlsx";
    private static final String MOTHER_FILE_3D_PATH = "src/main/resources/PentUNFOLD3D.xlsx";
    private static final String ROW_ELEMENT = "row";
    private static final String VALUE_ELEMENT = "v";
    private static final String CELL_ELEMENT = "c";

    private XMLEventFactory eventFactory;
    private SharedStringsTable sharedstringstable;
    private PackagePart sharedStringsTablePart;
    private XMLEventWriter writer;
    private int rowsCount;
    private int columnCount;
    private boolean fillChain = false;
    private boolean is3dChainFilling = false;

    @Override
    public void fill2DFile(PentUNFOLDModel pentUNFOLDModel, String fileName, String chain) throws Exception {
        fileProcessingService.copyFile(fileName, MOTHER_FILE_2D_PATH);
        processOneSheet(pentUNFOLDModel.getPdb(), 1, format(FILE_2D_PATH, fileName));
        processOneSheet(pentUNFOLDModel.getDssp(), 3, format(FILE_2D_PATH, fileName));
        fillChain(chain, 4, false, format(FILE_2D_PATH, fileName));
        fileProcessingService.removeFile(format(FILE_2D_PATH, fileName));
    }

    @Override
    public void fill3DFile(PentUNFOLDModel pentUNFOLDModel, String fileName,
                           ArrayList<String> picResult, String chain) throws Exception {
        fileProcessingService.copyFile(fileName + "3D", MOTHER_FILE_3D_PATH);
        processOneSheet(pentUNFOLDModel.getPdb(), 1, format(FILE_3D_PATH, fileName));
        processOneSheet(pentUNFOLDModel.getDssp(), 2, format(FILE_3D_PATH, fileName));
        fillChain(chain, 4, true, format(FILE_3D_PATH, fileName));
        fileProcessingService.removeFile(format(FILE_3D_PATH, fileName));
    }

    private void fillChain(String chain, int sheet, boolean is3d, String path) throws Exception {
        fillChain = true;
        is3dChainFilling = is3d;
        processOneSheet(Collections.singletonList(chain), sheet, path);
        fillChain = false;
    }

    public void processOneSheet(List<String> values, int sheet, String filePath) throws Exception {
        OPCPackage opcpackage = OPCPackage.open(filePath);
        XMLEventReader reader = startFillingProcess(opcpackage, sheet);

        while(reader.hasNext()){
            XMLEvent event = (XMLEvent)reader.next();
            if(event.isStartElement() && values.size() >= rowsCount - 1) {
                event = putNewValuesInOldRows(reader, event, values);
            }
            writer.add(event);
            if(event.isEndElement()  && values.size() >= rowsCount - 1){
                putOtherValuesToNewRows(reader, event, values);
            }
        }
        closeFillingProcess();
        opcpackage.close();
    }

    private XMLEventReader startFillingProcess(OPCPackage opcpackage, int sheet)
            throws IOException, XMLStreamException {
        sharedStringsTablePart = opcpackage.getPartsByName(Pattern.compile("/xl/sharedStrings.xml")).get(0);
        sharedstringstable = new SharedStringsTable();
        sharedstringstable.readFrom(sharedStringsTablePart.getInputStream());
        PackagePart sheetpart = opcpackage.getPartsByName(Pattern.compile("/xl/worksheets/sheet" + sheet + ".xml")).get(0);
        XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(sheetpart.getInputStream());
        writer = XMLOutputFactory.newInstance().createXMLEventWriter(sheetpart.getOutputStream());
        eventFactory = XMLEventFactory.newInstance();
        rowsCount = 0;
        columnCount = 0;
        return reader;
    }

    private void closeFillingProcess() throws XMLStreamException, IOException {
        writer.flush();
        OutputStream out = sharedStringsTablePart.getOutputStream();
        sharedstringstable.writeTo(out);
        out.close();
    }

    private XMLEvent putNewValuesInOldRows(XMLEventReader reader, XMLEvent event, List<String> values) throws XMLStreamException {
        StartElement startElement = (StartElement)event;
        QName startElementName = startElement.getName();
        if(startElementName.getLocalPart().equalsIgnoreCase(ROW_ELEMENT)) {
            rowsCount++;
            columnCount = 0;
        } else if (startElementName.getLocalPart().equalsIgnoreCase(CELL_ELEMENT)) {
            columnCount++;
            event = fillCellIfNeeded(reader, event, values);
        } else if (startElementName.getLocalPart().equalsIgnoreCase(VALUE_ELEMENT) && isNeedFirstValueFilling()) {
            event = replaceOldValue(reader, values, event);
        } else if (startElementName.getLocalPart().equalsIgnoreCase(VALUE_ELEMENT) && isNeedSecondValueFilling()) {
            event = replaceOldValue(reader, values, event);
        }
        return event;
    }

    private XMLEvent fillCellIfNeeded(XMLEventReader reader, XMLEvent event,
                                      List<String> values) throws XMLStreamException {
        if (isNeedCellFilling(reader)) {
            return putValueInEmptyCell(reader, values);
        }
        return event;
    }

    private XMLEvent putValueInEmptyCell(XMLEventReader reader, List<String> values) throws XMLStreamException {
        String value = values.get(rowsCount - 2);
        writer = xlsxService.writeValueInNewCell(eventFactory, sharedstringstable, writer, value);
        reader.next();
        return (XMLEvent)reader.next();
    }

    private void putOtherValuesToNewRows(XMLEventReader reader, XMLEvent event, List<String> values)
            throws XMLStreamException {
        EndElement endElement = (EndElement)event;
        QName endElementName = endElement.getName();
        if(endElementName.getLocalPart().equalsIgnoreCase(ROW_ELEMENT)) {
            putValuesInNew(reader, values);
        }
    }

    private void putValuesInNew(XMLEventReader reader, List<String> values) throws XMLStreamException {
        XMLEvent nextElement = (XMLEvent)reader.peek();
        QName nextElementName = defineNextElementName(nextElement);
        if(!nextElementName.getLocalPart().equalsIgnoreCase(ROW_ELEMENT)) {
            List<String> residueValues = values.subList(rowsCount - 1, values.size());
            writer = xlsxService.writeValuesInNewRows(eventFactory, sharedstringstable, writer, residueValues);
        }
    }

    private XMLEvent replaceOldValue(XMLEventReader reader, List<String> values, XMLEvent event) throws XMLStreamException {
        writer.add(event);
        event = (XMLEvent)reader.next();
        if (event.isCharacters()) {
            String value = values.get(rowsCount - 2);
            event = xlsxService.prepareFillingValueEvent(eventFactory, sharedstringstable, value);
        }
        return event;
    }

    private QName defineNextElementName(XMLEvent nextElement) {
        if (nextElement.isStartElement()) {
            return ((StartElement)nextElement).getName();
        } else if (nextElement.isEndElement()) {
            return  ((EndElement)nextElement).getName();
        }
        return null;
    }

    private boolean isNeedFirstValueFilling() {
        return  rowsCount > 1 && columnCount == 1 && !fillChain;
    }

    private boolean isNeedSecondValueFilling() {
        int neededColumn = is3dChainFilling ? 1 : 2;
        return  rowsCount > 1 && columnCount == neededColumn && fillChain;
    }

    private boolean isNeedCellFilling(XMLEventReader reader) throws XMLStreamException {
        return !fillChain && noValue(reader) && rowsCount > 1 && columnCount == 1;
    }

    private boolean noValue(XMLEventReader reader) throws XMLStreamException {
        XMLEvent nextElement = (XMLEvent)reader.peek();
        QName nextElementName = defineNextElementName(nextElement);
        return !nextElementName.getLocalPart().equalsIgnoreCase(VALUE_ELEMENT);
    }
}
