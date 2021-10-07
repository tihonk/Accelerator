package com.accelerator.restController;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.json.util.RestResponse;
import com.accelerator.service.PentUNFOLDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/chemistry")
public class PentUNFOLDController {

    @Autowired
    PentUNFOLDService pentINFOLDService;

    private static final String  OK_MESSAGE = "Pent UNFOLD Algorithm is working.";

    @GetMapping(value = "/pent-unfold")
    public RestResponse getPentUnFOLDAlgorithm() throws JsonParseException {
        return new RestResponse(OK.value(), OK_MESSAGE);
    }

    @PostMapping(value = "/pent-un-fold",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public PentUNFOLDModel postPentUnFOLDAlgorithm(@RequestParam MultipartFile pdbFile) throws JsonParseException, IOException {
        PentUNFOLDModel pentUNFOLDModel = pentINFOLDService.fillXlsxData(pdbFile);
        return pentUNFOLDModel;
    }
}
