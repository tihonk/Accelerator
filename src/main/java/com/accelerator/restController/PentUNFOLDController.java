package com.accelerator.restController;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.json.util.RestResponse;
import com.accelerator.facades.PentUNFOLDFacade;
import com.accelerator.services.XlsxFileFillingService;
import com.accelerator.services.XlsxFillingFacade;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/chemistry")
public class PentUNFOLDController {

    @Resource
    PentUNFOLDFacade pentUNFOLDFacade;
    @Resource
    XlsxFileFillingService xlsxFileFillingService;
    @Resource
    XlsxFillingFacade xlsxFillingFacade;

    private static final String  OK_MESSAGE = "Pent UNFOLD Algorithm is working.";

    @GetMapping(value = "/pent-unfold")
    public RestResponse getPentUnFOLDAlgorithm() throws Exception {
        xlsxFillingFacade.test();
        return new RestResponse(OK.value(), OK_MESSAGE);
    }

    @PostMapping(value = "/pent-un-fold",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public PentUNFOLDModel postPentUnFOLDAlgorithm(@RequestParam MultipartFile pdbFile) throws JsonParseException, IOException, InvalidFormatException {
        PentUNFOLDModel pentUNFOLDModel = pentUNFOLDFacade.fillXlsxData(pdbFile);
        xlsxFileFillingService.fillFile(pentUNFOLDModel);
        return pentUNFOLDModel;
    }
}
