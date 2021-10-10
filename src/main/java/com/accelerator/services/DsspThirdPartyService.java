package com.accelerator.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DsspThirdPartyService {

    String getDsspContext(MultipartFile pdbFile) throws IOException;
}
