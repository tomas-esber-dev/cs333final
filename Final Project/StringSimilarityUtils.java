import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringSimilarityUtils {

    public static void main(String[] args) {
        double a = nGramSimilarity("This patient has ADHD.", "Trial requires patients with ADHD.", 3);
        System.out.println(a);

        double b = sorensenDiceSimilarity("This patient has ADHD.", "Trial requires patients with ADHD.");
        System.out.println(b);
    }

    /**
     * N-gram similarity score by comparing the various n-length substrings of both strings.
     * We calculate difference using Jaccard similarity.
     * 
     * @param s1 string to be compared
     * @param s2 string to be compared
     * @param gramLength desired length of our substrings
     * @return similarity score of strings
     */
    public static double nGramSimilarity(String s1, String s2, int gramLength) {
        // error handling
        if (s1.length() == 0 && s2.length() == 0) return -1.0;

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        for (int i = 0; i < s1.length() - gramLength + 1; i++) {
            String sub1 = s1.substring(i, i + gramLength);
            set1.add(sub1);
        }

        for (int i = 0; i < s2.length() - gramLength + 1; i++) {
            String sub2 = s2.substring(i, i + gramLength);
            set2.add(sub2);
        }

        // number of grams that both strings have in common
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        double countSimilar = intersection.size();

        // Jaccard similarity
        return countSimilar / (set1.size() + set2.size() - countSimilar);
    }

    /**
     * Algorithm that uses the Sorensen-Dice coefficient to determine similarity.
     * 
     * @param s1 string to be compared
     * @param s2 string to be compared
     * @return similarity score of strings
     */
    public static double sorensenDiceSimilarity(String s1, String s2) {
        // error handling
        if (s1.length() == 0 && s2.length() == 0) return -1.0;

        List<String> lst1 = Arrays.asList(s1.split(" "));
        List<String> lst2 = Arrays.asList(s2.split(" "));

        Set<String> set1 = new HashSet<>(lst1);
        Set<String> set2 = new HashSet<>(lst2);

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        double totalSize = lst1.size() + lst2.size();

        return (2.0 * intersection.size()) / totalSize;
    }
}
