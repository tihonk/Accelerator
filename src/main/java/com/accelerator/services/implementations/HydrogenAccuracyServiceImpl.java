package com.accelerator.services.implementations;

import com.accelerator.dto.AminoAcid;
import com.accelerator.dto.HydrogenAccuracy;
import com.accelerator.dto.HydrogenAccuracyResponse;
import com.accelerator.services.DistanceService;
import com.accelerator.services.HBoundService;
import com.accelerator.services.HydrogenAccuracyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("hydrogenAccuracyService")
public class HydrogenAccuracyServiceImpl implements HydrogenAccuracyService {

    @Resource
    HBoundService hBoundService;

    @Resource
    DistanceService distanceService;

    Double preSecondAminoAcidResidueKey;

    @Override
    public HydrogenAccuracyResponse calculateHydrogenAccuracyService(SortedMap<Double, List<String[]>> pdbData) {

        HydrogenAccuracyResponse response  = new HydrogenAccuracyResponse();

        List<HydrogenAccuracy> relatedAminoAcids = findHBounds(pdbData);
        response.setAllList(relatedAminoAcids);

        Map<String, Integer> composition = setComposition(relatedAminoAcids);
        response.setComposition(composition);

        Double averageNHDistance = getAverageNHDistance(relatedAminoAcids);
        response.setAverageNHDistance(averageNHDistance);

        Double averageHHDistance = getAverageHHDistance(relatedAminoAcids);
        response.setAverageHHDistance(averageHHDistance);

        return response;
    }

    private Map<String, Integer> setComposition(List<HydrogenAccuracy> relatedAminoAcids) {
        Map<String, Integer> composition = new HashMap<>();
        relatedAminoAcids.stream()
            .map(HydrogenAccuracy::getAminoAcid)
            .forEach(aminoAcids ->  {
                if (composition.containsKey(aminoAcids)){
                    composition.put(aminoAcids, composition.get(aminoAcids) + 1);
                } else {
                    composition.put(aminoAcids, 1);
                }
            });
        return composition;
    }

    private List<HydrogenAccuracy> findHBounds(SortedMap<Double, List<String[]>> pdbData) {
        List<HydrogenAccuracy> hydrogenAccuracies = new ArrayList<>();
        List<Map.Entry<Double, List<String[]>>> aminoAcidResidues = new ArrayList<>(pdbData.entrySet());
        for (int i = 0; i < aminoAcidResidues.size(); i++) {
            HydrogenAccuracy hydrogenAccuracy = new HydrogenAccuracy();
            hBoundService.findHCoordinates(hydrogenAccuracy, aminoAcidResidues, pdbData, i);

            List<AminoAcid> hBoundAminoAcids = new ArrayList<>();
            for(int j = 0; j < aminoAcidResidues.size(); j++) {
                if (Math.abs(j - i) > 2 && i > 0 && i < aminoAcidResidues.size() - 1) {
                    Double co_residueKey = aminoAcidResidues.get(j).getKey();
                    Double nh_residueKey = aminoAcidResidues.get(i).getKey();
                    Double pre_nh_ResidueKey = aminoAcidResidues.get(i-1).getKey();
                    hBoundService.findAccuracyHBound(hydrogenAccuracy, pdbData, co_residueKey, nh_residueKey, pre_nh_ResidueKey, hBoundAminoAcids);
                }
            }
            hydrogenAccuracy.sethBoundAminoAcids(hBoundAminoAcids);
            hydrogenAccuracies.add(hydrogenAccuracy);
        }
        return hydrogenAccuracies;
    }

    private Double getPreAminoAcidResidueKey(SortedMap<Double, List<String[]>> pdbData, int index) {
        return null;
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

    private Double getAverageNHDistance(List<HydrogenAccuracy> relatedAminoAcids) {
        Double [] countDistance = {0.0, 0.0};
        relatedAminoAcids.stream()
            .map(HydrogenAccuracy::getNhDistance)
            .forEach(distance -> {
                if (distance != null && distance > 0) {
                    countDistance[0] += distance;
                    countDistance[1] += 1.0;
                }
            });
        return countDistance[0] / countDistance[1];
    }

    private Double getAverageHHDistance(List<HydrogenAccuracy> relatedAminoAcids) {
        Double [] countDistance = {0.0, 0.0};
        relatedAminoAcids.stream()
            .map(HydrogenAccuracy::getDistanceToReal)
            .forEach(distance -> {
                if (distance != null && distance > 0) {
                    countDistance[0] += distance;
                    countDistance[1] += 1.0;
                }
            });
        return countDistance[0] / countDistance[1];
    }
}
