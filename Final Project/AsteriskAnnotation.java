public class AsteriskAnnotation extends LogicalEntity {
    
    String annotationType;
    LogicalEntity argOne;
    LogicalEntity argTwo;

    AsteriskAnnotation(String annotationType, LogicalEntity argOne, LogicalEntity argTwo) {
        super(annotationType, annotationType);
        this.argOne = argOne;
        this.argTwo = argTwo;
    }

    public LogicalEntity getArgOne() {
        return this.argOne;
    }

    public LogicalEntity getArgTwo() {
        return this.argTwo;
    }
}
