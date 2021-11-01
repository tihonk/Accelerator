package com.accelerator.services.implementations;

import com.accelerator.services.PentUNFOLDFilterService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service("pentUNFOLDFilterService")
public class PentUNFOLDFilterServiceImpl implements PentUNFOLDFilterService {

    private static final String DSSP_CHAIN_MATCHING = "         %s         ";
    private static final String PDB_CHAIN_REGEX = "ATOM(.*)\\s[A-Z]{3}\\s%s\\s(.*)";

    @Override
    public List<String> filterDssp(List<String> dsspContext, String chainContext) {
        return dsspContext.stream()
                .filter(dsspString -> dsspString.indexOf(format(DSSP_CHAIN_MATCHING, chainContext)) > 0)
                .collect(toList());
    }

    @Override
    public List<String> filterPdb(List<String> pdbContext, String chainContext) {
        return pdbContext.stream()
                .filter(pdbString -> pdbString.matches(format(PDB_CHAIN_REGEX, chainContext)))
                .collect(toList());
    }
}
