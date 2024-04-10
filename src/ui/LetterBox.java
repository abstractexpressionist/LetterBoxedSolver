package ui;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String;
import java.util.BitSet;

public class LetterBox {
    public ArrayList<String> sides = new ArrayList<String>();
    private Scanner scanner = new Scanner(System.in);
    BitSet letters_present = new BitSet(26);
    public int solutions_desired;

    public LetterBox() {
        while (true) {
            for (int side = 0; side < 4; side++) {
                sides.add(getLetters(side));
            }
            if (verifyLetters(sides)) {break;}
        }
        System.out.print("Enter how many solutions you would like: ");
        solutions_desired = scanner.nextInt();
    }

    // Gets the letters on each side of the box via text input from the user
    public String getLetters(int side) {
        String input;
        while (true) {
            System.out.print("Please enter the letters on side " + (side + 1) + ": ");
            input = scanner.nextLine();
            input.toLowerCase();
            input.replaceAll("\\s", ""); // Trims any whitespace from the input
            
            boolean characters_are_unique = true;
            boolean characters_are_letters = true;
            for (Character c : input.toCharArray()) {
                if (c < 'a' || c > 'z') {
                    characters_are_letters = false;
                }
                if (letters_present.get(c - 'a')) {
                    characters_are_unique = false;
                } else {
                    letters_present.set(c - 'a');
                }
            }
            
            if ((input.length() != 3) || !characters_are_unique || !characters_are_letters) {
                if (input.length() != 3) {
                    System.out.println("One side should have exactly 3 letters");
                } 
                
                if (!characters_are_unique) {
                    System.out.println("All letters must be unique");
                }  

                if (!characters_are_letters) {
                    System.out.println("All characters entered must be letters");

                }
                System.err.println("Please try again");
            } else {
                break;
            }
        }
        return input; 
    }

    // Displays the user entered letters and asks the user to verify that they are correct
    public boolean verifyLetters(ArrayList<String> sides) {
        char[][] char_array_sides = new char[4][3];
        for (int i = 0; i < 4; i++) {
            char_array_sides[i] = sides.get(i).toCharArray();
        }

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
        
        String input = "";
        System.out.print("Does this look correct? (y/n): ");
        input = scanner.nextLine();
        if (input.equals("y")) {return true;}
        else {
            sides.clear();
            return false;
        }
    }

    public static void main(String[] args) {
        new LetterBox();
    }
}