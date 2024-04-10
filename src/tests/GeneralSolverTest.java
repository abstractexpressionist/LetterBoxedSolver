package tests;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.*;
import solver.Solver;

public class GeneralSolverTest {
    Solver test_solver;
    TestData test_data;

    @Before
    public void testSetup() throws IOException, FileNotFoundException{
        this.test_data = new TestData();
        this.test_solver = new Solver(test_data.test_sides, test_data.test_words);
        test_solver.Solve(3);
    }

    @Test
    public void testGetFrequencies() {
        Double[] total_frequencies = {0.0, 0.0, 0.0};
        Double[] expected_total_frequencies = {1.0, 1.0, 1.0};
        test_solver.word_scores.frequencies.forEach((Character k, double[]v) -> {
            System.out.println(k);
            for (int i = 0; i < 3; i++) {
                total_frequencies[i] += v[i];
                System.out.print(v[i] + ' ');
            }
            System.out.println();
        });
        assertArrayEquals(expected_total_frequencies, total_frequencies);
    }

    @Test
    public void testGetLetterMasks() {
        test_solver.letter_masks.forEach((Character k, BitSet v) -> {
            System.out.println(k);
            System.out.println(v.toString());
        });
    }

    // Prints the first 25 words in valid words, the letters they contain, and the set bits in their mask
    @Test
    public void testGetWordMasks() {
        for (int i = 0; i < 25; i++) { // 
            String word = test_solver.valid_words.get(i);
            BitSet word_mask = test_solver.word_datas.get(word).word_mask;
            String characters_contained = test_solver.word_scores.getCharactersFromMask(word_mask);
            System.out.println(word);
            System.out.println(characters_contained);
            System.out.println(word_mask.toString());
            for (Character c : characters_contained.toCharArray()) {
                assertTrue(word.contains(c.toString()));
            }
        }
    }

    @Test
    public void testGetLetterRanges() {
        test_solver.letter_ranges.forEach((Character c, int[] range ) -> {
            assertEquals(c, (Character)(test_solver.valid_words.get(range[0]).charAt(0)));
            // range[1] is the index one past the end of the given character, as subList is exclusive of the upper bound
            assertEquals(c, (Character)(test_solver.valid_words.get(range[1] - 1).charAt(0)));
        });
    }

    @Test
    public void testGetSolutions() {
        // oh please oh please
        System.out.println("Solutions:");
        for (ArrayList<String> solution : test_solver.solutions) {
            for (String word : solution) {
                System.out.print(word + ' ');
            }
            System.out.println();
        }
        
    }
}