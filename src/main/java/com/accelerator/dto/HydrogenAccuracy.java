package com.accelerator.dto;

import java.util.List;
import java.util.Map;

public class HydrogenAccuracy {

    private String aminoAcidNumber;
    private String aminoAcid;
    private String previousAminoAcid;
    private String nextAminoAcid;
    private String hydrogenBoundAminoAcids;
    private String realXCoordinate;
    private String realYCoordinate;
    private String realZCoordinate;
    private String predictedXCoordinate;
    private String predictedYCoordinate;
    private String predictedZCoordinate;
    private Double distanceToReal;
    private Double nhDistance;
    private List<AminoAcid> hBoundAminoAcids;
    private List<Atom> nearestAtoms;

    public String getAminoAcidNumber() {
        return aminoAcidNumber;
    }

    public void setAminoAcidNumber(String aminoAcidNumber) {
        this.aminoAcidNumber = aminoAcidNumber;
    }

    public String getAminoAcid() {
        return aminoAcid;
    }

    public void setAminoAcid(String aminoAcid) {
        this.aminoAcid = aminoAcid;
    }

    public String getRealXCoordinate() {
        return realXCoordinate;
    }

    public void setRealXCoordinate(String realXCoordinate) {
        this.realXCoordinate = realXCoordinate;
    }

    public String getRealYCoordinate() {
        return realYCoordinate;
    }

    public void setRealYCoordinate(String realYCoordinate) {
        this.realYCoordinate = realYCoordinate;
    }

    public String getRealZCoordinate() {
        return realZCoordinate;
    }

    public void setRealZCoordinate(String realZCoordinate) {
        this.realZCoordinate = realZCoordinate;
    }

    public String getPredictedXCoordinate() {
        return predictedXCoordinate;
    }

    public void setPredictedXCoordinate(String predictedXCoordinate) {
        this.predictedXCoordinate = predictedXCoordinate;
    }

    public String getPredictedYCoordinate() {
        return predictedYCoordinate;
    }

    public void setPredictedYCoordinate(String predictedYCoordinate) {
        this.predictedYCoordinate = predictedYCoordinate;
    }

    public String getPredictedZCoordinate() {
        return predictedZCoordinate;
    }

    public void setPredictedZCoordinate(String predictedZCoordinate) {
        this.predictedZCoordinate = predictedZCoordinate;
    }

    public String getPreviousAminoAcid() {
        return previousAminoAcid;
    }

    public void setPreviousAminoAcid(String previousAminoAcid) {
        this.previousAminoAcid = previousAminoAcid;
    }

    public String getNextAminoAcid() {
        return nextAminoAcid;
    }

    public void setNextAminoAcid(String nextAminoAcid) {
        this.nextAminoAcid = nextAminoAcid;
    }

    public String getHydrogenBoundAminoAcids() {
        return hydrogenBoundAminoAcids;
    }

    public void setHydrogenBoundAminoAcids(String hydrogenBoundAminoAcids) {
        this.hydrogenBoundAminoAcids = hydrogenBoundAminoAcids;
    }

    public Double getDistanceToReal() {
        return distanceToReal;
    }

    public void setDistanceToReal(Double distanceToReal) {
        this.distanceToReal = distanceToReal;
    }

    public Double getNhDistance() {
        return nhDistance;
    }

    public void setNhDistance(Double nhDistance) {
        this.nhDistance = nhDistance;
    }

    public List<AminoAcid> gethBoundAminoAcids() {
        return hBoundAminoAcids;
    }

    public void sethBoundAminoAcids(List<AminoAcid> hBoundAminoAcids) {
        this.hBoundAminoAcids = hBoundAminoAcids;
    }

    public List<Atom> getNearestAtoms() {
        return nearestAtoms;
    }

    public void setNearestAtoms(List<Atom> nearestAtoms) {
        this.nearestAtoms = nearestAtoms;
    }
}
