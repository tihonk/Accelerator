package com.accelerator.convertors.impl;

import com.accelerator.convertors.SubNumberConvertor;
import org.springframework.stereotype.Service;

@Service("subNumberConvertor")
public class SubNumberConvertorImpl implements SubNumberConvertor {

    @Override
    public String convertToPointDigit(String litterSubNumber) {
        if(litterSubNumber.equalsIgnoreCase("A")) {
            return ".1";
        } else if(litterSubNumber.equalsIgnoreCase("B")) {
            return ".2";
        } else if(litterSubNumber.equalsIgnoreCase("C")) {
            return ".3";
        } else if(litterSubNumber.equalsIgnoreCase("D")) {
            return ".4";
        } else if(litterSubNumber.equalsIgnoreCase("E")) {
            return ".5";
        }
        return null;
    }
}
