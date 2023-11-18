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

    static Map<String, LogicalEntity> map = new HashMap<>();

    static TAnnotation DUMMY = 
             new TAnnotation("Dummy", "Dummy", "-1", "-1", "Dummy");

	public static void main(String[] args) {
    List<String> annotations = readAnnotations("sample_anno_input_1.ann");

    createRelationships(annotations);

    System.out.println(relationships);
    // System.out.println(((AsteriskAnnotation) map.get("O1")).getAnnotationType());
    // System.out.println(((AsteriskAnnotation) map.get("O1")).getArgOne().annotationId);

        traverseMap(map.get("O1"));
        System.out.println();
        // traverseMap(map.get("T3"));
        // traverseMap(map.get("T3"));

        LogicalEntity a = map.get("O1");

        TAnnotation t1 = new TAnnotation("T1", "Condition", "0", "3", "metastatic carcinoid tumors");
        TAnnotation t2 = new TAnnotation("T2", "Procedure", "0", "3", "biopsy");
        TAnnotation t3 = new TAnnotation("T3", "Value", "0", "3", "proven");

        RelationAnnotation r1 = new RelationAnnotation("Has_value", t3, t2);
        LogicalEntity r2 = new RelationAnnotation("AND", t1, r1);

        System.out.println("Trees contain logically equivalent subtrees: " + treesAreLogicallyEquivalent(a, r2));
    }

    public static boolean treesAreLogicallyEquivalent(LogicalEntity a, LogicalEntity b) {

        if (a == null) return false;
        if (b == null) return false;

        if (a instanceof TAnnotation && b instanceof TAnnotation) {
            // these are leaf nodes (have no subtree)
            // check for similarity of texts using similarity index
        }

        if (subTreesAreLogicallyEquivalent(a, b)) return true;

        return  treesAreLogicallyEquivalent(a.argOne, b) || 
                treesAreLogicallyEquivalent(a.argTwo, b);
           
    }

    public static boolean subTreesAreLogicallyEquivalent(LogicalEntity a, LogicalEntity b) {

        if (a == null && b == null) return true;
        if (a == null || b == null) return false;

        System.out.println(a.annotationId + "  " + b.annotationId);

        return  (a.annotationId.equals(b.annotationId)) &&
                subTreesAreLogicallyEquivalent(a.argOne, b.argOne) &&
                subTreesAreLogicallyEquivalent(a.argTwo, b.argTwo);
    }

    public static void processLogicalEntities() {
        for (LogicalEntity e : entities) {
            if (e instanceof TAnnotation) 
                System.out.println(((TAnnotation) e).getText());
            else
                System.out.println(e.annotationId);
        }
    }

    // in-order traversal of our "binary tree-like" map
    public static void traverseMap(LogicalEntity entity) {
        
        if (entity == null) return;

        traverseMap(entity.argOne);
        System.out.println(entity.annotationId + " " + entity.annotationType);
        traverseMap(entity.argTwo);
    }

    public static void createRelationships(List<String> annotations) {

        for (String annotation : annotations) {
            char annoType = annotation.charAt(0);
            
            if (annoType == 'T') handleAnnotationTType(annotation);
            else if (annoType == 'R') handleAnnotationRType(annotation);
            else if (annoType == 'O') handleAnnotationAsteriskType(annotation);
        }
    }

    public static void handleAnnotationAsteriskType(String annotation) {
        String annotationId = annotation.substring(0, 3).strip();

        annotation = annotation.substring(3).strip();

        String[] annotationElements = annotation.split(" ");

        String annotationType = annotationElements[0].strip();

        String[] args = new String[] { annotationElements[1].strip(), annotationElements[2].strip() };

        // System.out.println("Annotation ID: " + annotationId);
        // System.out.println("Annotation type: " + annotationType);
        // System.out.println("ARGS: " + args[0] + "," + args[1]);
        // System.out.println("");
        // System.out.println(relationships.getOrDefault(args[0], args[0]));
        relationships.put(annotationId, annotationType+"("+relationships.getOrDefault(args[0], args[0])
                        +","+relationships.getOrDefault(args[1], args[1])+")");
        
        AsteriskAnnotation annotation2 = new AsteriskAnnotation(annotationType, map.getOrDefault(args[0], DUMMY), map.getOrDefault(args[1], DUMMY));

        map.put(annotationId, annotation2);
    }

    public static void handleAnnotationRType(String annotation) {
        String relationId = annotation.substring(0, 3).strip();

        annotation = annotation.substring(3).strip();

        String[] annotationElements = annotation.split(" ");

        String relationType = annotationElements[0].strip();

        String[] args = new String[] { annotationElements[1].split(":")[1].strip() , 
                                                annotationElements[2].split(":")[1].strip() };

        // System.out.println("Relation ID: " + relationId);
        // System.out.println("Relation type: " + relationType);
        // System.out.println("ARGS: " + args[0] + "," + args[1]);
        // System.out.println("");

        relationships.putIfAbsent(relationId, relationships.getOrDefault(args[0], args[0])+","
            +relationships.getOrDefault(args[1], args[1]));

        if (!relationships.containsKey(args[0])) {
            relationships.put(args[0], args[0]);
        }
        relationships.put(args[0], relationId+"("+relationships.getOrDefault(args[0],args[0])+
        ","+relationships.getOrDefault(args[1],args[1])+")");


        RelationAnnotation relationAnnotation = new RelationAnnotation(relationType, (TAnnotation) map.getOrDefault(args[0], DUMMY), map.getOrDefault(args[1], DUMMY));
        map.put(relationId, relationAnnotation);

        if (!map.containsKey(args[0])) {
            map.put(args[0], DUMMY);
        }
        map.put(args[0], relationAnnotation);
    }

    public static void handleAnnotationTType(String annotation) {
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

        // System.out.println("Anno ID : " + annotationId);
        // System.out.println("Anno type : " + annotationType);
        // System.out.println("Anno indexes : " + annotationIndexes[0] + "," + annotationIndexes[1]);
        // System.out.println("Anno text : " + annotationText);
        // System.out.println("");

        relationships.putIfAbsent(annotationId, annotationId);

        TAnnotation annotation2 = new TAnnotation(annotationId, annotationType, annotationIndexes[0],  annotationIndexes[1], annotationText);

        map.putIfAbsent(annotationId, annotation2);
    }

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