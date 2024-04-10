package gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import solver.*;
import DictionaryCleaner.*;

public class Display {
    JFrame frame = new JFrame("Letter Boxed Solver");
    ArrayList<JTextField> boxes_holding_letters = new ArrayList<JTextField>(); // Used for shifting the focus between boxes
    BoxTraversalPolicy policy;
    JPanel main_pane = new JPanel(new GridBagLayout());
    JPanel central_pane = new JPanel(new GridBagLayout());
    static int LETTER_BOX_ROWS = 5;
    static int LETTER_BOX_COLS = 5;
    Styling s = new Styling();
    GridBagConstraints c = new GridBagConstraints();
    
    GameDictionaryCleaner dictionary;
    Solver solver;

    public Display() {
        // Creating the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setContentPane(main_pane);
        
        main_pane.setBackground(Color.WHITE);
        central_pane.setBackground(Color.WHITE);
        setInitialDisplay();
        main_pane.setFocusCycleRoot(true);
        policy = new BoxTraversalPolicy(boxes_holding_letters);
        main_pane.setFocusTraversalPolicy(policy);

        frame.setVisible(true);
    }

    public void setInitialDisplay() {
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
       
        // Creating all the boxes which will hold the letters
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = s.box_which_will_hold_a_letter_inset;

        // Creating side 1
        for (int col = 1; col < LETTER_BOX_COLS - 1; col++) {
            setBoxWhichWillHoldALetter(0, col);
        }

        // Creating side 2
        for (int row = 1; row < LETTER_BOX_ROWS - 1; row++) {
            setBoxWhichWillHoldALetter(row, LETTER_BOX_COLS - 1);
        }

        // Creating side 3
        for (int col = 3; col > 0; col--) { // Kinda gave up here
            setBoxWhichWillHoldALetter(LETTER_BOX_ROWS - 1, col);
        }
        
        // Creating side 4
        for (int row = 3; row > 0; row--) {
            setBoxWhichWillHoldALetter(row, 0);
        }

        // Creating the title
        JTextArea title = new JTextArea("welcome to\n Letter Boxed Solver 2025");
        title.setEditable(false);
        title.setFont(s.fancy_function_non_critical_font.deriveFont(35f));
        title.setBorder(s.title_border);
        
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;

        central_pane.add(title, c);

        // Creating the mode selection menu
        JLabel mode_select_label = new JLabel("select a mode:");
        mode_select_label.setFont(s.fancy_function_non_critical_font.deriveFont(25f));
        
        c.anchor = GridBagConstraints.EAST;
        c.ipady = 40;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;

        central_pane.add(mode_select_label,c);

        String[] modes = {"solve", "assisted solve - does not work :("};
        JComboBox<String> mode_select = new JComboBox<String>(modes);
        
        c.anchor = GridBagConstraints.CENTER;
        c.ipady = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 1;

        central_pane.add(mode_select, c);

        // Adding the go button
        JButton go_button = new JButton("go!");
        go_button.setFocusPainted(false);
        go_button.setBorder(s.generic_black_border);
        go_button.setPreferredSize(new Dimension(125, 70));
        go_button.setFont(go_button.getFont().deriveFont(25f));

        // Adding the on-click functionality for the button
        go_button.addActionListener(new SolveButtonAL(this, mode_select));

        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;

        central_pane.add(go_button, c);

        // Adding the central pane to the main pane
        c.gridwidth = 3;
        c.gridheight = 3;
        c.gridx = 1;
        c.gridy = 1;

        main_pane.add(central_pane, c);
    }

    public void setSolveDisplay(Solver solver) {
        central_pane.removeAll();
        GridBagConstraints solve_c = new GridBagConstraints();
        solve_c.weightx = 0;
        solve_c.weighty = 0;
        solve_c.anchor = GridBagConstraints.CENTER;
        solve_c.fill = GridBagConstraints.HORIZONTAL;

        JTextArea solutions_display = new JTextArea(6, 20);
        solutions_display.setEditable(false);
        solutions_display.setLineWrap(true);
        solutions_display.setFont(solutions_display.getFont().deriveFont(15f));
        solutions_display.append("found " + solver.solutions.size() + " solutions in " + solver.time_to_solve + " milliseconds\n");
        solutions_display.append("solutions:\n");
        for (int i = 0; i < solver.solutions.size(); i++) {
            for (String str : solver.solutions.get(i)) {
                solutions_display.append(str + ' ');
            }
            solutions_display.append("\n"); 
        }
        solve_c.insets = s.title_inset;
        solve_c.gridwidth = 3;
        solve_c.gridheight = 1;
        solve_c.gridx = 0;
        solve_c.gridy = 0;
        central_pane.add(solutions_display, solve_c);

        // Adding the save button
        JButton save_button = new JButton("save your results?");
        save_button.setHorizontalAlignment(SwingConstants.CENTER);
        save_button.setFocusPainted(false);
        save_button.setBorder(s.generic_black_border);
        save_button.setPreferredSize(new Dimension(125, 70));
        save_button.setFont(save_button.getFont().deriveFont(25f));
        save_button.addActionListener(new SaveButtonAL(solver, save_button));

        solve_c.gridy = 1;
        central_pane.add(save_button, solve_c);
        redrawPanel();
    }

    // Helper Methods

    public void setBoxWhichWillHoldALetter(int row, int col) {
        LetterInput letter_input = new LetterInput(this.s, this.boxes_holding_letters);
        boxes_holding_letters.add(letter_input.box_which_will_hold_a_letter);
        c.gridx = col;
        c.gridy = row;
        main_pane.add(letter_input.box_which_will_hold_a_letter, c);
    }

    public ArrayList<String> getSidesFromTextFields() {
        ArrayList<String> sides = new ArrayList<String>();
        StringBuilder side = new StringBuilder();

        for (int i = 0; i < boxes_holding_letters.size(); i++) {
            side.append(boxes_holding_letters.get(i).getText().toLowerCase()); // 
            if (side.length() == 3) {
                sides.add(side.toString());
                side.setLength(0); // Resets the StringBuilder
            }
        }
        return sides;
    }

    public void redrawPanel() {
        frame.setContentPane(main_pane);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Display display = new Display();
    }
}