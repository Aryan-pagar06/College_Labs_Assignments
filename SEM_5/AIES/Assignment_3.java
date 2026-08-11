import java.util.*;

public class Cryptarithmetic {

    // Helper method to convert a word into its numeric value based on current assignment
    private static long getValue(String word, Map<Character, Integer> assignment) {
        long num = 0;
        for (int i = 0; i < word.length(); i++) {
            num = num * 10 + assignment.get(word.charAt(i));
        }
        return num;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first word: ");
        String word1 = scanner.next().toUpperCase();

        System.out.print("Enter second word: ");
        String word2 = scanner.next().toUpperCase();

        System.out.print("Enter result word: ");
        String result = scanner.next().toUpperCase();

        // Find unique letters maintaining insertion order
        List<Character> letters = new ArrayList<>();
        String combined = word1 + word2 + result;
        for (int i = 0; i < combined.length(); i++) {
            char ch = combined.charAt(i);
            if (!letters.contains(ch)) {
                letters.add(ch);
            }
        }

        if (letters.size() > 10) {
            System.out.println("More than 10 unique letters. No solution possible.");
            scanner.close();
            return;
        }

        // Keep track of letters that cannot be zero (leading letters)
        Set<Character> leading = new HashSet<>();
        leading.add(word1.charAt(0));
        leading.add(word2.charAt(0));
        leading.add(result.charAt(0));

        Map<Character, Integer> assignment = new HashMap<>();
        boolean[] used = new boolean[10];
        boolean[] solved = new boolean[1]; // Wrapper array to safely track solution across recursive calls

        solvePermutations(0, letters, leading, assignment, used, word1, word2, result, solved);

        if (!solved[0]) {
            System.out.println("No solution found.");
        }
        scanner.close();
    }

    // Recursive backtracking to simulate Python's itertools.permutations
    private static void solvePermutations(int index, List<Character> letters, Set<Character> leading, 
                                           Map<Character, Integer> assignment, boolean[] used, 
                                           String word1, String word2, String result, boolean[] solved) {
        // If a solution has already been found somewhere down the recursive chain, halt immediately
        if (solved[0]) return;

        // Base case: All letters have been assigned a unique digit
        if (index == letters.size()) {
            long n1 = getValue(word1, assignment);
            long n2 = getValue(word2, assignment);
            long n3 = getValue(result, assignment);

            if (n1 + n2 == n3) {
                solved[0] = true;
                System.out.println("\nSolution:");
                for (char ch : letters) {
                    System.out.println(ch + " = " + assignment.get(ch));
                }
                System.out.println("\nVerification:");
                System.out.println(n1 + " + " + n2 + " = " + n3);
            }
            return;
        }

        char currentLetter = letters.get(index);

        // Try assigning digits 0-9 to the current letter
        for (int digit = 0; digit <= 9; digit++) {
            if (!used[digit]) {
                // Rule validation: Leading letter cannot be 0
                if (digit == 0 && leading.contains(currentLetter)) {
                    continue;
                }

                // Place choice
                used[digit] = true;
                assignment.put(currentLetter, digit);

                // Move to the next letter
                solvePermutations(index + 1, letters, leading, assignment, used, word1, word2, result, solved);

                // Undo choice (backtrack)
                assignment.remove(currentLetter);
                used[digit] = false;
            }
        }
    }
}
