public class LogicalEntity {
    public String annotationId;
    public String annotationType;
    public LogicalEntity argOne;
    public LogicalEntity argTwo;

    LogicalEntity(String annotationId, String annotationType, LogicalEntity argOne, LogicalEntity argTwo) {
        this.annotationId = annotationId;
        this.annotationType = annotationType;
        this.argOne = argOne;
        this.argTwo = argTwo;
    }

    public String getAnnotationId() {
        return this.annotationId;
    }

    public String getAnnotationType() {
        return this.annotationType;
    }
}
