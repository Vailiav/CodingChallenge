public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to hangman!");
        HangmanGame game;
        do {
            game = new HangmanGame();
        } while (game.playGame());
        System.out.println("Thanks for playing!");
    }
}
