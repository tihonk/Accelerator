package com.accelerator.facades;

import com.accelerator.dto.PentUNFOLDModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

public interface PentUNFOLDFacade {

    PentUNFOLDModel fillXlsxData(MultipartFile pdbFile, ArrayList<String> picResult, String chain) throws IOException;

    PentUNFOLDModel fill1dSequenceData(String sequence);
}
