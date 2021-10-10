package com.accelerator.facades;

import com.accelerator.dto.PentUNFOLDModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PentUNFOLDFacade {

    PentUNFOLDModel fillXlsxData(MultipartFile pdbFile) throws IOException;
}
