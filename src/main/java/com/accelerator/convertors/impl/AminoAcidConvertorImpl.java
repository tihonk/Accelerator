package com.accelerator.convertors.impl;

import com.accelerator.convertors.AminoAcidConvertor;
import org.springframework.stereotype.Service;

@Service("aminoAcidConvertor")
public class AminoAcidConvertorImpl implements AminoAcidConvertor {

    @Override
    public String convertToShort(String longName) {
        if(longName.equalsIgnoreCase("ALA")) {
            return "A";
        } else if(longName.equalsIgnoreCase("ILE")) {
            return "I";
        } else if(longName.equalsIgnoreCase("LEU")) {
            return "L";
        } else if(longName.equalsIgnoreCase("MET") || longName.equalsIgnoreCase("MSE")) {
            return "M";
        } else if(longName.equalsIgnoreCase("VAL")) {
            return "V";
        } else if(longName.equalsIgnoreCase("PHE")) {
            return "F";
        } else if(longName.equalsIgnoreCase("TRP")) {
            return "W";
        } else if(longName.equalsIgnoreCase("TYR")) {
            return "Y";
        } else if(longName.equalsIgnoreCase("ASN")) {
            return "N";
        } else if(longName.equalsIgnoreCase("CYS") || longName.equalsIgnoreCase("CSO")) {
            return "C";
        } else if(longName.equalsIgnoreCase("GLN")) {
            return "Q";
        } else if(longName.equalsIgnoreCase("SER")) {
            return "S";
        } else if(longName.equalsIgnoreCase("THR")) {
            return "T";
        } else if(longName.equalsIgnoreCase("ASP")) {
            return "D";
        } else if(longName.equalsIgnoreCase("GLU")) {
            return "E";
        } else if(longName.equalsIgnoreCase("ARG")) {
            return "R";
        } else if(longName.equalsIgnoreCase("HIS")) {
            return "H";
        } else if(longName.equalsIgnoreCase("LYS")) {
            return "K";
        } else if(longName.equalsIgnoreCase("GLY")) {
            return "G";
        } else if(longName.equalsIgnoreCase("PRO")) {
            return "P";
        }
        return null;
    }
}
