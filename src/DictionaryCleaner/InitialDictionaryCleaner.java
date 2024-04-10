package DictionaryCleaner;

import java.io.*; 


// This was run once to produce base_dictionary.txt, and now serves no purpose
public class InitialDictionaryCleaner extends DictionaryCleaner {
    static File initial_dictionary = new File("dictionaries/initial_words.txt");
    
    public InitialDictionaryCleaner(File modified_dictionary) throws IOException, FileNotFoundException {
        super(initial_dictionary, modified_dictionary);
    }

    public void clean() throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            // Skipping a word if it's too short
            if (line.length() <= 2) {
                continue;
            }

            // checks for if any 2 adjacent letters are the same
            boolean valid_word = true; // flag to check if the loop ends early
            for (int i = 1; i < line.length(); i++) { 
                if (line.charAt(i) == line.charAt(i-1)) {
                    valid_word = false; 
                    break;
                }               
            }

            if (valid_word) {
                writer.write(line + '\n');
                writer.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        File modified_dictionary = new File("dictionaries/base_dictionary.txt");
        DictionaryCleaner base_dictionary = new InitialDictionaryCleaner(modified_dictionary);
        base_dictionary.clean();
    }
}