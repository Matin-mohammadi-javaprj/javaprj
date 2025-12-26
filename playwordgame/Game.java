import java.util.Random;

public class Game {
    
    private SecretWord secretWord;
    private int remainingAttempts;
    private String guessedLetters;
    private int score;
    
    private static final String[] WORD_LIST = {
       "computer", "java", "book", "pen", "bird",
        "lion", "game", "gun", "snake", "cat",
        "dog", "football", "class", "player", "car"
    };

    private static final int INITIAL_ATTEMPTS = 15;

    private static String getRandomWord() {
        Random random = new Random();
        return WORD_LIST[random.nextInt(WORD_LIST.length)];
    }

    public Game() {
        this.secretWord = new SecretWord(getRandomWord());
        this.remainingAttempts = INITIAL_ATTEMPTS;
        this.guessedLetters = "";
        this.score = 0;
    }

    private void displayStatus() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("Word: ");
        secretWord.printDisplay();
        
        System.out.println("Attempts left: " + remainingAttempts);
        System.out.println("Current score: " + score);
        
        if (!guessedLetters.isEmpty()) {
            System.out.println("Guessed letters: " + guessedLetters.trim());
        }
        System.out.println("Missing letters: " + secretWord.getMissingLettersCount());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private boolean isAlreadyGuessed(char letter) {
        return guessedLetters.indexOf(letter) != -1;
    }

    private void newRound() {
        secretWord = new SecretWord(getRandomWord());
        guessedLetters = "";
    }

    @SuppressWarnings("ConvertToStringSwitch")
    public void play(java.util.Scanner scanner) {
        System.out.println("<<< GAME SESSION STARTED >>>");
        System.out.println("Total attempts: " + INITIAL_ATTEMPTS);
        System.out.println("Scoring:");
        System.out.println("  * +1 point for each correct unique letter");
        System.out.println("  * +5 points bonus for completing a word");
        System.out.println("  * +5 extra points for correctly guessing the full word");
        System.out.println("Full word guess available when at least two letters are missing");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        boolean gameOver = false;

        while (!gameOver && remainingAttempts > 0) {
            newRound();
            System.out.println("NEW WORD! Remaining attempts: " + remainingAttempts + "\n");

            while (remainingAttempts > 0 && !secretWord.isSolved()) {
                displayStatus();

                System.out.println("Choose your action:");
                System.out.println("  1 - Guess a single letter");
                System.out.println("  2 - Guess the full word" + 
                                  (secretWord.getMissingLettersCount() < 2 ? " (not available yet)" : ""));
                System.out.print("Your choice (1 or 2): ");

                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    System.out.print("Enter a single letter: ");
                    String letterInput = scanner.nextLine().trim().toLowerCase();

                    if (letterInput.length() == 1 && Character.isLetter(letterInput.charAt(0))) {
                        char letter = letterInput.charAt(0);

                        if (isAlreadyGuessed(letter)) {
                            System.out.println("Already tried '" + letter + "'!");
                            continue;
                        }

                        guessedLetters += letter + " ";

                        boolean correct = secretWord.guessLetter(letter);

                        if (correct) {
                            System.out.println("Correct! +1 point");
                            score += 1;
                        } else {
                            System.out.println("Wrong! -1 attempt");
                            remainingAttempts--;
                        }
                    } else {
                        System.out.println("Please enter exactly one letter!");
                    }
                } 
                else if (choice.equals("2")) {
                    if (secretWord.getMissingLettersCount() < 2) {
                        System.out.println("Full word guess not available yet (need at least 2 missing letters)");
                        continue;
                    }

                    System.out.print("Enter your full word guess: ");
                    String wordGuess = scanner.nextLine().trim().toLowerCase();

                    if (wordGuess.isEmpty()) {
                        System.out.println("No guess entered.");
                        continue;
                    }

                    if (wordGuess.equals(secretWord.getOriginalWord())) {
                        System.out.println("PERFECT! Full word correct!!!");
                        System.out.println("Extra +5 points for full word guess!");
                        score += 5;
                        secretWord.revealFullWord();
                    } else {
                        System.out.println("Wrong full word! -5 attempts!!!");
                        remainingAttempts -= 5;
                    }
                } 
                else {
                    System.out.println("Invalid choice! Please enter 1 or 2.");
                }

                System.out.println();
            }

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            if (secretWord.isSolved()) {
                System.out.println("CONGRATULATIONS! Word found!");
                System.out.println("The word was: " + secretWord.getOriginalWord());
                score += 5;
                System.out.println("BONUS: +5 points for completing the word!");
            } else {
                System.out.println("No attempts left! The word was: " + secretWord.getOriginalWord());
                System.out.println("GAME OVER!");
                gameOver = true;
            }

            System.out.println("Current total score: " + score);
            System.out.println("Remaining attempts: " + remainingAttempts);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }

        if (gameOver) {
            System.out.println("FINAL SCORE: " + score);
            System.out.println("Game session ended. Returning to main menu...");
        }
    }
}