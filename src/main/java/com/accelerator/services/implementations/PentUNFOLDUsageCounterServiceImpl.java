package com.accelerator.services.implementations;

import com.accelerator.services.PentUNFOLDUsageCounterService;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service("pentUNFOLDUsageCounterService")
public class PentUNFOLDUsageCounterServiceImpl implements PentUNFOLDUsageCounterService {

    private static final String PENT_UNFOLD_USES = "Number of uses of the algorithm: %s";
    private static long usesPentUNFOLDAlgorithm = 0;

    public void incrementCounter() {
        usesPentUNFOLDAlgorithm++;
        System.out.println(format(PENT_UNFOLD_USES, usesPentUNFOLDAlgorithm));
    }
}
