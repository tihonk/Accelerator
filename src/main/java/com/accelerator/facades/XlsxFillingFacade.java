package com.accelerator.facades;

import com.accelerator.dto.PentUNFOLDModel;

public interface XlsxFillingFacade {

    void fill2DFile(PentUNFOLDModel pentUNFOLDModel, String fileName) throws Exception;

    void fill3DFile(PentUNFOLDModel pentUNFOLDModel, String fileName) throws Exception;
}
