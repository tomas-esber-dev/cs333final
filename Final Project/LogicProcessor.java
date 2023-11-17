

public class LogicProcessor {

    TreeNode t = new TreeNode(null, null, null);
    
    String str1 = "OR(R2(T3,R1(T1,T2)),R4(T4,T5))";
    String str2 = "OR(R2(T1,R1(T3,T2)),T4)";

    //                  OR
    //                  /\
    //                R2  T4
    //                /\
    //              T1 R1
    //                 /\
    //                T3 T2

    // in-order: T1 R2 T3 R1 T2 T4 OR
}
