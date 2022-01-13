package com.accelerator.services.implementations;

import com.accelerator.services.AlphaHelixService;
import com.accelerator.services.DsspService;
import com.accelerator.services.PentUNFOLDFilterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@Service("dsspService")
public class DsspServiceImpl implements DsspService {

    @Resource
    PentUNFOLDFilterService pentUNFOLDFilterService;
    @Resource
    AlphaHelixService alphaHelixService;

    SortedMap<Double, Character> secondaryStructure; 

    @Override
    public SortedMap<Double, Character> getDsspContext(List<String> pdbFile, String chain) {
        SortedMap<Double, List<String[]>> pdbData = pentUNFOLDFilterService.filterPdbToDssp(pdbFile, chain);
        alphaHelixDeterminate(pdbData);
        return null;
    }

    private void alphaHelixDeterminate(SortedMap<Double, List<String[]>> pdbData) {
        alphaHelixDeterminateByTurn(pdbData, 3);
        alphaHelixDeterminateByTurn(pdbData, 4);
        alphaHelixDeterminateByTurn(pdbData, 5);
    }

    private void alphaHelixDeterminateByTurn(SortedMap<Double, List<String[]>> pdbData, int turn) {
        List<Map.Entry<Double, List<String[]>>> aminoAcidResidues = new ArrayList<>(pdbData.entrySet());
        for (int i = 0; i < aminoAcidResidues.size(); i++) {
            Double firstAminoAcidResidueKey = aminoAcidResidues.get(i).getKey();
            Double secondAminoAcidResidueKey = findSecondAminoAcidResidueKey(aminoAcidResidues, firstAminoAcidResidueKey, turn, i);
            System.out.println(firstAminoAcidResidueKey + " " + secondAminoAcidResidueKey);
        }
    }

    private Double findSecondAminoAcidResidueKey(List<Map.Entry<Double, List<String[]>>> aminoAcidResidues,
                                                 Double firstAminoAcidResidueKey, int turn, int index) {
        int aminoAcidResiduesSize = aminoAcidResidues.size();
        for (int i = 1; i <= turn; i++){
            int secondIndex = index + i;
            boolean isExistRecord = aminoAcidResiduesSize > secondIndex;
            Double secondAminoAcidResidueKey = isExistRecord ? aminoAcidResidues.get(secondIndex).getKey() : null;
            if (secondAminoAcidResidueKey != null) {
                if (secondAminoAcidResidueKey > firstAminoAcidResidueKey + turn) {
                    break;
                } else if (secondAminoAcidResidueKey == firstAminoAcidResidueKey + turn || i == turn) {
                    return secondAminoAcidResidueKey;
                }
            } else break;
        }
        return null;
    }
}
