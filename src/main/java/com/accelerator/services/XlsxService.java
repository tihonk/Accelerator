package com.accelerator.services;

import org.apache.poi.xssf.model.SharedStringsTable;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

public interface XlsxService {

    XMLEventWriter writeValuesInNewRows(XMLEventFactory eventFactory,
                                        SharedStringsTable sharedstringstable,
                                        XMLEventWriter writer,
                                        List<String> values) throws XMLStreamException;

    XMLEventWriter writeValueInNewCell(XMLEventFactory eventFactory,
                                       SharedStringsTable sharedstringstable,
                                       XMLEventWriter writer,
                                       String value) throws XMLStreamException;

    XMLEvent prepareFillingValueEvent(XMLEventFactory eventFactory,
                                      SharedStringsTable sharedstringstable,
                                      String value);
}
