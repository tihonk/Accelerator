package com.accelerator.facades.implementations;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.facades.PentUNFOLDFacade;
import com.accelerator.services.DsspThirdPartyService;
import com.accelerator.services.PdbContextService;
import com.accelerator.services.PicThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service("pentUNFOLDFacade")
public class PentUNFOLDFacadeImpl implements PentUNFOLDFacade {

    @Resource
    DsspThirdPartyService dsspThirdPartyService;
    @Resource
    PicThirdPartyService picThirdPartyService;
    @Resource
    PdbContextService pdbContextService;

    @Override
    public PentUNFOLDModel fillXlsxData(MultipartFile pdbFile) throws IOException {
        List<String> dsspContext = dsspThirdPartyService.getDsspContext(pdbFile);
        List<String> picContext = picThirdPartyService.getPicContext(pdbFile);
        List<String> pdbContext = pdbContextService.getPdbContext(pdbFile);
        PentUNFOLDModel pentUNFOLDModel = preparePentUNFOLDModel(dsspContext, picContext, pdbContext);
        return pentUNFOLDModel;
    }

    private PentUNFOLDModel preparePentUNFOLDModel(List<String> dsspContext,
                                                   List<String> picContext,
                                                   List<String> pdbContext) {
        PentUNFOLDModel pentUNFOLDModel = new PentUNFOLDModel();
        pentUNFOLDModel.setDssp(dsspContext);
        pentUNFOLDModel.setPic(picContext);
        pentUNFOLDModel.setPdb(pdbContext);
        return pentUNFOLDModel;
    }
}
