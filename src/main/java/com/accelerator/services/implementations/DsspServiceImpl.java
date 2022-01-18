package com.accelerator.services.implementations;

import com.accelerator.services.AlphaHelixService;
import com.accelerator.services.DsspService;
import com.accelerator.services.PentUNFOLDFilterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@Service("dsspService")
public class DsspServiceImpl implements DsspService {

    private static final List<Double> ALFA_HELIX = Arrays.asList(3.0, 4.0, 5.0);

    @Resource
    PentUNFOLDFilterService pentUNFOLDFilterService;
    @Resource
    AlphaHelixService alphaHelixService;

    SortedMap<Double, Set<Double[]>> hBoundsDescription;
    SortedMap<Double, String> secondaryStructure;
    List<String> finalDsspData;
    Double preSecondAminoAcidResidueKey;

    @Override
    public List<String> getDsspContext(List<String> pdbFile, String chain) {
        SortedMap<Double, List<String[]>> pdbData = pentUNFOLDFilterService.filterPdbToDssp(pdbFile, chain);
        alphaHelixDeterminate(pdbData);
        secondaryStructureDeterminate();
        prepareFinalDsspData(pdbData);
        return finalDsspData;
    }

    private void alphaHelixDeterminate(SortedMap<Double, List<String[]>> pdbData) {
        hBoundsDescription = new TreeMap<>();
        secondaryStructure = new TreeMap<>();
        finalDsspData = new ArrayList<>();
//        alphaHelixDeterminateByTurn(pdbData, 3.0);
        alphaHelixDeterminateByTurn(pdbData, 4.0);
//        alphaHelixDeterminateByTurn(pdbData, 5.0);
    }

    private void alphaHelixDeterminateByTurn(SortedMap<Double, List<String[]>> pdbData, Double turn) {
        List<Map.Entry<Double, List<String[]>>> aminoAcidResidues = new ArrayList<>(pdbData.entrySet());
        for (int i = 0; i < aminoAcidResidues.size(); i++) {
            Double firstAminoAcidResidueKey = aminoAcidResidues.get(i).getKey();
            addNewHBoundRecord(firstAminoAcidResidueKey);
            Double secondAminoAcidResidueKey = findSecondAminoAcidResidueKey(aminoAcidResidues, firstAminoAcidResidueKey, turn, i);
            if (secondAminoAcidResidueKey != null) {
                boolean isHBoundExist = alphaHelixService.isHBoundExist(
                        pdbData, firstAminoAcidResidueKey, secondAminoAcidResidueKey, preSecondAminoAcidResidueKey);
                updateHBoundsDescription(firstAminoAcidResidueKey, secondAminoAcidResidueKey, isHBoundExist, turn);
            }
        }
    }

    public Double findSecondAminoAcidResidueKey(List<Map.Entry<Double, List<String[]>>> aminoAcidResidues,
                                                 Double firstAminoAcidResidueKey, Double turn, int index) {
        preSecondAminoAcidResidueKey = null;
        int aminoAcidResiduesSize = aminoAcidResidues.size();
        for (int i = 1, loopTime = 1; i <= turn; i++, loopTime++){
            int secondIndex = index + loopTime;
            boolean isExistRecord = aminoAcidResiduesSize > secondIndex;
            Double secondAminoAcidResidueKey = isExistRecord ? aminoAcidResidues.get(secondIndex).getKey() : null;
            if (secondAminoAcidResidueKey != null) {
                if (secondAminoAcidResidueKey > firstAminoAcidResidueKey + turn) {
                    break;
                } else if(secondAminoAcidResidueKey - i > firstAminoAcidResidueKey) {
                    i = (int) (secondAminoAcidResidueKey - firstAminoAcidResidueKey);
                }
                if (secondAminoAcidResidueKey == firstAminoAcidResidueKey + turn || i == turn) {
                    preSecondAminoAcidResidueKey = aminoAcidResidues.get(secondIndex-1).getKey();
                    return secondAminoAcidResidueKey;
                }
            } else break;
        }
        return null;
    }

