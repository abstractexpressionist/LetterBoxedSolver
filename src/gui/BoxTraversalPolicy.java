package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JTextField;

public class BoxTraversalPolicy extends FocusTraversalPolicy {
    ArrayList<JTextField> order;

    public BoxTraversalPolicy(ArrayList<JTextField> order) {
        this.order = order;
    }

    public Component getComponentBefore(Container con, Component comp) {
        int index = order.indexOf(comp) - 1;
        if (index < 0) {
            return order.get(order.size() - 1);
        } else {
            return order.get(index);
        }
    }

    public Component getComponentAfter(Container con, Component comp) {
        int index = (order.indexOf(comp) + 1) % order.size();
        return order.get(index);
    }

    public Component getFirstComponent(Container con) {
        return order.get(0);
    }

    public Component getLastComponent(Container con) {
        return order.get(order.size() - 1);
    }

    public Component getDefaultComponent(Container con) {
        return order.get(0);
    }
}
