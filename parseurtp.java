package exer;

import java.util.*;

public class RecursiveParser {
    private final String sequence;
    private int currentIndex;

    public RecursiveParser(String sequence) {
        this.sequence = sequence;
        this.currentIndex = 0;
    }

    public boolean processS() {
        if (isAtEnd()) {
            return true;
        }

        // Rule S -> bSb
        return (check('b') && processS() && check('b')) ||
               // Rule S -> cAc
               (check('c') && processA() && check('c'));
    }

    public boolean processA() {
        if (isAtEnd()) {
            return true;
        }

        // Rule A -> bAA
        return (check('b') && processA() && processA()) ||
               // Rule A -> cASAb
               (check('c') && processA() && processS() && processA() && check('b')) ||
               // Rule A -> dcb
               (check('d') && check('c') && check('b'));
    }

    private boolean check(char expectedChar) {
        if (currentIndex < sequence.length() && sequence.charAt(currentIndex) == expectedChar) {
            currentIndex++;
            return true;
        }
        return false;
    }

    private boolean isAtEnd() {
        return currentIndex == sequence.length();
    }

    public boolean parse() {
        return processS() && isAtEnd();
    }

    public static void main(String[] args) {
        List<String> examples = Arrays.asList(
            "cdcbc",
            "bcdcbcb",
            "cbdcbdcbc",
            "ccdcbcdcbcdcbbcr",
            "cdcbbb",
            "cdcb",
            ""
        );

        for (String example : examples) {
            RecursiveParser parser = new RecursiveParser(example);
            boolean result = parser.parse();
            System.out.println("Input: \"" + example + "\" -> " + (result ? "Valid" : "Invalid"));
        }
    }
}
