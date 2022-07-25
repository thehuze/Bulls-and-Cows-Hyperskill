package bullscows.entity;

import java.util.List;

public class Grader implements Player {

    private final Game game;

    protected Grader(Game game) {
        this.game = game;
    }

    @Override
    // 'X bulls and Y cows'
    public String move() {
        String code = game.getCode();
        String guessedCode = game.getGuesser().getGuessedCode();
        return grade(List.of(guessedCode.split("")),
                List.of(code.split("")));
    }

    public String grade(List<String> guessedCode, List<String> code) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < game.getCode().length(); i++) {
            String codeSymbol = code.get(i);
            String guessedSymbol = guessedCode.get(i);
            if (!code.contains(guessedSymbol)) {
                continue;
            }
            if (codeSymbol.equalsIgnoreCase(guessedSymbol)) {
                bulls++;
            }
            else {
                cows++;
            }
        }

        if (bulls == game.getCode().length()) {
            game.stop();
        }

        if (bulls == 0 && cows == 0) {
            return "None";
        } else if (bulls != 0 && cows == 0) {
            return String.format("%s bull(s)", bulls);
        } else if (bulls == 0) {
            return String.format("%s cow(s)", cows);
        } else {
            return String.format("%s bull(s) and %s cows(s)", bulls, cows);
        }
    }

    public Game getGame() {
        return game;
    }
}
