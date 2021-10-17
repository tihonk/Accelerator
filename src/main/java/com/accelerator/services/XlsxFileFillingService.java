package com.accelerator.services;

import com.accelerator.dto.PentUNFOLDModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public interface XlsxFileFillingService {

    void fillFile(PentUNFOLDModel pentUNFOLDModel) throws IOException, InvalidFormatException;
}
