public class TAnnotation {
    
    public String annotationId;
    public String annotationType;
    public int startIndex;
    public int stopIndex;
    public String annotationText;

    public TAnnotation(String annotationId, String annotationType, String startIndex, String stopIndex, String annotationText) {
        this.annotationId = annotationId;
        this.annotationType = annotationType;
        this.startIndex = Integer.parseInt(startIndex);
        this.stopIndex = Integer.parseInt(stopIndex);
        this.annotationText = annotationText;
    }
}
