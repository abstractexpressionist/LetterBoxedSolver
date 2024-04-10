package solver;

import java.util.*;

// Currently unused, will try to find something fun to do with it
public class WordScores {
    public HashMap<Character, double[]> frequencies;
    public HashMap<String, Double> word_scores;
    String letters;
    int total_character_count = 0;

    public WordScores(String letters, ArrayList<String> valid_words, HashMap<String, WordData> word_datas) {
        this.letters = letters;
        this.frequencies = this.getFrequencies(valid_words);
        this.word_scores = this.getWordScores(valid_words, word_datas);
    }
    
    public HashMap<Character, double[]> getFrequencies(ArrayList<String> valid_words) {
        HashMap<Character, double[]> letter_count = new HashMap<Character, double[]>();
        double[] initial_value = {0, 0, 0};     // double[0] is the total occurrences of a letter, 
        for(String word : valid_words) {        // double[1] is the occurrences of a letter as the last letter in a word
            int word_length = word.length();    // double[2] is the occurrences of a letter as the first letter in a word
            this.total_character_count += word_length;
            for (int i = 0; i < word_length; i++) {
                Character c = word.charAt(i);
                letter_count.putIfAbsent(c, Arrays.copyOf(initial_value, initial_value.length));
                if (i == word_length - 1) { // Checks if we are accessing the last character in the string
                    letter_count.compute(c, (Character k, double[] v) -> {v[0]++; v[1]++; return v;});
                } else if (i == 0) {
                    letter_count.compute(c, (Character k, double[] v) -> {v[0]++; v[2]++; return v;});
                } else {
                    letter_count.compute(c, (Character k, double[] v) -> {v[0]++; return v;});
                }
            }
        }

        // Calculates the frequency of each character, where the overall frequency
        // is the occurrences of that character divided by the total amount of characters,
        // and the frequency of a letter in at the beginning or end of a word is the occurrences
        // of that character in that position divided by the total amount of words
        letter_count.replaceAll((Character k, double[] v) -> {
            v[0] /= this.total_character_count;
            v[1] /= valid_words.size();
            v[2] /= valid_words.size();
            return v;
        });
        return letter_count;
    }

    public HashMap<String, Double> getWordScores(ArrayList<String> valid_words, HashMap<String, WordData> word_datas) {
        HashMap<String, Double> word_scores = new HashMap<String, Double>();
        for (String word : valid_words) {
            double word_score = 0;
            BitSet word_mask = word_datas.get(word).word_mask;
            for (int i = word_mask.nextSetBit(0); i >= 0; i = word_mask.nextSetBit(i + 1)) {
                word_score += (1 - frequencies.get(letters.charAt(i))[0]);
            }
            word_scores.put(word, word_score);
        }
        return word_scores;
    }

    // Helper methods

    public String getCharactersFromMask(BitSet word_mask) {
        StringBuilder unique_letters = new StringBuilder();
        int letters_length = this.letters.length();
        for (int i = 0; i < letters_length; i++) {
            if (word_mask.get(i)) {
                unique_letters.append(this.letters.charAt(i));
            }
        }
        return unique_letters.toString();
    }
}
