# Letterbox Solver

## Premise
The new york times, famed publishers of the crossword and wordle also have host other word games
such as Letter Box (https://www.nytimes.com/puzzles/letter-boxed). I personally enjoy this one most of all,
but lack the word game prowess to solve every puzzle. This is why I have created this project, with aims of out sourcing
the tedium of puzzle solving to the computer, so us humans can get on with our business

## Instructions for use 
- Imputing letters into the box (first required event)
    - First run the main method in src\gui\Display.java. When the gui pops up keyboard focus will automatically be on the first box, and entering a letter will advance the carat to the next box.
- Adding / creating, then displaying a solution (second required event)
    - Once all letters have been entered (note: I'd recommend sticking with solving actual letterboxed puzzles, It wont let you enter a invalid set of letters, but from what I've found random letters only produce a solution about half the time) press the button labeled "go!". After it's found the solution will be displayed automatically.
- The visual component of the program would be the font if I had to choose. It can be found by running the gui.
- Once a solution has been found, it can be saved to a text file (along with the letter box associated with it) by clicking the button labeled save.


## User stories
* sure a box is great, but i prefer other shapes (triangle, hexagon, etc.). When will you let me solve those?
* i ain't about to let no son of sam-ual johnson tell me what is and isn't a word, which is why i've curated and edited my own english dictionary. When will you let me solve Letter Box for it?

## Assignment 3: Task 3
Frankly, I biffed it when choosing my project for this course. Most of this class was about writing general code, meaning code which is sufficiently abstracted so that it may be used to represent / accomplish several similar things, but a solver (in its simplest form) only does one, very specific thing. Especially for a game like letterboxed, where the algorithm to solve it is pretty straight forward, there just isn't a ton of room to generalize. Looking at my UML diagram, the first change that occurred to me was to make the gui less of a mess, but after that i would probably remove WordData and just put it's functionality in Solver. As it stands, it's basically just a tuple, and it isn't used by any other class.

## written by SN: 88568902