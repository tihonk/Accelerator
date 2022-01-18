package com.accelerator.restController;

import com.accelerator.dto.PentUNFOLDModel;
import com.accelerator.json.util.RestResponse;
import com.accelerator.facades.PentUNFOLDFacade;
import com.accelerator.facades.XlsxFillingFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.ArrayList;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/chemistry")
public class PentUNFOLDController {

    private static final Logger rootLogger = LogManager.getRootLogger();
    private static final String FILE_1D_PATH = "src/main/resources/user-files/%s1D.xlsx";
    private static final String FILE_2D_PATH = "src/main/resources/user-files/%s2D.xlsx";
    private static final String FILE_3D_PATH = "src/main/resources/user-files/%s3D.xlsx";

    @Resource
    PentUNFOLDFacade pentUNFOLDFacade;
    @Resource
    XlsxFillingFacade xlsxFillingFacade;

    private static final String  OK_MESSAGE = "Pent UNFOLD Algorithm is working.";

    @GetMapping(value = "/pent-un-fold")
    public RestResponse getPentUnFOLDAlgorithm() {
        return new RestResponse(OK.value(), OK_MESSAGE);
    }

    @PostMapping(value = "/pent-un-fold",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String postPentUnFOLDAlgorithm(@RequestParam MultipartFile pdbFile, @RequestParam boolean include1d,
                                          @RequestParam boolean include2d, @RequestParam boolean include3d,
                                          @RequestParam ArrayList<String> picResult, @RequestParam String chain,
                                          @RequestParam boolean isFileNeeded){
        String fileName = generateUniqueFileName(requireNonNull(pdbFile.getOriginalFilename()));
        try {
            fillNecessaryFiles(include1d, include2d, include3d, isFileNeeded, picResult, chain, fileName, pdbFile);
        } catch (Exception exception) {
            rootLogger.error(exception.getMessage());
            return null;
        }
        return fileName;
    }

    @PostMapping(value = "/pent-un-fold/sequence",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String postPentUnFOLD1dSequence(@RequestParam String sequence)
            throws Exception {
        String fileName = generateUniqueFileName("sequ");
        PentUNFOLDModel pentUNFOLDModel = pentUNFOLDFacade.fill1dSequenceData(sequence);
        xlsxFillingFacade.fill1DFile(pentUNFOLDModel, fileName);
        return fileName;
    }

    @GetMapping(value = "/pent-un-fold/2d/{id}")
    public ResponseEntity<InputStreamResource> downloadXlsxFile(@PathVariable("id") String fileId) throws IOException {
        return getXlsxFile(format(FILE_2D_PATH, fileId));
    }

    @GetMapping(value = "/pent-un-fold/3d/{id}")
    public ResponseEntity<InputStreamResource> download3dXlsxFile(@PathVariable("id") String fileId) throws IOException {
        return getXlsxFile(format(FILE_3D_PATH, fileId));
    }

    @GetMapping(value = "/pent-un-fold/1d/{id}")
    public ResponseEntity<InputStreamResource> download1dXlsxFile(@PathVariable("id") String fileId) throws IOException {
        return getXlsxFile(format(FILE_1D_PATH, fileId));
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

    private String generateUniqueFileName(String originalFilename) {
        String fileNameFormatter = "%s_(%s)";
        String id = UUID.randomUUID().toString().substring(0, 8);
        String fileName = originalFilename.substring(0, 4);
        return format(fileNameFormatter, fileName, id);
    }

    private void fillNecessaryFiles(boolean include1d, boolean include2d, boolean include3d, boolean isFileNeeded,
                                    ArrayList<String> picResult, String chain, String fileName,
                                    MultipartFile pdbFile) throws Exception {
        PentUNFOLDModel pentUNFOLDModel
                = pentUNFOLDFacade.fillXlsxData(pdbFile, picResult, chain, include2d, include3d, isFileNeeded);
        if (include1d) {
            xlsxFillingFacade.fill1DFile(pentUNFOLDModel, fileName);
        }
        if (include2d) {
            xlsxFillingFacade.fill2DFile(pentUNFOLDModel, fileName);
        }
        if (include3d) {
            xlsxFillingFacade.fill3DFile(pentUNFOLDModel, fileName);
        }
    }
}
