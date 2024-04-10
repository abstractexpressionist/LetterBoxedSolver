package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LetterInput {
    public JTextField box_which_will_hold_a_letter = new JTextField();
    public ArrayList<JTextField> boxes_holding_letters;

    public LetterInput(Styling s, ArrayList<JTextField> boxes_holding_letters) {
        this.boxes_holding_letters = boxes_holding_letters;
        box_which_will_hold_a_letter.setFont(s.fancy_function_non_critical_font.deriveFont(125f));
        box_which_will_hold_a_letter.setBorder(s.generic_black_border);
        box_which_will_hold_a_letter.setHorizontalAlignment(JTextField.CENTER);
        box_which_will_hold_a_letter.setPreferredSize(new Dimension(150,150));
        
        // Prevents more then one character from being typed into a single box
        box_which_will_hold_a_letter.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char letter = e.getKeyChar();

                if ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z')) {
                    // Checking if the letter entered is already present on the box
                    boolean letter_is_unique = true;
                    for (JTextField tf : boxes_holding_letters) {
                        if (!tf.getText().isEmpty()) {
                            if (tf.getText().charAt(0) == letter) {
                                letter_is_unique = false;
                            }
                        }
                    }

                    if (!letter_is_unique) {
                        e.consume();
                    } else {
                        if (!box_which_will_hold_a_letter.getText().isEmpty()) {
                        e.consume();
                        }
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                    }
                } else {
                    e.consume();
                }
            }

            // Two separate events are need as keyTyped cant detect a backspace, as it's not a unicode character
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (box_which_will_hold_a_letter.getText().isEmpty()) {
                        e.consume();
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                    }
                }
            }

            // Bit scuffed, triggers input verification whenever a key is released
            public void keyReleased(KeyEvent e) {
                try {
                    Robot enter_presser = new Robot();
                    enter_presser.keyPress(KeyEvent.VK_ENTER);
                } catch (AWTException a) {}

            }
        });

        // Guarantees that the text field can only hold one valid character
        box_which_will_hold_a_letter.addActionListener(new InputVerificationAL(this));
    }
}