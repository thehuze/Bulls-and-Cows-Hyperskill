package bullscows.entity;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private final Guesser guesser;
    private final Grader grader;
    private String code;
    private State state;

    public Game() {
        guesser = new Guesser(this);
        grader = new Grader(this);
        generateRandomCode();
    }

    public void start() {
        state = State.STARTED;
        int round = 1;
        do {
            System.out.printf("Turn %d: \n", round);

            getGuesser().move();
            String grade = getGrader().move();

            System.out.printf("Grade: %s \n", grade);
            round++;
        } while (state == State.STARTED);
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public void stop() {
        state = State.FINISHED;
    }

    public Guesser getGuesser() {
        return guesser;
    }

    public Grader getGrader() {
        return grader;
    }

    protected String getCode() {
        return code;
    }

    private void generateRandomCode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        int codeLength = isInputIntegerCorrectFormat(scanner.nextLine());

        System.out.println("Input the number of possible symbols in the code:");
        int numberOfPossibleSymbols = isInputIntegerCorrectFormat(scanner.nextLine());

        checkInputForErrors(codeLength, numberOfPossibleSymbols);

        // заполнение цифрами
        List<String> possibleSymbols = IntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .limit(numberOfPossibleSymbols)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());

        // заполнение символами
        if (numberOfPossibleSymbols > 10) {
            int upperLimit = numberOfPossibleSymbols - 10;
            for (int i = 'a'; i != 'a' + upperLimit; i++) {
                possibleSymbols.add(String.valueOf((char) i));
            }
        }

        Collections.shuffle(possibleSymbols);
        code = String.join("", possibleSymbols.subList(0, codeLength));

        System.out.printf("The secret is prepared: %s %s.\n",
                code.replaceAll(".", "*"),
                getCodeRange(numberOfPossibleSymbols));
        System.out.println("Okay, let's start a game!");
    }

    private int isInputIntegerCorrectFormat(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.printf("Error: '%s' isn't a valid number.\n", input);
            Runtime.getRuntime().exit(1);
        }
        return 0;
    }

    private void checkInputForErrors(int length, int uniqueSymbolsSize) {
        if (length == 0 || uniqueSymbolsSize == 0) {
            System.out.println("Error: Length and number of symbols can't be == 0!");
            Runtime.getRuntime().exit(1);
        }
        else if (length > uniqueSymbolsSize) {
            System.out.println("Error: it's not possible to generate " +
                    "a code with a length of 6 with 5 unique symbols.");
            Runtime.getRuntime().exit(1);
        }
        else if (uniqueSymbolsSize > 36) {
            System.out.println("Error: can't generate a secret number with a " +
                    "length of 11 because there aren't enough unique digits.");
            Runtime.getRuntime().exit(1);
        }
    }

    private String getCodeRange(int numberOfPossibleSymbols) {
        int firstDigit = 0;
        int lastDigit = numberOfPossibleSymbols <= 10 ? numberOfPossibleSymbols - 1 : 9;
        if (numberOfPossibleSymbols <= 10) {
            return String.format("(%d-%d)", firstDigit, lastDigit);
        }
        char firstLetter = 'a';
        char lastLetter = (char) IntStream.iterate('a', operand -> operand = operand + 1)
                .limit(numberOfPossibleSymbols - 10)
                .max()
                .orElse('a');
        return String.format("(%d-%d, %s-%s)", firstDigit, lastDigit,
                firstLetter, lastLetter);
    }
}
