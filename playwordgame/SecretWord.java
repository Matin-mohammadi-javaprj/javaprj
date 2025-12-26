public class SecretWord {
    
    private final String originalWord;
    private final char[] display;

    public SecretWord(String word) {
        this.originalWord = word.toLowerCase();
        this.display = new char[word.length()];
        
        for (int i = 0; i < display.length; i++) {
            display[i] = '_';
        }
    }

    public void printDisplay() {
        for (char c : display) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public boolean guessLetter(char letter) {
        letter = Character.toLowerCase(letter);
        boolean found = false;
        
        for (int i = 0; i < originalWord.length(); i++) {
            if (originalWord.charAt(i) == letter && display[i] == '_') {
                display[i] = letter;
                found = true;
            }
        }
        return found;
    }

    public boolean isSolved() {
        for (char c : display) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public int getMissingLettersCount() {
        int count = 0;
        for (char c : display) {
            if (c == '_') count++;
        }
        return count;
    }

    public void revealFullWord() {
        for (int i = 0; i < originalWord.length(); i++) {
            display[i] = originalWord.charAt(i);
        }
    }
}