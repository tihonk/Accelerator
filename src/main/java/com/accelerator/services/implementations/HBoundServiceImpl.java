package com.accelerator.services.implementations;

import com.accelerator.services.HBoundService;
import com.accelerator.services.DistanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.SortedMap;

import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;
import static java.util.Objects.nonNull;

@Service("hBoundService")
public class HBoundServiceImpl implements HBoundService {

    private static final Double MIN_E_H_BOUND = -0.5;
    private static final Double N_H_DISTANCE = 1.01;
    private static final String CARBON = "C";
    private static final String CARBON_A = "CA";
    private static final String OXYGEN = "O";
    private static final String NITROGEN = "N";
    private static final String HYDROGEN = "H";

    @Resource
    private DistanceService distanceService;

    private Double leadingDistance;

    @Override
    public boolean isHBoundExist(SortedMap<Double, List<String[]>> pdbData, Double co_residueKey,
                                 Double nh_residueKey, Double pre_nh_ResidueKey) {
        
        if (nh_residueKey - pre_nh_ResidueKey <= 1) {
            List<String[]> firstAminoAcidResidue = pdbData.get(co_residueKey);
            List<String[]> secondAminoAcidResidue = pdbData.get(nh_residueKey);
            List<String[]> preSecondAminoAcidResidue = pdbData.get(pre_nh_ResidueKey);
            
            if (isProline(firstAminoAcidResidue) || isProline(secondAminoAcidResidue)) {
                return false;
            }

            String[] first_C = findData(firstAminoAcidResidue, CARBON);
            String[] first_O = findData(firstAminoAcidResidue, OXYGEN);
            String[] second_N = findData(secondAminoAcidResidue, NITROGEN);
            String[] second_H = findData(secondAminoAcidResidue, HYDROGEN);

            if (second_H == null) {
                String[] preSecond_C = findData(preSecondAminoAcidResidue, CARBON);
                String[] second_C = findData(secondAminoAcidResidue, CARBON_A);
                second_H = findHydrogenData(second_C, second_N, preSecond_C);
            }

            if (nonNull(first_C) && nonNull(first_O) && nonNull(second_N)) {
                Double interactionEnergy = countEnergy(first_C, first_O, second_N, second_H);
                return interactionEnergy < MIN_E_H_BOUND;
            }
        }
        return false;
    }

    private boolean isProline(List<String[]> secondAminoAcidResidue) {
        return secondAminoAcidResidue.get(0)[1].equals("P");
    }

    private String[] findData(List<String[]> firstAminoAcidResidue, String atom) {
        for (String[] atomData : firstAminoAcidResidue) {
            if(atomData[0].equals(atom)){
                return atomData;
            }
        }
        return null;
    }

    private String[] findHydrogenData(String[] second_C, String[] second_N, String[] preSecond_C) {
        Double[] a_coordinates = new Double[3];
        a_coordinates[0]= getMean(second_C[4], preSecond_C[4]);
        a_coordinates[1]= getMean(second_C[5], preSecond_C[5]);
        a_coordinates[2]= getMean(second_C[6], preSecond_C[6]);

        Double[] n_coordinates = getAtomCoordinates(second_N);
        String[] second_H = new String[7];
        second_H[0] = HYDROGEN;
        second_H[1] = second_N[1];
        second_H[2] = second_N[2];
        second_H[3] = second_N[3];
        leadingDistance = distanceService.countDistance(a_coordinates, n_coordinates);
        for (int i = 4; i < 7; i++) {
            second_H[i] = calculateCoordinate(a_coordinates[i-4], second_N[i]);
        }
        leadingDistance = null;
        return second_H;
    }

    private Double countEnergy(String[] first_C, String[] first_O,
                               String[] second_N, String[] second_H) {
        Double[] c_coordinates = getAtomCoordinates(first_C);
        Double[] o_coordinates = getAtomCoordinates(first_O);
        Double[] n_coordinates = getAtomCoordinates(second_N);
        Double[] h_coordinates = getAtomCoordinates(second_H);

        Double o_n_distance = distanceService.countDistance(o_coordinates, n_coordinates);
        Double c_h_distance = distanceService.countDistance(c_coordinates, h_coordinates);
        Double o_h_distance = distanceService.countDistance(o_coordinates, h_coordinates);
        Double c_n_distance = distanceService.countDistance(c_coordinates, n_coordinates);

        Double energy = 0.42 * 0.2 * ((1/o_n_distance) + (1/c_h_distance) - (1/o_h_distance) - (1/c_n_distance)) * 332;

        return energy;
    }

    private Double getMean(String first_Coordinate, String second_Coordinate) {
        return (parseDouble(first_Coordinate) + parseDouble(second_Coordinate)) / 2;
    }
    private String calculateCoordinate(Double meanCoordinate, String nCoordinate) {
        Double difference = meanCoordinate - parseDouble(nCoordinate);
        Double finalDifference = (leadingDistance + N_H_DISTANCE) * difference / leadingDistance;
        return valueOf(meanCoordinate - finalDifference);
    }

    private Double[] getAtomCoordinates(String[] second_n) {
        Double[] atom_coordinates = new Double[3];
        atom_coordinates[0] = parseDouble(second_n[4]);
        atom_coordinates[1]= parseDouble(second_n[5]);
        atom_coordinates[2]= parseDouble(second_n[6]);
        return atom_coordinates;
    }
}
