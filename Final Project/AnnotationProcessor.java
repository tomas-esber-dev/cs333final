import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AnnotationProcessor {

    static Map<String, String> relationships = new HashMap<>();

    static List<LogicalEntity> entities = new ArrayList<>();

    static Map<String, LogicalEntity> logicalMap = new HashMap<>();

    static TAnnotation DUMMY = 
             new TAnnotation("Dummy", "Dummy", "-1", "-1", "Dummy");


    // Main method for testing purposes
	public static void main(String[] args) {
    List<String> annotations = readAnnotations("sample_ct_anno_input_1.ann");

    createRelationships(annotations);

    System.out.println(relationships);
    // System.out.println(((AsteriskAnnotation) map.get("O1")).getAnnotationType());
    // System.out.println(((AsteriskAnnotation) map.get("O1")).getArgOne().annotationId);

        traverseMap(logicalMap.get("O1"));
        System.out.println();
        // traverseMap(map.get("T3"));
        // traverseMap(map.get("T3"));

        LogicalEntity a = logicalMap.get("O1");

        TAnnotation t1 = new TAnnotation("T1", "Condition", "0", "3", "metastatic carcinoid tumors");
        TAnnotation t2 = new TAnnotation("T2", "Procedure", "0", "3", "biopsy");
        TAnnotation t3 = new TAnnotation("T3", "Value", "0", "3", "proven");

        RelationAnnotation r1 = new RelationAnnotation("Has_value", t3, t2);
        LogicalEntity r2 = new RelationAnnotation("AND", t1, r1);
    }



    /**
     * Performs an in-order traversal of our "binary tree-like" map.
     * @param entity root node of our tree
     */
    private static void traverseMap(LogicalEntity entity) {
        if (entity == null) return;
        traverseMap(entity.argOne);
        System.out.println(entity.annotationId + " " + entity.annotationType);
        traverseMap(entity.argTwo);
    }

    /**
     * Public facing method for creating a tree-structured logical entity out of a file path.
     * @param filePath string with local file path with annotations
     * @param treeRoot key in the map for the tree root you want to search through
     * @return constructed tree with distinct node objects
     */
    public static LogicalEntity createLogicalEntity(String filePath, String treeRoot) {
        List<String> annotations = readAnnotations(filePath);
        createRelationships(annotations);
        return logicalMap.get(treeRoot);
    }

    /**
     * Main logical method containing loop for building nodes out of the annotations.
     * @param annotations list of annotations in string form.
     */
    private static void createRelationships(List<String> annotations) {

        for (String annotation : annotations) {
            char annoType = annotation.charAt(0);
            
            if (annoType == 'T') handleAnnotationTType(annotation);
            else if (annoType == 'R') handleAnnotationRType(annotation);
            else if (annoType == 'O') handleAnnotationAsteriskType(annotation);
        }
    }

    /**
     * Helper method for creating asterisk nodes (e.g., OR node).
     * @param annotation list of annotations in string form.
     */
    private static void handleAnnotationAsteriskType(String annotation) {
        String annotationId = annotation.substring(0, 3).strip();

        annotation = annotation.substring(3).strip();

        String[] annotationElements = annotation.split(" ");

        String annotationType = annotationElements[0].strip();

        String[] args = new String[] { annotationElements[1].strip(), annotationElements[2].strip() };

        relationships.put(annotationId, annotationType+"("+relationships.getOrDefault(args[0], args[0])
                        +","+relationships.getOrDefault(args[1], args[1])+")");
        
        AsteriskAnnotation annotation2 = new AsteriskAnnotation(annotationType, logicalMap.getOrDefault(args[0], DUMMY), logicalMap.getOrDefault(args[1], DUMMY));

        logicalMap.put(annotationId, annotation2);
    }

    /**
     * Helper method for creating relation nodes (e.g., Has_value node, AND node).
     * @param annotation list of annotations in string form.
     */
    private static void handleAnnotationRType(String annotation) {
        String relationId = annotation.substring(0, 3).strip();

        annotation = annotation.substring(3).strip();

        String[] annotationElements = annotation.split(" ");

        String relationType = annotationElements[0].strip();

        String[] args = new String[] { annotationElements[1].split(":")[1].strip() , 
                                                annotationElements[2].split(":")[1].strip() };

        relationships.putIfAbsent(relationId, relationships.getOrDefault(args[0], args[0])+","
            +relationships.getOrDefault(args[1], args[1]));

        // visual logical representation of tree
        if (!relationships.containsKey(args[0])) {
            relationships.put(args[0], args[0]);
        }
        relationships.put(args[0], relationId+"("+relationships.getOrDefault(args[0],args[0])+
        ","+relationships.getOrDefault(args[1],args[1])+")");


        RelationAnnotation relationAnnotation = new RelationAnnotation(relationType, logicalMap.getOrDefault(args[0], DUMMY), logicalMap.getOrDefault(args[1], DUMMY));
        logicalMap.put(relationId, relationAnnotation);

        if (!logicalMap.containsKey(args[0])) {
            logicalMap.put(args[0], DUMMY);
        }
        logicalMap.put(args[0], relationAnnotation);
    }

    /**
     * Helper method for creating T nodes (e.g., Condition node, Value node).
     * @param annotation list of annotations in string form.
     */
    private static void handleAnnotationTType(String annotation) {
        // parse annotation id
        String annotationId = annotation.substring(0, 3).strip();

        annotation = annotation.substring(3).strip();
        
        String[] annotationElements = annotation.split(" ");
        String annotationType = annotationElements[0].strip();
        String[] annotationIndexes = new String[] { annotationElements[1], annotationElements[2] };

        String annotationText = "";

        for (int i = 3; i < annotationElements.length; i++) {
            annotationText += " " + annotationElements[i];
        }

        annotationText = annotationText.strip();

        relationships.putIfAbsent(annotationId, annotationId);

        TAnnotation annotation2 = new TAnnotation(annotationId, annotationType, annotationIndexes[0],  annotationIndexes[1], annotationText);

        logicalMap.putIfAbsent(annotationId, annotation2);
    }

    /**
     * Read in .txt and .ann files for labeled patient data and clinical trial data.
     * @param fileName local file path string for txt file
     * @return List<String> of lines in the file
     */
    private static List<String> readAnnotations(String fileName) {
        List<String> annos = new ArrayList<>();
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();

			while (line != null) {
				// read next line
                annos.add(line);
                line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return annos;
	}
}