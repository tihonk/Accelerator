package com.accelerator.services.implementations;

import com.accelerator.services.PentUNFOLDFilterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service("pentUNFOLDFilterService")
public class PentUNFOLDFilterServiceImpl implements PentUNFOLDFilterService {

    private static final String PDB_CHAIN_REGEX = "ATOM(.*)\\s[A-Z]{3}\\s%s\\s(.*)";
    private static final String DSSP_CHAIN_MATCHING = "         %s         ";
    private static final String DSSP_START_NUMBERS_MATCHING = "\\d{%s}\\s(.*)";
    private static final int MAX_DSSP_NEEDED_SPACES = 4;
    List<String> dsspContent = new ArrayList<>();

    @Override
    public List<String> filterDssp(List<String> dsspContext, String chainContext) {
        dsspContext.stream()
                .filter(dsspString -> dsspString.indexOf(format(DSSP_CHAIN_MATCHING, chainContext)) > 0)
                .forEach(this::addSpacesToString);
        return dsspContent;
    }

    @Override
    public List<String> filterPdb(List<String> pdbContext, String chainContext) {
        return pdbContext.stream()
                .filter(pdbString -> pdbString.matches(format(PDB_CHAIN_REGEX, chainContext)))
                .collect(toList());
    }

    private void addSpacesToString(String dsspString){
        for(int i = 1; i <= MAX_DSSP_NEEDED_SPACES; i++) {
            if (dsspString.matches(format(DSSP_START_NUMBERS_MATCHING, i))) {
                dsspContent.add(addSpaces(dsspString, i));
                return;
            }
        }
        dsspContent.add(dsspString);
    }

    private String addSpaces(String dsspString, int numbers) {
        int neededSpaces = MAX_DSSP_NEEDED_SPACES - numbers;
        StringBuilder spaces = new StringBuilder();
        for (;neededSpaces >= 0; neededSpaces--) {
            spaces.append(" ");
        }
        return format("%s%s", spaces.toString(), dsspString);
    }
}
