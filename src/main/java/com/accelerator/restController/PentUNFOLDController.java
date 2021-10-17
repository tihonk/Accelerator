package com.accelerator.restController;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.json.util.RestResponse;
import com.accelerator.facades.PentUNFOLDFacade;
import com.accelerator.facades.XlsxFillingFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/chemistry")
public class PentUNFOLDController {

    private static final String FILE_2D_PATH = "src/main/resources/test1.xlsx";

    @Resource
    PentUNFOLDFacade pentUNFOLDFacade;
    @Resource
    XlsxFillingFacade xlsxFillingFacade;

    private static final String  OK_MESSAGE = "Pent UNFOLD Algorithm is working.";

    @GetMapping(value = "/pent-unfold")
    public RestResponse getPentUnFOLDAlgorithm() throws Exception {
        return new RestResponse(OK.value(), OK_MESSAGE);
    }

    @PostMapping(value = "/pent-un-fold",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public PentUNFOLDModel postPentUnFOLDAlgorithm(@RequestParam MultipartFile pdbFile)
            throws Exception {
        PentUNFOLDModel pentUNFOLDModel = pentUNFOLDFacade.fillXlsxData(pdbFile);
        xlsxFillingFacade.processOneSheet(pentUNFOLDModel.getPdb(), 1, FILE_2D_PATH);
        xlsxFillingFacade.processOneSheet(pentUNFOLDModel.getDssp(), 2, FILE_2D_PATH);
        return pentUNFOLDModel;
    }
}
