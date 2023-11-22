package Approach_1_Code;
import java.util.HashMap;
import java.util.Map;

public class MainMatcher {
    public static void main(String[] args) {
        
        Map<String,String> patientFiles = Map.of("Patient1", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_1.ann,R2", 
                                                 "Patient2", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_2.ann,T1",
                                                 "Patient3", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_3.ann,O2",
                                                 "Patient5", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_5.ann,O1",
                                                 "Patient6", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_6.ann,O1",
                                                 "Patient7", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_7.ann,R2",
                                                 "Patient8", "Approach_1_Code/Approach_1_Test_Files/sample_patient_anno_input_7.ann,R3");
        Map<String,String> clinicalTrialFiles = Map.of("Trial1", "Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_1.ann,O1", 
                                                       "Trial2","Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_2.ann,O1",
                                                       "Trial3", "Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_3.ann,O2",
                                                       "Trial5", "Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_5.ann,O1",
                                                       "Trial6", "Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_6.ann,O1",
                                                       "Trial7", "Approach_1_Code/Approach_1_Test_Files/sample_ct_anno_input_5.ann,T7");
        Map<String,Boolean> expectedMatchings = Map.of("Patient1,Trial1", true, 
                                                       "Patient2,Trial2", true,
                                                       "Patient3,Trial3", true,
                                                       "Patient5,Trial5", true,
                                                       "Patient6,Trial5", true,
                                                       "Patient8,Trial7", true);

        Map<String,Boolean> expectedMatchings2 = Map.of("Patient7,Trial6", true);
        
        Map<String,Boolean> expected = new HashMap<>(expectedMatchings);                                         
        expected.putAll(new HashMap<>(expectedMatchings2));

        QueryProcessor queryProcessor = new QueryProcessor(patientFiles, clinicalTrialFiles, expected);
        System.out.println("Accuracy for this query is: " + queryProcessor.calculateAccuracyForPatientTrialMatchings());
    }
}
