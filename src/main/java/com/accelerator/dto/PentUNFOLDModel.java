package com.accelerator.dto;

import java.util.List;

public class PentUNFOLDModel {

    private List<String> pdb;
    private List<String> dssp;
    private List<String> pic;

    public PentUNFOLDModel() {
    }

    public List<String> getPdb() {
        return pdb;
    }

    public void setPdb(List<String> pdb) {
        this.pdb = pdb;
    }

    public List<String> getDssp() {
        return dssp;
    }

    public void setDssp(List<String> dssp) {
        this.dssp = dssp;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}
