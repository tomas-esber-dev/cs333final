package Approach_1_Code;
import java.util.Map;

public class MainMatcher {
    public static void main(String[] args) {
        
        Map<String,String> patientFiles = Map.of("Patient1", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_1.ann,R2", 
                                                 "Patient2", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_2.ann,T1");
        Map<String,String> clinicalTrialFiles = Map.of("Trial1", "Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_1.ann,O1", 
                                                       "Trial2","Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_2.ann,O1");
        Map<String,Boolean> trueMatchings = Map.of("Patient1,Trial1", true, 
                                                   "Patient1,Trial2", false,
                                                   "Patient2,Trial1", false,
                                                   "Patient2,Trial2", true);

        QueryProcessor queryProcessor = new QueryProcessor(patientFiles, clinicalTrialFiles, trueMatchings);
        System.out.println("Accuracy for this query is: " + queryProcessor.calculateAccuracyForPatientTrialMatchings());
    }
}
