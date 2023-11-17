public class LogicalEntity {
    public String annotationId;
    public String annotationType;

    LogicalEntity(String annotationId, String annotationType) {
        this.annotationId = annotationId;
        this.annotationType = annotationType;
    }

    public String getAnnotationId() {
        return this.annotationId;
    }

    public String getAnnotationType() {
        return this.annotationType;
    }
}
