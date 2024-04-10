package tests;

import java.util.*;
import org.junit.*;
import solver.Solver;

public class SpecificSolverTest {
    Solver test_solver;
    ArrayList<String> test_valid_words = new ArrayList<String>();
    String[] test_letters_array = {"the","xir","afc","los"};
    ArrayList<String> test_letters = new ArrayList<String>();

    @Before
    public void testSetup() {
        test_valid_words.add("there");
        test_valid_words.add("exit");
        test_valid_words.add("traffic");
        test_valid_words.add("close");
        for (String str : test_letters_array) {
            test_letters.add(str);
        }
        this.test_solver = new Solver(test_letters,test_valid_words);
        test_solver.Solve(1);
    }

    @Test
    public void testGetSolutions() {
        // oh please oh please
        test_solver.printSolutions();
    }
  

}