package com.example.dto;

public class Ligand {

    private String ligandName;
    private Double maxAcceptableDistance;
    private String PDBFile;

    public String getLigandName() {
        return ligandName;
    }

    public void setLigandName(String ligandName) {
        this.ligandName = ligandName;
    }

    public Double getMaxAcceptableDistance() {
        return maxAcceptableDistance;
    }

    public void setMaxAcceptableDistance(Double maxAcceptableDistance) {
        this.maxAcceptableDistance = maxAcceptableDistance;
    }

    public String getPDBFile() {
        return PDBFile;
    }

    public void setPDBFile(String PDBFile) {
        this.PDBFile = PDBFile;
    }
}
