package com.accelerator.services;

import java.util.List;
import java.util.SortedMap;

public interface AlphaHelixService {

    boolean isHBoundExist(SortedMap<Double, List<String[]>> pdbData,
                          Double firstAminoAcidResidueKey,
                          Double secondAminoAcidResidueKey,
                          Double preSecondAminoAcidResidueKey);
}
