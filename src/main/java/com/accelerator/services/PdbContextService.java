package com.accelerator.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PdbContextService {

    String getPdbContext(MultipartFile pdbFile) throws IOException;
}
