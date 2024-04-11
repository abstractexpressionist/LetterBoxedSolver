package ui;
import java.io.*;

import dictionarycleaner.GameDictionaryCleaner;
import solver.Solver;

public abstract class Game {
    public LetterBox letter_box = new LetterBox();
    public GameDictionaryCleaner new_game_dictionary;
    public Solver solver;

    public Game() throws IOException {
        new_game_dictionary = new GameDictionaryCleaner(letter_box.sides);
        new_game_dictionary.clean();
    }
}