    private void updateHBoundsDescription(Double firstAminoAcidResidueKey, Double secondAminoAcidResidueKey,
                                          boolean isHBoundExist, Double turn) {
        Set<Double[]> firstAminoAcidRelations = hBoundsDescription.get(firstAminoAcidResidueKey);
        Set<Double[]> secondAminoAcidRelations = hBoundsDescription.get(secondAminoAcidResidueKey);
        if (firstAminoAcidRelations == null) {
            Set<Double[]> newAminoAcidRelations = new HashSet<>();
            addNewValue(isHBoundExist, newAminoAcidRelations, firstAminoAcidResidueKey, secondAminoAcidResidueKey, turn);
        } else {
            addNewValue(isHBoundExist, firstAminoAcidRelations, firstAminoAcidResidueKey, secondAminoAcidResidueKey, turn);
        }

        if (secondAminoAcidRelations == null) {
            Set<Double[]> newAminoAcidRelations = new HashSet<>();
            addNewValue(isHBoundExist, newAminoAcidRelations, secondAminoAcidResidueKey, firstAminoAcidResidueKey, turn);
        } else {
            addNewValue(isHBoundExist, secondAminoAcidRelations, secondAminoAcidResidueKey, firstAminoAcidResidueKey, turn);
        }
    }

    private void addNewValue(boolean isHBoundExist, Set<Double[]> aminoAcidRelations, Double firstAminoAcidResidueKey,
                             Double secondAminoAcidResidueKey, Double turn) {
        if(isHBoundExist) {
            Double[] aminoAcidData = new Double[2];
            aminoAcidData[0] = secondAminoAcidResidueKey;
            aminoAcidData[1] = turn;
            aminoAcidRelations.add(aminoAcidData);
        }
        hBoundsDescription.put(firstAminoAcidResidueKey, aminoAcidRelations);
        secondaryStructure.put(firstAminoAcidResidueKey, "");
    }

    private void addNewHBoundRecord(Double aminoAcidResidueKey) {
        boolean isRecordExist = hBoundsDescription.get(aminoAcidResidueKey) != null;
        if (!isRecordExist) {
            hBoundsDescription.put(aminoAcidResidueKey, new HashSet<>());
            secondaryStructure.put(aminoAcidResidueKey, null);
        }
    }

    private void prepareFinalDsspData(SortedMap<Double, List<String[]>> pdbData) {
        List<Map.Entry<Double, String>> secondaryStructureEntrySet = new ArrayList<>(secondaryStructure.entrySet());
        for(Map.Entry<Double, String> secondaryConfig : secondaryStructureEntrySet) {
            finalDsspData.add(secondaryConfig.getValue());
            finalDsspData.add(pdbData.get(secondaryConfig.getKey()).get(0)[1]);
            finalDsspData.add(pdbData.get(secondaryConfig.getKey()).get(0)[3]);
        }
    }

    private void secondaryStructureDeterminate() {
        List<Map.Entry<Double, Set<Double[]>>> hBoundsDescriptionEntrySet = new ArrayList<>(hBoundsDescription.entrySet());
        Double firstAminoAcid = 0.0;
        for(int i = 0; i < hBoundsDescriptionEntrySet.size(); i++) {
            Map.Entry<Double, Set<Double[]>> hBoundConfig = hBoundsDescriptionEntrySet.get(i);
            firstAminoAcid = hBoundConfig.getKey();
            Set<Double[]> relations = hBoundConfig.getValue();
            FIRST_RELATIONS: for (Double[] relatedAminoAcid : relations) {
                Double secondAminoAcid = relatedAminoAcid[0];
                Double stepAminoAcid = relatedAminoAcid[1];
                if (ALFA_HELIX.contains(stepAminoAcid)){
                    Set<Double> alfaHelix = new HashSet<>();
                    alfaHelix.add(firstAminoAcid);
                    alfaHelix.add(secondAminoAcid);
                    for(int sub_i = 1; sub_i <= stepAminoAcid; sub_i++){
                        if (hBoundsDescriptionEntrySet.size() > i + sub_i) {
                            Double sub_firstAminoAcid = hBoundsDescriptionEntrySet.get(i + sub_i).getKey();
                            alfaHelix.add(sub_firstAminoAcid);
                            for (Double[] sub_relatedAminoAcid : hBoundsDescriptionEntrySet.get(i + sub_i).getValue()) {
                                Double sub_secondAminoAcid = relatedAminoAcid[0];
                                if (secondAminoAcid + stepAminoAcid < sub_secondAminoAcid) {
                                    break;
                                } else if (sub_relatedAminoAcid[1].equals(stepAminoAcid) && sub_firstAminoAcid < secondAminoAcid) {
                                    int j = i;
                                    do {
                                        secondaryStructure.put(hBoundsDescriptionEntrySet.get(j).getKey(), "H");
                                        j++;
                                    } while (!hBoundsDescriptionEntrySet.get(j).getKey().equals(sub_secondAminoAcid));
                                    break FIRST_RELATIONS;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
