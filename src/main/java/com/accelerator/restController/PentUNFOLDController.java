package com.accelerator.restController;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.json.util.RestResponse;
import com.accelerator.facades.PentUNFOLDFacade;
import com.accelerator.facades.XlsxFillingFacade;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/chemistry")
public class PentUNFOLDController {

    private static final String FILE_2D_PATH = "src/main/resources/PentUNFOLD.xlsx";
    private static final String FILE_3D_PATH = "src/main/resources/PentUNFOLD3D.xlsx";

    @Resource
    PentUNFOLDFacade pentUNFOLDFacade;
    @Resource
    XlsxFillingFacade xlsxFillingFacade;

    private static final String  OK_MESSAGE = "Pent UNFOLD Algorithm is working.";

    @GetMapping(value = "/pent-un-fold")
    public RestResponse getPentUnFOLDAlgorithm() throws Exception {
        return new RestResponse(OK.value(), OK_MESSAGE);
    }

    @PostMapping(value = "/pent-un-fold",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String postPentUnFOLDAlgorithm(@RequestParam MultipartFile pdbFile, @RequestParam boolean include3d)
            throws Exception {
        PentUNFOLDModel pentUNFOLDModel = pentUNFOLDFacade.fillXlsxData(pdbFile);
        xlsxFillingFacade.fill2DFile(pentUNFOLDModel);
        if (include3d) {
            xlsxFillingFacade.fill3DFile(pentUNFOLDModel);
        }
        return "testId";
    }

    @GetMapping(value = "/pent-un-fold/{id}")
    public ResponseEntity<InputStreamResource> downloadXlsxFile(@PathVariable("id") String fileId) throws IOException {
        return getXlsxFile(FILE_2D_PATH);
    }

    @GetMapping(value = "/pent-un-fold/3d/{id}")
    public ResponseEntity<InputStreamResource> download3dXlsxFile(@PathVariable("id") String fileId) throws IOException {
        return getXlsxFile(FILE_3D_PATH);
    }

    private ResponseEntity<InputStreamResource> getXlsxFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        String mineType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        MediaType mediaType = MediaType.parseMediaType(mineType);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}
