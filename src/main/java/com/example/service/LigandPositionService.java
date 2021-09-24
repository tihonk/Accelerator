package com.example.service;

import com.example.dto.AminoAcid;
import com.example.dto.Ligand;

import java.util.List;

public interface LigandPositionService {

    List<AminoAcid> getRelatedAminoAcids(Ligand ligand);
}
