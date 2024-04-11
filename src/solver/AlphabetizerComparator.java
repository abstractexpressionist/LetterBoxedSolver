package solver;

import java.util.Comparator;
// This should probably just be an anonymous class in solver
public class AlphabetizerComparator implements Comparator<String> {

    public int compare(String str_1, String str_2) {
        char first_letter_1 = str_1.charAt(0);
        char first_letter_2 = str_2.charAt(0);
        
        if (first_letter_1 > first_letter_2) {
            return 1;
        } else if (first_letter_1 < first_letter_2) {
            return -1;
        }
        return 0;
    }
}
