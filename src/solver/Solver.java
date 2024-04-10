package solver;

import java.util.*;

public class Solver { 
    public ArrayList<ArrayList<String>> solutions = new ArrayList<ArrayList<String>>();
    public ArrayList<String> potential_solution = new ArrayList<String>();
    public int solution_length;
    public static int MAX_SOLUTION_LENGTH = 5;
    public int time_to_solve;
    public BitSet letters_needed = new BitSet(12); // Used to keep track of the letters required to complete a solution
    // Information about the words that may compose a solution
    public ArrayList<String> valid_words;
    public HashMap<String, WordData> word_datas = new HashMap<String, WordData>();
    public WordScores word_scores; // Currently unused
    public HashMap <Character, int[]> letter_ranges;
    // Information about the user entered letters on the box
    public String letters;
    public HashMap<Character, BitSet> letter_masks;
    
    public Solver(ArrayList<String> sides, ArrayList<String> valid_words) {
        this.valid_words = valid_words;
        this.letters = this.stringArrayToString(sides);
        this.letter_masks = this.getLetterMasks();
        for (String word : valid_words) {
            WordData word_data = new WordData(this.letter_masks, word);
            this.word_datas.put(word, word_data);
        }
        this.word_scores = new WordScores(letters, valid_words, word_datas);
        this.letter_ranges = this.getLetterRanges();
    }

    public void Solve(int solutions_desired) {
        long t0 = System.currentTimeMillis();
        solution_length = 2; // Initially search for 2 word long solutions
        for (int i = 0; i < solutions_desired; i++) {
            letters_needed.clear();
            try {
                this.getSolution();
            } catch (NoValidWordException e) {
                System.out.println("The desired quantity of solutions could not be found");
                break;
            }
        }
        long t1 = System.currentTimeMillis();
        this.time_to_solve = (int)(t1 - t0);
    }

    public void getSolution() throws NoValidWordException { 
        while (letters_needed.cardinality() < 12) {
            
            // This condition is checked prior to the candidate.isEmpty() check, as to not needlessly call findBestMoves
            // additionally, the comparison greater then or equal to because this block is only executed if the solution is not already complete
            if (potential_solution.size() >= solution_length) {
                this.removeDeadEnd();
            }
            // findWinningMove only returns words that complete the solution
            String candidate = (potential_solution.size() == solution_length - 1 ) ? findWinningMove(getRangeToBeSearched()) : findBestMove(getRangeToBeSearched());
            if (candidate.isEmpty()) { // Meaning that no words were found which contributed new letters
                this.removeDeadEnd();
            } else {
                letters_needed.or(word_datas.get(candidate).word_mask);
                potential_solution.add(candidate);
            }
        }

        // Setting the used field of each word in the now confirmed to be valid solution to true so
        // that they are not used in following solutions
        for (String word : potential_solution) {
            word_datas.get(word).used = true;
        }
        
        solutions.add((ArrayList<String>)potential_solution.clone());
        potential_solution.clear();
        return;
    }

    private int[] getRangeToBeSearched() throws NoValidWordException {
        // Ensuring that all of valid_words is searched if potential_solution is currently empty
        if (potential_solution.isEmpty()) {
            int[] search_range = {0, valid_words.size()};
            return search_range;
        }
        
        String previous_word = potential_solution.get(potential_solution.size() - 1);
        Character last_letter = previous_word.charAt(previous_word.length() - 1);
        if (letter_ranges.containsKey(last_letter)) {
            return letter_ranges.get(last_letter);
        } else {
            this.removeDeadEnd();
            return getRangeToBeSearched(); // Recursion for the fans
        }
    }

    public String findBestMove(int[] search_range) {
        String best_move = ""; 
        int max_unique_letters = 0;
        for (String word : valid_words.subList(search_range[0], search_range[1])) {
            WordData word_data = word_datas.get(word);
            // Checks if the word has already been found to lead to a dead end from the current game state
            if (!word_data.tried_games_states.contains(letters_needed) && !word_data.used) {
                int unique_letters = getUniqueLetters(word_data.word_mask);
                if (unique_letters > max_unique_letters) {
                    best_move = word;
                    max_unique_letters = unique_letters;
                }
            }
        }   
        return best_move;
    }

