package com.accelerator.service.implementation;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.service.PentUNFOLDService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("pentUNFOLDService")
public class PentUNFOLDServiceImpl implements PentUNFOLDService {


    @Override
    public PentUNFOLDModel fillXlsxData(MultipartFile pdbFile) {
        PentUNFOLDModel pentUNFOLDModel = new PentUNFOLDModel();
        pentUNFOLDModel.setPdb("Test pdb.");
        pentUNFOLDModel.setDssp("Test dssp.");
        pentUNFOLDModel.setPic("Test pic");
        return pentUNFOLDModel;
    }
}
