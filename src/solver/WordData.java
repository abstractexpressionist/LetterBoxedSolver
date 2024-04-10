package solver;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class WordData {
    public BitSet word_mask;
    // Tracks the game state's (i.e. the state of letters_needed when a word was tried, and no continuations were found
    public ArrayList<BitSet> tried_games_states = new ArrayList<BitSet>();
    public boolean used = false;

    public WordData(HashMap<Character, BitSet> letter_masks, String word) {
        this.word_mask = getWordMask(letter_masks, word);
    }

    public BitSet getWordMask(HashMap<Character, BitSet> letter_masks, String word) {
        BitSet word_mask = new BitSet(12);
        for (Character c : word.toCharArray()) {
            word_mask.or(letter_masks.get(c));
        }
        return word_mask;
    }
}
