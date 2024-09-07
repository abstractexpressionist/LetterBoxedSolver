package gui;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.JButton;
import java.text.*;
import solver.Solver;

public class SaveButtonAL implements ActionListener {
    Solver solver;
    JButton save_button;
    File saved_game;
    int times_saved = 0;

    public SaveButtonAL(Solver solver, JButton save_button) {
        this.solver = solver;
        this.save_button = save_button;
        String file_name = "saved games/" + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ".txt";
        saved_game = new File(file_name);
    }

    public void actionPerformed(ActionEvent e) {
        times_saved++;
        if (times_saved <= 1) {
            saveGame();
            save_button.setFont(save_button.getFont().deriveFont(15f));
            save_button.setText("<html>" + "results saved as"  + "<br>" + saved_game.getName() + "</html");
        } else if (times_saved < 5) {
            save_button.setText("<html>" + "results for this game" + "<br>" + "have already been saved" + "</html>");
        } else {
            save_button.setText("stop that");
        }
    }

    public void saveGame() {
        // Setting System.out to print into our saved game file
        try {
            PrintStream output = new PrintStream(saved_game);
            System.setOut(output);
            // Turning the string containing our letters back into a format that can be used with code written for the console based game
            char[][] char_array_sides = new char[4][3];
            int m = 0;
            int n = 0;
            for (int i = 0; i < solver.letters.length(); i++) {
                n = i % 3;
                if (i > 0 && (i % 3 == 0)) {
                    m++;
                }
                char_array_sides[m][n] = solver.letters.charAt(i);
            }
            // Sometimes you really just don't care anymore


            System.out.print(" ");
            for (int i = 0; i < 3; i++) {
                System.out.print(char_array_sides[0][i] + " ");
            }

            System.out.println();
            for (int i = 0; i < 3; i++) {
                System.out.println(char_array_sides[3][i] + "     " + char_array_sides[1][i]);
            }
            
            System.out.print(" ");
            for (int i = 0; i < 3; i++) {
                System.out.print(char_array_sides[2][i] + " ");
            }
            System.out.println();
            System.out.println();
        
            solver.printSolutions();
            output.close();
        } catch (FileNotFoundException e) {}
    }
}