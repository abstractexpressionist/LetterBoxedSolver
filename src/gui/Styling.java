package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;

// There is no way in hell this is proper
public class Styling {
    Border generic_black_border = BorderFactory.createLineBorder(Color.BLACK, 4, true);
    Border title_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK);
    Insets box_which_will_hold_a_letter_inset = new Insets(1, 1, 1, 1);
    Insets title_inset = new Insets(3, 3, 3, 3);
    Font fancy_function_non_critical_font;

    public Styling() {
        try {
            fancy_function_non_critical_font = Font.createFont(Font.TRUETYPE_FONT, new File("src\\gui\\Cleanhand-Regular.ttf"));
        } catch (IOException | FontFormatException e) {
            fancy_function_non_critical_font = null;
        }
    }
}
