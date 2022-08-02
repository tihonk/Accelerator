package com.accelerator.services;

import com.accelerator.dto.HydrogenAccuracyResponse;
import java.util.List;
import java.util.SortedMap;

public interface HydrogenAccuracyService {

    HydrogenAccuracyResponse calculateHydrogenAccuracyService(SortedMap<Double, List<String[]>> pdbData);
}
