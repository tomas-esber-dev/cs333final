public class RelationAnnotation extends LogicalEntity {
    
    String annotationType;

    RelationAnnotation(String annotationType, LogicalEntity argOne, LogicalEntity argTwo) {
        super(annotationType, annotationType, argOne, argTwo);
    }
}
