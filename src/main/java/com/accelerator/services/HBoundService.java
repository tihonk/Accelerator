package com.accelerator.services;

import java.util.List;
import java.util.SortedMap;

public interface HBoundService {

    boolean isHBoundExist(SortedMap<Double, List<String[]>> pdbData,
                          Double co_residueKey,
                          Double nh_residueKey,
                          Double pre_nh_ResidueKey);
}
