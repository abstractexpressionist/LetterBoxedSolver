package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import DictionaryCleaner.GameDictionaryCleaner;
import solver.Solver;

public class SolveButtonAL implements ActionListener {
    Display display;
    JComboBox<String> mode_select;
    ArrayList<String> sides;
    GameDictionaryCleaner dictionary;
    Solver solver;
    

    public SolveButtonAL(Display display, JComboBox<String> mode_select) {
        this.display = display;
        this.mode_select = mode_select;
    }

    public void actionPerformed(ActionEvent e) {
        if (mode_select.getSelectedItem() == "solve") {
            this.sides = display.getSidesFromTextFields();
            try {
                dictionary = new GameDictionaryCleaner(sides);
                dictionary.clean();
            } catch (IOException exp) {}
                solver = new Solver(sides, dictionary.valid_words);
                solver.Solve(3); // Arbitrary solutions desired
                display.setSolveDisplay(solver);
        } else if (mode_select.getSelectedItem() == "assisted solve") {
        // TODO: Implement this stuff
        }
    }
}