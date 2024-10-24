# Letterbox Solver

## Premise
The New York times, famed publishers of the crossword and wordle also have a host other word games
such as Letter Boxed (https://www.nytimes.com/puzzles/letter-boxed). Personally, I love knowing the answer to a puzzle, but just can't stand figuring it out. 
This is why I have created this project, with aims of out sourcing the tedium of puzzle solving to the computer, so us humans can get on with our business.

## Instructions for use 
- Imputing letters into the box 
    - First run the main method in src\gui\Display.java. When the gui pops up keyboard focus will automatically be on the first box, and entering a letter will advance the carat to the next box.
- Adding / creating, then displaying a solution 
    - Once all letters have been entered (note: I'd recommend sticking with solving actual letterboxed puzzles, it wont let you enter a invalid set of letters, but I've found random letters only produce a solution about half the time) press the button labeled "go!". After it's found the solution will be displayed automatically.
