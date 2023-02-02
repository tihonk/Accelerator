package com.accelerator.services.implementations

import com.accelerator.services.BendService
import com.accelerator.services.DistanceService
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.pow

@Service
class BendServiceImpl(private val distanceService: DistanceService) : BendService {

    private val CARBON_A = "CA"
    private val COS_70 = 0.24202014332

    override fun isBend(pdbData: SortedMap<Double, MutableList<Array<String>>>?,
                        preResidueKey: Double?,
                        residueKey: Double?,
                        postResidueKey: Double?): Boolean {
        val preAminoAcidResidue = pdbData?.get(preResidueKey)
        val aminoAcidResidue = pdbData?.get(residueKey)
        val postSecondAminoAcidResidue = pdbData?.get(postResidueKey)

        val first_C = getAtomCoordinates(findData(preAminoAcidResidue, CARBON_A))
        val midl_C = getAtomCoordinates(findData(aminoAcidResidue, CARBON_A))
        val last_C = getAtomCoordinates(findData(postSecondAminoAcidResidue, CARBON_A))

        if (Objects.nonNull(first_C) && Objects.nonNull(midl_C) && Objects.nonNull(last_C)) {
            val a_length: Double = distanceService.countDistance(first_C, midl_C)
            val b_length: Double = distanceService.countDistance(midl_C, last_C)
            val c_length: Double = distanceService.countDistance(last_C, first_C)
            val cos_b: Double = findCosByLegs(a_length, b_length, c_length)
            return cos_b > COS_70
        }

        return false
    }

    private fun findData(aminoAcidResidue: MutableList<Array<String>>?, atom: String): Array<String>? {
        for (atomData in aminoAcidResidue!!) {
            if (atomData[0] == atom) {
                return atomData
            }
        }
        return null
    }

    private fun getAtomCoordinates(atom: Array<String>?): Array<Double?> {
        val atomCoordinates = arrayOfNulls<Double>(3)
        atomCoordinates[0] = atom!![4].toDouble()
        atomCoordinates[1] = atom[5].toDouble()
        atomCoordinates[2] = atom[6].toDouble()
        return atomCoordinates
    }

    private fun findCosByLegs(a_length: Double, b_length: Double, c_length: Double): Double {
        return (a_length.pow(2.0) + b_length.pow(2.0) - c_length.pow(2.0)) / (2 * a_length * b_length)
    }
}