package com.accelerator.services.implementations;

import com.accelerator.services.PicThirdPartyService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("picThirdPartyService")
public class PicThirdPartyServiceImpl implements PicThirdPartyService {

    @Override
    public List<String> getPicContext(MultipartFile pdbFile) {
        return null;
    }
}
