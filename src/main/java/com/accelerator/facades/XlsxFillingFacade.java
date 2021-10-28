package com.accelerator.facades;

import com.accelerator.dto.PentUNFOLDModel;

import java.util.ArrayList;

public interface XlsxFillingFacade {

    void fill2DFile(PentUNFOLDModel pentUNFOLDModel, String name, String chain) throws Exception;

    void fill3DFile(PentUNFOLDModel pentUNFOLDModel, String name,
                    ArrayList<String> picResult, String chain) throws Exception;
}
