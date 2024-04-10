package ui;

import java.io.*;
import solver.Solver;

public class Solve extends Game {

    public Solve() throws FileNotFoundException, IOException{
        super();
        solver = new Solver(letter_box.sides, new_game_dictionary.valid_words);
        solver.Solve(letter_box.solutions_desired);
        solver.printSolutions();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Solve session = new Solve();
    }
}
