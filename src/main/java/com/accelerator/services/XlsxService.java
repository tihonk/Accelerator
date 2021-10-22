package com.accelerator.services;

import org.apache.poi.xssf.model.SharedStringsTable;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import java.util.List;

public interface XlsxService {

    XMLEventWriter writeValuesInNewRows(XMLEventFactory eventFactory,
                                        SharedStringsTable sharedstringstable,
                                        XMLEventWriter writer,
                                        List<String> values) throws XMLStreamException;
}
