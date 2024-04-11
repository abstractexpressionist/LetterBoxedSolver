package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import dictionarycleaner.DictionaryCleaner;

public class TestData {
    // Letters, solutions, and the reference dictionary are from the nyt Letter Boxed puzzle for march 1st
    public static String[] reference_solutions = {"CHURLISH", "HATBOX"};
    public ArrayList<String> test_sides = new ArrayList<String>();
    public static File reference_dictionary = new File("src\\tests\\test dictionaries\\reference_game_dictionary.txt");
    public ArrayList<String> test_words;
    public File test_game_dictionary_destination = new File("src\\tests\\test dictionaries\\test_game_dictionary.txt");

    public TestData() throws IOException, FileNotFoundException{
        DictionaryCleaner test_dictionary = new DictionaryCleaner(test_game_dictionary_destination) {public void clean(){}}; // A little bit scuffed but its not like we'll be calling that
        test_words = test_dictionary.readToArrayList();
        test_sides.add("loa");
        test_sides.add("scu");
        test_sides.add("ixt");
        test_sides.add("rhb");
    }
            
        
}
