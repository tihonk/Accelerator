package com.accelerator.services.implementations;

import com.accelerator.convertors.AminoAcidConvertor;
import com.accelerator.services.PentUNFOLDFilterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Service("pentUNFOLDFilterService")
public class PentUNFOLDFilterServiceImpl implements PentUNFOLDFilterService {

    @Resource
    AminoAcidConvertor aminoAcidConvertor;

    private static final String PDB_CHAIN_REGEX = "ATOM(.*)\\s[A-Z]{3}\\s%s\\s(.*)";
    private static final String DSSP_CHAIN_MATCHING = "         %s         ";
    private static final String DSSP_START_NUMBERS_MATCHING = "\\d{%s}\\s(.*)";
    private static final int MAX_DSSP_NEEDED_SPACES = 4;
    List<String> dsspContent;
    List<String> pdbContent;
    String aminoAcidSequence = "";

    @Override
    public List<String> filterDssp(List<String> dsspContext, String chainContext) {
        dsspContent = new ArrayList<>();
        dsspContext.stream()
                .filter(dsspString -> dsspString.indexOf(format(DSSP_CHAIN_MATCHING, chainContext)) > 0)
                .forEach(this::addSpacesToString);
        return dsspContent;
    }

    @Override
    public List<String> filterPdb(List<String> pdbContext, String chainContext) {
        pdbContent = new ArrayList<>();
        pdbContext.stream()
                .filter(pdbString -> pdbString.matches(format(PDB_CHAIN_REGEX, chainContext)))
                .forEach(this::addNumberAndAminoAcid);
        return pdbContent;
    }

    @Override
    public String getSequence() {
        String aminoAcidSequenceToReturn = this.aminoAcidSequence;
        this.aminoAcidSequence = "";
        return aminoAcidSequenceToReturn;
    }

    private void addSpacesToString(String dsspString){
        for(int i = 1; i <= MAX_DSSP_NEEDED_SPACES; i++) {
            if (dsspString.matches(format(DSSP_START_NUMBERS_MATCHING, i))) {
                dsspString = addSpaces(dsspString, i);
                break;
            }
        }
        dsspContent.add(dsspString.substring(16, 17));
        dsspContent.add(dsspString.substring(13, 14));
        dsspContent.add(getDsspNumber(dsspString));
    }

    private String addSpaces(String dsspString, int numbers) {
        int neededSpaces = MAX_DSSP_NEEDED_SPACES - numbers;
        StringBuilder spaces = new StringBuilder();
        for (;neededSpaces >= 0; neededSpaces--) {
            spaces.append(" ");
        }
        return format("%s%s", spaces.toString(), dsspString);
    }

    private void addNumberAndAminoAcid(String pdbString) {
        Pattern generalPattern = Pattern.compile("(\\s[A-Z]{3}\\s\\w\\s+\\d+\\s)");
        Pattern numberPattern = Pattern.compile("(\\s\\d+\\s)");
        Matcher generalMatcher = generalPattern.matcher(pdbString);
        if(generalMatcher.find()) {
            String generalString = generalMatcher.group(1);
            Matcher numberMatcher = numberPattern.matcher(generalString);
            numberMatcher.find();
            String number = numberMatcher.group(1).trim();
            if(!pdbContent.contains(number)) {
                pdbContent.add(number);
                String aminoAcid = getAminoAcid(generalString);
                pdbContent.add(aminoAcid);
                aminoAcidSequence += aminoAcid;
            }
        }
    }

    private String getAminoAcid(String generalString) {
        String aminoAcid = generalString.substring(1, 4);
        return aminoAcidConvertor.convertToShort(aminoAcid);
    }

    private String getDsspNumber(String dsspString) {
        Pattern numberPattern = Pattern.compile("\\s+\\d+\\s+(\\d+)\\s+");
        Matcher generalMatcher = numberPattern.matcher(dsspString);
        generalMatcher.find();
        return generalMatcher.group(1);
    }
}
