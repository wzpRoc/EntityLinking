package org.ailab.entityLinking_old.modelOfDocGlobal;

import weka.core.Instance;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 上午1:20
 * Desc:
 */
public class CandidateEntityPermutationClassificationResult implements Comparable<CandidateEntityPermutationClassificationResult> {
    public CandidateEntityPermutation candidateEntityPermutation;
    public Instance instance;
    public double probability;

    public CandidateEntityPermutationClassificationResult(CandidateEntityPermutation candidateEntityPermutation, Instance instance, double probability) {
        this.candidateEntityPermutation = candidateEntityPermutation;
        this.instance = instance;
        this.probability = probability;
    }

    @Override
    public int compareTo(CandidateEntityPermutationClassificationResult o) {
        double diff = this.probability - o.probability;
        if(diff<0) {
            return -1;
        } else if(diff==0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return candidateEntityPermutation.toString()+" "+probability;
    }
}
