package com.example.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MysteryConfig
{
    public List<String> getAminoAcids(String charAminoAcids)
    {
        charAminoAcids.replaceAll("[^A-Za-z]", "");
        boolean readingStarted = false;
        int codonNumber = 0;
        String startCodon = "";
        String beforeStarted = "";
        List<String> aminoAcids = new ArrayList<>();

        char [] charArray = charAminoAcids.toCharArray();
        Character[] newCharArray = IntStream.range(0, charArray.length)
            .mapToObj(i -> charArray[i])
            .toArray(Character[]::new);
        Arrays.sort(newCharArray, Collections.reverseOrder());

        List<Character> characters = new ArrayList<>();
        Collections.addAll(characters, newCharArray);

        while (!readingStarted || Objects.isNull(characters.get(codonNumber)))
        {
            while(Objects.requireNonNull(startCodon).length() < 3 && codonNumber < characters.size()+3)
            {
                startCodon += characters.get(codonNumber);
                beforeStarted += characters.get(codonNumber);
                codonNumber ++;
            }

            if ("AUG".equals(startCodon))
            {
                readingStarted = true;
                aminoAcids.add(beforeStarted + " —");
                codonNumber += 3;
            }
            codonNumber -= 2;
            startCodon = "";
        }

        if (readingStarted)
        {
            String aminoAcidEncoding = "";
            while (codonNumber < characters.size()-2)
            {
                while(Objects.requireNonNull(aminoAcidEncoding).length() < 3)
                {
                    aminoAcidEncoding += characters.get(codonNumber);
                    codonNumber ++;
                }

                if (aminoAcidEncoding.equals("GGU") || aminoAcidEncoding.equals("GGC")
                    || aminoAcidEncoding.equals("GGA") || aminoAcidEncoding.equals("GGG")) {
                    aminoAcids.add("Glycine");
                }
                else if (aminoAcidEncoding.equals("GCU") || aminoAcidEncoding.equals("GCC")
                    || aminoAcidEncoding.equals("GCA") || aminoAcidEncoding.equals("GCG")) {
                    aminoAcids.add("Alanine");
                }
                else if (aminoAcidEncoding.equals("GUU") || aminoAcidEncoding.equals("GUC")
                    || aminoAcidEncoding.equals("GUA") || aminoAcidEncoding.equals("GUG")) {
                    aminoAcids.add("Valine");
                }
                else if (aminoAcidEncoding.equals("UUA") || aminoAcidEncoding.equals("UUG")
                    || aminoAcidEncoding.equals("CUU") || aminoAcidEncoding.equals("CUC")
                    || aminoAcidEncoding.equals("CUA")|| aminoAcidEncoding.equals("CUG")) {
                    aminoAcids.add("Leucine");
                }
                else if (aminoAcidEncoding.equals("AUU") || aminoAcidEncoding.equals("AUC")
                    || aminoAcidEncoding.equals("AUA")){
                    aminoAcids.add("Isoleucine");
                }
                else if (aminoAcidEncoding.equals("UCU") || aminoAcidEncoding.equals("UCC")
                    || aminoAcidEncoding.equals("UCA") || aminoAcidEncoding.equals("UCG")){
                    aminoAcids.add("Serine");
                }
                else if (aminoAcidEncoding.equals("ACU") || aminoAcidEncoding.equals("ACC")
                    || aminoAcidEncoding.equals("ACA") || aminoAcidEncoding.equals("ACG")){
                    aminoAcids.add("Threonine");
                }
                else if (aminoAcidEncoding.equals("GAU") || aminoAcidEncoding.equals("GAC")){
                    aminoAcids.add("Aspartic Acid");
                }
                else if (aminoAcidEncoding.equals("GAA") || aminoAcidEncoding.equals("GAG")){
                    aminoAcids.add("Glutamic Acid");
                }
                else if (aminoAcidEncoding.equals("AAU") || aminoAcidEncoding.equals("AAC")){
                    aminoAcids.add("Asparagine");
                }
                else if (aminoAcidEncoding.equals("CAA") || aminoAcidEncoding.equals("CAG")){
                    aminoAcids.add("Glutamine");
                }
                else if (aminoAcidEncoding.equals("AAA") || aminoAcidEncoding.equals("AAG")){
                    aminoAcids.add("Lysine");
                }
                else if (aminoAcidEncoding.equals("CGU") || aminoAcidEncoding.equals("CGC")
                    || aminoAcidEncoding.equals("CGA") || aminoAcidEncoding.equals("CGG")
                    || aminoAcidEncoding.equals("AGA") || aminoAcidEncoding.equals("AGG")){
                    aminoAcids.add("Arginine");
                }
                else if (aminoAcidEncoding.equals("UGU") || aminoAcidEncoding.equals("UGC")){
                    aminoAcids.add("Cysteine");
                }
                else if (aminoAcidEncoding.equals("AUG")){
                    aminoAcids.add("Methionine");
                }
                else if (aminoAcidEncoding.equals("UUU") || aminoAcidEncoding.equals("UUC")){
                    aminoAcids.add("Phenylalanine");
                }
                else if (aminoAcidEncoding.equals("UAU") || aminoAcidEncoding.equals("UAC")){
                    aminoAcids.add("Tyrosine");
                }
                else if (aminoAcidEncoding.equals("UGG")){
                    aminoAcids.add("Tryptophan");
                }
                else if (aminoAcidEncoding.equals("CAU") || aminoAcidEncoding.equals("CAC")){
                    aminoAcids.add("Histidine");
                }
                else if (aminoAcidEncoding.equals("CCU") || aminoAcidEncoding.equals("CCC")
                || aminoAcidEncoding.equals("CCA") || aminoAcidEncoding.equals("CCG")){
                    aminoAcids.add("Proline");
                }
                else {
                    aminoAcids.add(aminoAcidEncoding + characters.subList(codonNumber, characters.size()+1));

                }
                aminoAcidEncoding = "";
            }
        }
        else {
            aminoAcids.add("Nucleotide chain not recognized!");
        }

        return  aminoAcids;
    }
}
