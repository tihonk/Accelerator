package com.accelerator.services.implementations;

import com.accelerator.services.PdbContextService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service("pdbContextService")
public class PdbContextServiceImpl implements PdbContextService {

    @Override
    public String getPdbContext(MultipartFile pdbFile) throws IOException {
        String content = new String(pdbFile.getBytes(), StandardCharsets.UTF_8);
        int pdbContextStartIndex = content.indexOf("ATOM      1");
        return content.substring(pdbContextStartIndex);
    }
}
