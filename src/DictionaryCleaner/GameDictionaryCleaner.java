package DictionaryCleaner;

import java.io.*;
import java.util.ArrayList;

public class GameDictionaryCleaner extends DictionaryCleaner {
    public static File initial_dictionary = new File("dictionaries/base_dictionary.txt");
    public ArrayList<String> sides;
    public ArrayList<String> valid_words = new ArrayList<String>();

    public GameDictionaryCleaner(ArrayList<String> sides, File modified_dictionary) throws IOException, FileNotFoundException {
        super(initial_dictionary, modified_dictionary);
        this.sides = sides;
    }

    public GameDictionaryCleaner(ArrayList<String> sides) throws IOException, FileNotFoundException {
        super(initial_dictionary);
        this.sides = sides;
    }

    // Finds the side that the character is on, then returns it's index within sides.
    // If the character is not found on any side returns -1
    private int checkCharacterSide(char c) { 
        for (int i = 0; i < 4; i++) {
            if (this.sides.get(i).contains(c + "")) { // This is nessacary because the String.contains() does not take char as a parameter
                return i;
            }
        }
        return -1;
    }

    public void clean() throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            boolean valid_word = true;
            int c_index = checkCharacterSide(line.charAt(0));
            for (int i = 1; i < line.length(); i++) {
                int c_plus_1_index = checkCharacterSide(line.charAt(i));
                if ((c_index == -1) || (c_plus_1_index == -1) || (c_index == c_plus_1_index)) {
                    valid_word = false;
                    break;
                }
                c_index = c_plus_1_index;
            }
            if (valid_word) {
                if (super.export_as_file) {
                    writer.write(line + '\n');
                    writer.flush();
                } else {
                    valid_words.add(line);
                }
            }
        }
    }
}