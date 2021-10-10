package com.accelerator.facades.implementations;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.facades.PentUNFOLDFacade;
import com.accelerator.services.DsspThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service("pentUNFOLDFacade")
public class PentUNFOLDFacadeImpl implements PentUNFOLDFacade {

    @Resource
    DsspThirdPartyService dsspThirdPartyService;

    @Override
    public PentUNFOLDModel fillXlsxData(MultipartFile pdbFile) throws IOException {
        String dsspContext = dsspThirdPartyService.getDsspContext(pdbFile);
        String picContext = "Mock pic context.";
        String pdbContext = "Mock pdb context.";
        PentUNFOLDModel pentUNFOLDModel = preparePentUNFOLDModel(dsspContext, picContext, pdbContext);
        return pentUNFOLDModel;
    }

    private PentUNFOLDModel preparePentUNFOLDModel(String dsspContext, String picContext, String pdbContext) {
        PentUNFOLDModel pentUNFOLDModel = new PentUNFOLDModel();
        pentUNFOLDModel.setDssp(dsspContext);
        pentUNFOLDModel.setPic(picContext);
        pentUNFOLDModel.setPdb(pdbContext);
        return pentUNFOLDModel;
    }
}