    private String findWinningMove(int[] search_range) {
        for (String word : valid_words.subList(search_range[0], search_range[1])) {
            WordData word_data = word_datas.get(word);
            if (!word_data.tried_games_states.contains(letters_needed) && !word_data.used) {
                if (getUniqueLetters(word_data.word_mask) == 12); {
                    return word;
                }
            }
        }
        return "";
    }

    private void removeDeadEnd() throws NoValidWordException {
        // Will only be triggered when every single word has been tried as the initial guess
        if (potential_solution.isEmpty()) {
            if (solution_length < MAX_SOLUTION_LENGTH) {
                // Resets the tried_game_states for each word, as increasing the length of a permissable solution
                // means that there are now options which were not available earlier
                word_datas.forEach((String key, WordData value) -> value.tried_games_states.clear());
                solution_length++;
                return;
            } else {
                String error_message = "Could not find desired quantity of solutions. Solutions found: " + solutions.size();
                throw new NoValidWordException(error_message);
            }
        }

        String dead_end = potential_solution.get(potential_solution.size() - 1);
        potential_solution.remove(dead_end);
        letters_needed.clear();
        // letters_needed must to be reset like this as there is no inverse to a bitwise or
        for (String word : potential_solution) {
            letters_needed.or(word_datas.get(word).word_mask);
        }
        word_datas.get(dead_end).tried_games_states.add((BitSet)letters_needed.clone());
        return;
    }

    // Maps each character to a int array where the first element is the index of the characters first appearance 
    // as the beginning letter in valid_words, and the second the characters last
    // e.g. if letter_ranges.get('f') returns {24, 60} then valid_words.get(24 or 60).chaAt(0) will return f
    // whereas valid_words.get(23 or 61).charAt(0) will not return f (hopefully)
    public HashMap<Character, int[]> getLetterRanges() { 
        this.Alphabetize();
        HashMap<Character, int[]> letter_ranges = new HashMap<Character, int[]>();
        char current_range = this.valid_words.get(0).charAt(0); // initialized as to never be equal to the first letter of the first word in valid_words
        int range_start = 0; // We know that the first range begins at the start of valid_words
        int range_end;
        for (int i = 0; i < valid_words.size(); i++) {
            char current_letter = valid_words.get(i).charAt(0);
            if (current_letter != current_range) {
                range_end = i; // not i - 1 as the subList method is exclusive of the ending index
                int[] range_indices = {range_start, range_end};
                letter_ranges.put(current_range, range_indices);
                current_range = current_letter;
                range_start = i;
            }

            // Ensures that the last range, which ends not with a different character but with the end of the array,
            // is added to letter_ranges properly 
            if (i == valid_words.size() - 1) {
                int[] range_indices = {range_start, valid_words.size()};
                letter_ranges.put(current_letter, range_indices);
            }
        }
        return letter_ranges;
    }

    // For each character in letters, generates a unique mask for said character e.g.
    // if letters = "loascuixtrhb", l would be masked as 100000000000, o would be masked as 010000000000,
    // a as 001000000000 and so on
    public HashMap<Character, BitSet> getLetterMasks() {
        HashMap<Character, BitSet> letter_masks = new HashMap<Character, BitSet>();
        int letters_length = this.letters.length();
        for (int i = 0; i < letters_length; i++) {
            BitSet letter_mask = new BitSet(12);
            letter_mask.set(i);
            letter_masks.put(letters.charAt(i), letter_mask);
        }
        return letter_masks;
    }

    // Helper methods

    private void Alphabetize() {
        Comparator<String> alphabetizer_comparator = new AlphabetizerComparator();
        this.valid_words.sort(alphabetizer_comparator);
        return;
    }

    public String stringArrayToString(ArrayList<String> string_array) {
        StringBuilder str = new StringBuilder();
        for (String string : string_array) {
            str.append(string);
        }
        return str.toString();
    }

    // Bit of a misnomer, actually gets the number of letters that the current solution plus the word in question account for
    private int getUniqueLetters(BitSet word_mask) {
        BitSet temp_letters_needed = (BitSet)letters_needed.clone();
        temp_letters_needed.or(word_mask);
        return temp_letters_needed.cardinality();
    }

    public void printSolutions() {
        System.out.printf("Found %d solutions in %d milliseconds: %n", solutions.size(), time_to_solve);
        for (ArrayList<String> solution : this.solutions) {
            for (String word : solution) {
                System.out.print(word + ' ');
            }
            System.out.println();
        }
    }
}