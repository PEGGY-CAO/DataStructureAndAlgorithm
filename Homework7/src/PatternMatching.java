import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author YOUR NAME HERE
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is illegal");
        }
        if (text == null) {
            throw new IllegalArgumentException("text is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null");
        }
        List<Integer> returnList = new LinkedList<>();
        //build failure table
        int[] failureTable = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (i < text.length() && (i + pattern.length() - j - 1) < text.length()) {

            System.out.println("text: " + text.charAt(i) + " pattern: " + pattern.charAt(j));
            System.out.println(comparator.getCount());
            if (comparator.compare(text.charAt(i), pattern.charAt(j)) == 0) {
                if (j == pattern.length() - 1) {
                    returnList.add(i - j);
                    j = failureTable[j - 1];
                }
                i++;
                j++;
            } else if (j > 0) {
                j = failureTable[j - 1];

            } else {
                //when j = 0
                if (i + pattern.length() < text.length()) {
                    i++;
                } else {
                    i = text.length();
                }
            }
        }
        return returnList;
    }




    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The t the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("pattern or comparator cannot be null");
        }
        int[] failureTable = new int[pattern.length()];
        if (pattern.length() == 0) {
            return failureTable;
        } else {
            failureTable[0] = 0;
            int i = 1;
            int j = 0;
            int length = pattern.length();
            while (i < length) {
                //we have matched j + 1 chars
                int a = comparator.compare(pattern.charAt(i), pattern.charAt(j));
                if (a == 0) {
                    failureTable[i] = j + 1;
                    i++;
                    j++;
                    // use failure function to shift pattern
                } else if (j > 0) {
                    j = failureTable[j - 1];
                    // no prefix match
                } else { //when j = 0
                    failureTable[i] = 0;
                    i++;
                }
            }
            return failureTable;
        }
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                       CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Input pattern cannot be empty.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Input text cannot be empty");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Input comparator cannot be empty");
        }
        List<Integer> returnList = new LinkedList<>();
        Map<Character, Integer> lastOccurTable = buildLastTable(pattern);
        int n = text.length();
        int m = pattern.length();
        int i = 0;
        while (i <= (n - m)) {
            int j = m - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j < 0) {
                returnList.add(i);
                i++;
            } else {
                if (lastOccurTable.get(text.charAt(i + j)) == null) {
                    i = i + j + 1;
                } else if (lastOccurTable.get(text.charAt(i + j)) < j) {
                    i = i + j - lastOccurTable.get(text.charAt(i + j));
                } else {
                    i++;
                }
            }
        }
        return returnList;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Input pattern cannot be null");
        }
        Map<Character, Integer> lastOcurrTable = new HashMap<>();
        if (pattern.length() == 0) {
            return lastOcurrTable;
        } else {
            for (int i = 0; i < pattern.length(); i++) {
                if (lastOcurrTable.get(pattern.charAt(i)) == null) {
                    lastOcurrTable.put(pattern.charAt(i), i);
                }
                if (lastOcurrTable.get(pattern.charAt(i)) < i) {
                    lastOcurrTable.replace(pattern.charAt(i), i);
                }
            }
            return lastOcurrTable;
        }
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 137;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 137 hash
     * = b * 137 ^ 3 + u * 137 ^ 2 + n * 137 ^ 1 + n * 137 ^ 0 = 98 * 137 ^ 3 +
     * 117 * 137 ^ 2 + 110 * 137 ^ 1 + 110 * 137 ^ 0 = 254203747
     *
     * Note that since you are dealing with very large numbers here, your hash
     * will likely overflow, and that is fine for this implementation.
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 137
     * hash("unny") = (hash("bunn") - b * 137 ^ 3) * 137 + y * 137 ^ 0 =
     * (254203747 - 98 * 137 ^ 3) * 137 + 121 * 137 ^ 0 = 302928082
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                      CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Input pattern cannot be empty.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Input text cannot be empty");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Input comparator cannot be empty");
        }
        List<Integer> returnList = new LinkedList<>();
        int n = text.length();
        int m = pattern.length();
        int hashOfP = 0;
        int hashOft = 0;
        int min = Math.min(m, n);
        int multiplier = 1;
        int multiplierForUpdating = 0;
        for (int i = 0; i < m; i++) {
            multiplier *= BASE;
            if (i == m - 2) {
                multiplierForUpdating = multiplier;
            }
            hashOfP *= BASE;
            hashOfP += pattern.charAt(i);
            if (i < min) {
                hashOft *= BASE;
                hashOft += text.charAt(i);
            }
        }
        int i = 0;
        while (i <= (n - m)) {
            if (hashOfP == hashOft) {
                int j = 0;
                while (j < m && comparator.compare(pattern.charAt(j), text.charAt(i + j)) == 0) {
                    j++;
                }
                if (j == m) {
                    returnList.add(i);
                }
            }
            i++;
            if (i <= (n - m)){
                hashOft -= text.charAt(i - 1) * multiplierForUpdating;
                hashOft *= BASE;
                hashOft += text.charAt(i + m - 1);
                //hashOft = (hashOft - (double) text.charAt(i - 1) * Math.pow(BASE, m - 1)) * BASE + (int) text.charAt(i + m - 1);
            }
        }
        return  returnList;
    }

}