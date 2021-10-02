package com.accelerator.service;

import com.accelerator.dto.PentUNFOLDModel;
import org.springframework.web.multipart.MultipartFile;

public interface PentUNFOLDService {

    PentUNFOLDModel fillXlsxData(MultipartFile pdbFile);
}
