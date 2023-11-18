public class TAnnotation extends LogicalEntity {
    
    public String annotationId;
    public String annotationType;
    public int startIndex;
    public int stopIndex;
    public String annotationText;

    public TAnnotation(String annotationId, String annotationType, String startIndex, String stopIndex, String annotationText) {
        super(annotationId, annotationType, null, null);
        this.startIndex = Integer.parseInt(startIndex);
        this.stopIndex = Integer.parseInt(stopIndex);
        this.annotationText = annotationText;
    }

    public String getText() {
        return this.annotationText;
    }
}
