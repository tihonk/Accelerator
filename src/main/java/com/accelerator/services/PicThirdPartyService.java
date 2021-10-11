package com.accelerator.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PicThirdPartyService {

    String getPicContext(MultipartFile pdbFile) throws IOException;
}
