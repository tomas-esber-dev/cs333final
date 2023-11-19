/*
 * Given a query A (i.e., a labeled patient health record) and a clinical trial B, this class will determine if the patient is 
 * eligible to participate in the clinical trial.
 * 
 * The workflow goes as follows.
 * 
 * 1) First, we create a logical representation of A by representing its core logical components as a binary tree. In this tree,
 * nodes are both logical and relational entities connected by edges. We create a logical tree representation of B, as well. Let's
 * call the logical tree representation of A as T(A) and T(B) for B.
 * 
 * 2) We then check to see if T(A) and T(B) are the same tree, or if T(A) is equal to any subtrees of T(B).
 * 
 * 3) If T(A) and T(B) are the same tree (or if T(A) is equal to any subtrees of T(B)), we determine that patient with health 
 * records A is eligible for clinical trial B.
 * 
 * A note on the concept of "equals". We calculate a similarity index between nodes a in A and nodes b in B to determine if they
 * are equal. For example, if a node A contains the text "COVID", and a corresponding node b in B contains the text "COVID-19", we
 * calculate similarityScore("COVID", "COVID-19") which generates a numerical value determining how similar both strings are. We can
 * choose an arbitrary threshhold to determine if we want to treat the strings above that threshold as equal or not.
 * 
 * See below for an example. Take the logical string: OR(R2(T1,R1(T3,T2)),T4). We represent this as a tree:
 *                    
 *                   OR
 *                   /\
 *                 R2  T4
 *                 /\
 *               T1 R1
 *                  /\
 *                 T3 T2
 * 
 * where nodes with R represent relations (e.g., AND, Has_value) and nodes with T represent entities (e.g., Condition, Value).
 */

import java.util.HashMap;
import java.util.Map;

public class QueryProcessor {
    
    Map<String,String> patientFiles;
    Map<String,String> clinicalTrialFiles;
    Map<String, Boolean> trueMatchings;

    QueryProcessor(Map<String,String> patientFiles, Map<String,String> clinicalTrialFiles, Map<String, Boolean> trueMatchings) {
        this.patientFiles = patientFiles;
        this.clinicalTrialFiles = clinicalTrialFiles;
        this.trueMatchings = trueMatchings;
    }

    /**
     * Calculate how accurate our matching methodology is for a given set of patients and clinical trials.
     * @return a score of our algorithmic accuracy
     */
    public double calculateAccuracyForPatientTrialMatchings() {
        Map<String,Boolean> predictedMatchings = findMatchingsForPatientsAndTrials();
        int totalSeen = 0;
        int correctlyMatched = 0;

        for (String patientTrialMatch : predictedMatchings.keySet()) {
            if (trueMatchings.containsKey(patientTrialMatch)) {
                if (trueMatchings.get(patientTrialMatch) == predictedMatchings.get(patientTrialMatch)) {
                    correctlyMatched++;
                }
                totalSeen++;
            }
        }
        if (totalSeen == 0) return -1; // error handling
        return correctlyMatched / totalSeen;
    }

    /**
     * Determine for each patient x in a set of patients X, if x is eligible for each trial y in a set of trials Y.
     * @return a map with all possible patient-trial matchings and if the patient is eligible or not for the trial.
     */
    public Map<String,Boolean> findMatchingsForPatientsAndTrials() {
        Map<String,Boolean> patientToTrialMappings = new HashMap<>();
        
        for (String patientId : patientFiles.keySet()) {
            LogicalEntity patient = AnnotationProcessor.createLogicalEntity(patientFiles.get(patientId), "R2");
            for (String trialId : clinicalTrialFiles.keySet()) {
                LogicalEntity trial = AnnotationProcessor.createLogicalEntity(clinicalTrialFiles.get(trialId), "O1");
                boolean isEligible = determineIfPatientEligibleForTrial(trial, patient);
                String key = patientId + "," + trialId;
                patientToTrialMappings.putIfAbsent(key, isEligible);
            }
        }
        return patientToTrialMappings;
    }

    public static boolean determineIfPatientEligibleForTrial(LogicalEntity trial, LogicalEntity patient) {
        return treesAreLogicallyEquivalent(trial, patient, 0.4);
    }

    /**
     * Check to see if two logical trees (with nodes: TAnnotation, RelationAnnotation, AsteriskAnnotation) are equal, or to see
     * if the any subtrees of the clinical trial tree are contained within the patient tree.
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean treesAreLogicallyEquivalent(LogicalEntity a, LogicalEntity b, double similarityThreshhold) {

        if (a == null) return false;
        if (b == null) return false;

        if (subTreesAreLogicallyEquivalent(a, b, similarityThreshhold)) return true;

        return  treesAreLogicallyEquivalent(a.argOne, b, similarityThreshhold) || 
                treesAreLogicallyEquivalent(a.argTwo, b, similarityThreshhold);
    }

    /**
     * Helper method for determining if two subtrees are logically equivalent by traversing through the trees in order.
     * 
     * @param a root node
     * @param b root node
     * @param similarityThreshhold threshhold for dtermining similarity of nodes
     * @return true if trees are equal or sufficiently "similar"
     */
    public static boolean subTreesAreLogicallyEquivalent(LogicalEntity a, LogicalEntity b, double similarityThreshhold) {

        if (a == null && b == null) return true;
        if (a == null || b == null) return false;


        boolean nodesAreEqual;

        if (a instanceof TAnnotation && b instanceof TAnnotation) {
            nodesAreEqual = calculateSimilarityScore((TAnnotation)a, (TAnnotation)b) >= similarityThreshhold;
        } else {
            nodesAreEqual = a.annotationId.equals(b.annotationId);
        }

        return  nodesAreEqual &&
                subTreesAreLogicallyEquivalent(a.argOne, b.argOne, similarityThreshhold) &&
                subTreesAreLogicallyEquivalent(a.argTwo, b.argTwo, similarityThreshhold);
    }

    /**
     * Similarity score matcher between two strings. In essence, this is intended to be a smart algorithm that does NOT use
     * machine learning to determine if two strings are equal or similar, but instead relies on traditional 
     * deterministic algorithmic strategies.
     * 
     * @param node1 leaf node being compared
     * @param node2 leaf node being compared
     * @return similarity score between the texts stored within both nodes
     */
    private static double calculateSimilarityScore(TAnnotation node1, TAnnotation node2) {
        String s1 = node1.annotationText;
        String s2 = node2.annotationText;

        // if strings are the exact same, then return 1.0
        if (s1.equals(s2)) {
            return 1.0;
        }
        // if either string is a substring of the other, then return 1.0
        if (s1.contains(s2) || s2.contains(s1)) {
            return 1.0;
        }

        double score1 = StringSimilarityUtils.nGramSimilarity(s1, s2, 3);
        double score2 = StringSimilarityUtils.sorensenDiceSimilarity(s1, s2);

        return Math.min(1.0, (score1 + score2) / 2.0);
    }
}
