package gui;

import java.awt.event.*;
import javax.swing.*;

public class InputVerificationAL implements ActionListener {
    JTextField box_which_will_hold_a_letter;

    public InputVerificationAL(LetterInput li) {
        this.box_which_will_hold_a_letter = li.box_which_will_hold_a_letter;
    }

    public void actionPerformed(ActionEvent e) {
        if (!box_which_will_hold_a_letter.getText().isEmpty()) {
            String input = box_which_will_hold_a_letter.getText();
            Character first_letter = input.charAt(0);
            if ((first_letter >= 'a' && first_letter <= 'z') || (first_letter >= 'A' && first_letter <= 'Z')) {
                if (input.length() > 1) {
                    box_which_will_hold_a_letter.setText(first_letter.toString());
                }
            } else {
                box_which_will_hold_a_letter.setText(""); // Clearing the textfield if the first character is not a letter
            }
        }
    }
}
