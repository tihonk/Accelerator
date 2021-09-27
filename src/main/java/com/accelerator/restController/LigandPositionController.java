package com.accelerator.restController;

import com.accelerator.dto.AminoAcid;
import com.accelerator.dto.Ligand;
import com.accelerator.json.util.RestResponse;
import com.accelerator.service.LigandPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chemistry")
public class LigandPositionController {

    private static final String  OK_MESSAGE = "You are on the right way for calculating the position of the ligand";

    @Autowired
    LigandPositionService ligandPositionService;

    @GetMapping(value = "/ligand-position")
    public RestResponse getCountLigandPosition() throws JsonParseException {
        return new RestResponse(HttpStatus.OK.value(), OK_MESSAGE);
    }

    @PostMapping(value = "/ligand-position")
    public List<AminoAcid> postCountLigandPosition(Ligand ligand) throws JsonParseException {
        List<AminoAcid> relatedAminoAcids = ligandPositionService.getRelatedAminoAcids(ligand);
        return relatedAminoAcids;
    }
}
