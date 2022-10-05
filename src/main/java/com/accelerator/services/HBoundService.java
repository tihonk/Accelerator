package com.accelerator.services;

import com.accelerator.dto.AminoAcid;
import com.accelerator.dto.HydrogenAccuracy;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

public interface HBoundService {

    boolean isHBoundExist(SortedMap<Double, List<String[]>> pdbData,
                          Double co_residueKey,
                          Double nh_residueKey,
                          Double pre_nh_ResidueKey);

    void findHCoordinates(HydrogenAccuracy hydrogenAccuracy, List<Entry<Double, List<String[]>>> aminoAcidResidues,
                          SortedMap<Double, List<String[]>> pdbData, int index);

    void findAccuracyHBound(HydrogenAccuracy hydrogenAccuracy, SortedMap<Double, List<String[]>> pdbData,
                            Double co_residueKey,
                            Double nh_residueKey,
                            Double pre_nh_residueKey, List<AminoAcid> hBoundAminoAcids);
}
