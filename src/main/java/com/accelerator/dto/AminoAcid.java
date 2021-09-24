package com.accelerator.dto;

public class AminoAcid {

    private int id;
    private String aminoAcidName;
    private Long aminoAcidResiduePDBNumber;
    private String aminoAcidAtom;
    private Double nearestAtomDistance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAminoAcidName() {
        return aminoAcidName;
    }

    public void setAminoAcidName(String aminoAcidName) {
        this.aminoAcidName = aminoAcidName;
    }

    public Long getAminoAcidResiduePDBNumber() {
        return aminoAcidResiduePDBNumber;
    }

    public void setAminoAcidResiduePDBNumber(Long aminoAcidResiduePDBNumber) {
        this.aminoAcidResiduePDBNumber = aminoAcidResiduePDBNumber;
    }

    public String getAminoAcidAtom() {
        return aminoAcidAtom;
    }

    public void setAminoAcidAtom(String aminoAcidAtom) {
        this.aminoAcidAtom = aminoAcidAtom;
    }

    public Double getNearestAtomDistance() {
        return nearestAtomDistance;
    }

    public void setNearestAtomDistance(Double nearestAtomDistance) {
        this.nearestAtomDistance = nearestAtomDistance;
    }
}
