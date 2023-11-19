import java.util.Map;

public class MainMatcher {
    
    public static void main(String[] args) {

        Map<String,String> patientFiles = Map.of("Patient1", "sample_patient_anno_input_1.ann");
        Map<String,String> clinicalTrialFiles = Map.of("Trial1", "sample_ct_anno_input_1.ann");
        Map<String,Boolean> trueMatchings = Map.of("Patient1,Trial1", true);

        QueryProcessor queryProcessor = new QueryProcessor(patientFiles, clinicalTrialFiles, trueMatchings);
        System.out.println("Accuracy for this query is: " + queryProcessor.calculateAccuracyForPatientTrialMatchings());
    }
}
