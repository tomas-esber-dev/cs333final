package Approach_1_Code;
public class AsteriskAnnotation extends LogicalEntity {
    
    String annotationType;

    AsteriskAnnotation(String annotationType, LogicalEntity argOne, LogicalEntity argTwo) {
        super(annotationType, annotationType, argOne, argTwo);
    }

    public LogicalEntity getArgOne() {
        return this.argOne;
    }

    public LogicalEntity getArgTwo() {
        return this.argTwo;
    }
}
