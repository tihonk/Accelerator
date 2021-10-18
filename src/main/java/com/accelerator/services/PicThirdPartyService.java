package com.accelerator.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PicThirdPartyService {

    List<String> getPicContext(MultipartFile pdbFile) throws IOException;
}
