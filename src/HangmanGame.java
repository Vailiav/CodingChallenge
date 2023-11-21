import java.io.*;
import java.util.Random;

public class HangmanGame {
    final String[] hangman = {" ┌──┐\n │  │\n │\n │\n │\n─┴─",
                              " ┌──┐\n │  │\n │  O\n │\n │\n─┴─",
                              " ┌──┐\n │  │\n │  O\n │  │\n │\n─┴─",
                              " ┌──┐\n │  │\n │  O\n │  │\n │   \\\n─┴─",
                              " ┌──┐\n │  │\n │  O\n │  │\n │ / \\\n─┴─",
                              " ┌──┐\n │  │\n │  O\n │ /│\n │ / \\\n─┴─",
                              " ┌──┐\n │  │\n │  O\n │ /│\\\n │ / \\\n─┴─"};
    final String[] wordBank = {"apple", "banana", "orange", "grape", "kiwi", "melon", "pear", "peach", "plum", "berry"};
    int fails = 0;
    int success = 0;
    String goalWord;
    String currentWord;
    String currentGuess;
    StringBuilder guessed;

    public HangmanGame() {
        Random rand = new Random();
        goalWord = wordBank[rand.nextInt(10)];
        currentWord = "_".repeat(goalWord.length());
        guessed = new StringBuilder();
    }

    public Boolean playGame() {
        System.out.println("Any time an input is requested, if it is a single letter, that letter is your guess.\n" +
                "If you input more than one character, that is understood to be a guess at the final word.");
        while(!currentWord.equals(goalWord)) {
            displayState();
            queryString();
            if(fails >= 6) {
                //endgame with a loss
               return endGame(false);
            }
        }
        //endgame with a win
        return endGame(true);
    }

    private void queryString() {
        currentGuess = getUserGuess();
        if(currentGuess.length() > 1) {
            if(currentGuess.equals(goalWord)) {
                currentWord = goalWord;
                success += 1;
            } else {
                fails += 1;
            }
        }
        else {
            if(!updateString()) {
                System.out.println("That isn't in the word! So far you've guessed: " + guessed);
            }
        }
    }

    private Boolean updateString() {
        if(guessed.toString().contains(currentGuess) || currentWord.contains(currentGuess)) {
            System.out.println("You've already guessed these letters: " + guessed);
            return true;
        }
        if(goalWord.contains(currentGuess)) {
            char guess = currentGuess.charAt(0);
            StringBuilder temp = new StringBuilder(goalWord);
            StringBuilder updated = new StringBuilder(currentWord);
            while(temp.toString().contains(currentGuess)) {
                updated.setCharAt(temp.indexOf(currentGuess), guess);
                temp.setCharAt(temp.indexOf(currentGuess), '-');
            }
            currentWord = updated.toString();
            success += 1;
            return true;
        } else {
            guessed.append(currentGuess);
            fails += 1;
            return false;
        }
    }

    private void displayState() {
        System.out.println(hangman[fails]);
        System.out.println("You've failed " + fails + " times, and you've guessed " + success + " letters!");
        System.out.println("Your progress: " + currentWord);
    }

    private String getUserGuess() {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please input a character:");
        try {
            String r = read.readLine().toLowerCase();
            if(r.isEmpty()) return getUserGuess();
            else return r;
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return "Invalid guess.";
    }

    private Boolean endGame(Boolean win) {
        displayState();
        System.out.println("Good game" + (win ? "...˙◠˙ you" : " ;), I") + " won!" +
                (win ? (" It took you " + (fails + success) + " guesses.") : ""));
        System.out.println("Would you like to play again? (y | n)");
        return getUserGuess().equals("y");
    }
}