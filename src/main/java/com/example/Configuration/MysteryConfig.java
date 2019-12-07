package com.example.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MysteryConfig
{
    public List<String> getAminoAcids(String charAminoAcids)
    {
        List<String> aminoAcids = new ArrayList<>();

        char [] charArray = charAminoAcids.toCharArray();
        for(char newChar : charArray){
            if (newChar == 'G' || newChar == 'g') {
                aminoAcids.add("Glycine");
            }
            if (newChar == 'A' || newChar == 'a'){
                aminoAcids.add("Alanine");
            }
            if (newChar == 'V' || newChar == 'v'){
                aminoAcids.add("Valine");
            }
            if (newChar == 'L' || newChar == 'l'){
                aminoAcids.add("Leucine");
            }
            if (newChar == 'I' || newChar == 'i'){
                aminoAcids.add("Isoleucine");
            }
            if (newChar == 'S' || newChar == 's'){
                aminoAcids.add("Serine");
            }
            if (newChar == 'T' || newChar == 't'){
                aminoAcids.add("Threonine");
            }
            if (newChar == 'D' || newChar == 'd'){
                aminoAcids.add("Aspartic Acid");
            }
            if (newChar == 'E' || newChar == 'e'){
                aminoAcids.add("Glutamic Acid");
            }
            if (newChar == 'N' || newChar == 'n'){
                aminoAcids.add("Asparagine");
            }
            if (newChar == 'Q' || newChar == 'q'){
                aminoAcids.add("Glutamine");
            }
            if (newChar == 'K' || newChar == 'k'){
                aminoAcids.add("Lysine");
            }
            if (newChar == 'R' || newChar == 'r'){
                aminoAcids.add("Arginine");
            }
            if (newChar == 'C' || newChar == 'c'){
                aminoAcids.add("Cysteine");
            }
            if (newChar == 'M' || newChar == 'm'){
                aminoAcids.add("Methionine");
            }
            if (newChar == 'F' || newChar == 'f'){
                aminoAcids.add("Phenylalanine");
            }
            if (newChar == 'Y' || newChar == 'y'){
                aminoAcids.add("Tyrosine");
            }
            if (newChar == 'W' || newChar == 'w'){
                aminoAcids.add("Tryptophan");
            }
            if (newChar == 'H' || newChar == 'h'){
                aminoAcids.add("Histidine");
            }
            if (newChar == 'P' || newChar == 'p'){
                aminoAcids.add("Proline");
            }
        }

        return  aminoAcids;
    }
}
