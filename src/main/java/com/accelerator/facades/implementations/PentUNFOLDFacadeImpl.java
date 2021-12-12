package com.accelerator.facades.implementations;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.facades.PentUNFOLDFacade;
import com.accelerator.services.DsspThirdPartyService;
import com.accelerator.services.PdbContextService;
import com.accelerator.services.PentUNFOLDFilterService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("pentUNFOLDFacade")
public class PentUNFOLDFacadeImpl implements PentUNFOLDFacade {

    @Resource
    DsspThirdPartyService dsspThirdPartyService;
    @Resource
    PdbContextService pdbContextService;
    @Resource
    PentUNFOLDFilterService pentUNFOLDFilterService;

    @Override
    public PentUNFOLDModel fillXlsxData(MultipartFile pdbFile, ArrayList<String> picResult, String chain) throws IOException {
        List<String> dsspContext = dsspThirdPartyService.getDsspContext(pdbFile);
        List<String> pdbContext = pdbContextService.getPdbContext(pdbFile);
        return preparePentUNFOLDModel(dsspContext, pdbContext, picResult, chain);
    }

    @Override
    public PentUNFOLDModel fill1dSequenceData(String sequence) {
        PentUNFOLDModel pentUNFOLDModel = new PentUNFOLDModel();
        pentUNFOLDModel.setSequence(pentUNFOLDFilterService.filterSequence(sequence));
        return pentUNFOLDModel;
    }

    private PentUNFOLDModel preparePentUNFOLDModel(List<String> dsspContext,
                                                   List<String> pdbContext,
                                                   List<String> picContext,
                                                   String chainContext) {
        PentUNFOLDModel pentUNFOLDModel = new PentUNFOLDModel();
        pentUNFOLDModel.setDssp(pentUNFOLDFilterService.filterDssp(dsspContext, chainContext));
        pentUNFOLDModel.setPdb(pentUNFOLDFilterService.filterPdb(pdbContext, chainContext));
        pentUNFOLDModel.setSequence(pentUNFOLDFilterService.getSequence());
        pentUNFOLDModel.setPic(picContext);
        pentUNFOLDModel.setChain(chainContext);
        return pentUNFOLDModel;
    }
}
