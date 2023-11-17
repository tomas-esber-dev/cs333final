import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AnnotationProcessor {

    static Map<String, String> relationships = new HashMap<>();

	public static void main(String[] args) {
    List<String> annotations = readAnnotations("sample_anno_input_2.ann");

    createRelationships(annotations);

    System.out.println(relationships);

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
        System.out.println(relationships.getOrDefault(args[0], args[0]));
        relationships.put(annotationId, annotationType+"("+relationships.getOrDefault(args[0], args[0])
                        +","+relationships.getOrDefault(args[1], args[1])+")");
    }

    public static void handleAnnotationRType(String annotation) {
        String relationId = annotation.substring(0, 3).strip();

        annotation = annotation.substring(3).strip();

        String[] annotationElements = annotation.split(" ");

        String relationType = annotationElements[0].strip();

        String[] args = new String[] { annotationElements[1].split(":")[1].strip() , 
                                                annotationElements[2].split(":")[1].strip()};

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