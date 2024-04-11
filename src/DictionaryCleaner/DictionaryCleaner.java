package dictionarycleaner;

import java.io.*;
import java.util.ArrayList;

abstract public class DictionaryCleaner {
    public BufferedReader reader;
    public FileWriter writer;
    public File modified_dictionary; // Included as a field just in case we need it later
    public boolean export_as_file; // Controls weather the dictionary modified by clean() will be saved as a file or as a string array
    
    // Constructor for when the modified dictionary is to be saved as a file
    public DictionaryCleaner(File initial_dictionary, File destination_dictionary) throws FileNotFoundException, IOException {
        export_as_file = true;
        modified_dictionary = destination_dictionary;
        modified_dictionary.createNewFile();
        reader = new BufferedReader(new FileReader(initial_dictionary));
        writer = new FileWriter(modified_dictionary, false);
    }

    public DictionaryCleaner(File initial_dictionary) throws FileNotFoundException, IOException {
        export_as_file = false;
        reader = new BufferedReader(new FileReader(initial_dictionary));
    }

    // If an instance of dictionary was created with an initial and modified file,
    // this method will always read from the modified one
    public ArrayList<String> readToArrayList() throws IOException {
        ArrayList<String> words = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        return words;
    }

    abstract public void clean() throws IOException;
}