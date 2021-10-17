package com.accelerator.facades;

import java.util.List;

public interface XlsxFillingFacade {

    void processOneSheet(List<String> values, int sheet, String filePath) throws Exception;
}
