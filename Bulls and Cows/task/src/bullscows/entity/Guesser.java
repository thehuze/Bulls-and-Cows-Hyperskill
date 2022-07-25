package bullscows.entity;

import java.util.Scanner;

public class Guesser implements Player{

    private final Game game;
    private String guessedCode;

    protected Guesser(Game game) {
        guessedCode = "";
        this.game = game;
    }

    @Override
    public String move() {
        Scanner scanner = new Scanner(System.in);
        guessedCode = scanner.nextLine();
        return guessedCode;
    }

    public String getGuessedCode() {
        return guessedCode;
    }

    public Game getGame() {
        return game;
    }
}
