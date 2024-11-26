package clack.cipher;
import java.util.HashSet;

/** PlayfairCipher class is used for encrypting/decrypting text using the Playfair Cipher method.
 * PlayfairCipher uses a 5x5 matrix to encrypt and decrypt pairs of letters. This class replaces the letter 'J'
 * with the letter 'I', as there are 26 letters in the alphabet and only 25 (5x5) spaces in the matrix. 'J' is one
 * of the most uncommon letters in the English language, so that is why we chose to replace it with 'I'.
 */

public class PlayfairCipher {

    private final char[][] matrix;

    /** Constructs a PlayFairCipher instance and generates the 5x5 matrix.
     * @param key is the keyword that is used in the matrix.
     * @throws IllegalArgumentException if the key is just a null value.
     */
    public PlayfairCipher(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null or empty. ");
        }
        this.matrix = generateMatrix(key);
    }

    /** generateMatrix generates the 5x5 matrix that is used for encrypting/decrypting the key.
     * This matrix contains the letters in the key followed by the remaining letters of the alphabet, skipping
     * all duplicates and replacing 'J' with 'I'.
     * generateMatrix implements a HashSet, as HashSets contain unique items (in this case, letters).
     * We found HashSets to be more efficient when creating the matrix of unique letters (no duplicates).
     * @param keyword is the keyword used in the matrix.
     * @return the 5x5 matrix.
     */

    private char[][] generateMatrix(String keyword) {
        // ALPHABET LETTERS WITHOUT THE LETTER 'J'
        String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        StringBuilder characters = new StringBuilder();
        HashSet<Character> letterSet = new HashSet<>();

        for (char c : keyword.toUpperCase().toCharArray()) {
            if (c == 'J') {
                c = 'I'; }

            if (!letterSet.contains(c) && ALPHABET.indexOf(c) >= 0) {
                characters.append(c);
                letterSet.add(c); }
        }

        for (char c : ALPHABET.toCharArray()) {
            if (c != 'J' && !letterSet.contains(c)) {
                characters.append(c);
                letterSet.add(c); }
        }

        // ADDS REMAINING LETTERS OF ALPHABET (NO DUPLICATES) TO MATRIX
        char[][] matrix = new char[5][5];
        for (int i = 0; i < 25; i++) {
            matrix[i / 5][i % 5] = characters.charAt(i);
        }
        return matrix;
    }

    /** encrypt performs encryption of a plaintext message by implementing the
     * checkPairs(String, boolean) method. checkPairs(String, boolean) is a method
     * used for both encryption and decryption.
     * @param plaintext is the plaintext that needs to be encrypted.
     * @return the final message from checkPairs(String, boolean)
     * (if plaintext is null, encrypt returns null).
     */
    public String encrypt (String plaintext) {
        if (plaintext == null) {
            return null; }

        return checkPairs(plaintext, true);
    }

    /** decrypt performs decryption of an encrypted message by implementing
     * the checkPairs(String, boolean) method. checkPairs(String, boolean)
     * is a method used for both encryption and decryption.
     * @param ciphertext is the encrypted text that needs to be decrypted.
     * @return the final message from checkPairs(String, boolean)
     * (if ciphertext is null, then decrypt returns null).
     */
    public String decrypt (String ciphertext) {
        if (ciphertext == null) {
            return null; }

        return checkPairs(ciphertext, false);
    }

    /** prep prepares the plaintext for encryption. It converts the text to uppercase letters,
     * and it replaces all the 'J' letters with 'I' letters. prep also removes non-alphabetic characters
     * and checks if the text length is even/odd. prep also adds padding for repeating characters.
     * @param text is the plaintext that needs to be prepared for encryption.
     * @return the prepared text that is ready for encryption or null if text is null.
     */
    public String prep(String text) {
        if (text == null) {
            return null; }

        // CLEANS TEXT (CONVERTS TO UPPERCASE, REMOVES NON-ALPHABETIC CHARACTERS,
        // AND REPLACES ALL 'J' LETTERS WITH 'I' LETTERS)
        text = text.toUpperCase();
        text = text.replaceAll("[^A-Z]", "");
        text = text.replaceAll("J", "I");

        StringBuilder preparedText = new StringBuilder();
        preparedText.append(text);

        for (int d = 0; d < 10; d++) {
            int count = 1;
            for (int i = 0; i < preparedText.length(); i++) {
                if (count % 2 == 0 && preparedText.charAt(i) == preparedText.charAt(i - 1)) {
                    preparedText.insert(i, 'X');
                    break;
                }
                count++;
            }
        }

        // CHECKS IF LENGTH OF MESSAGE IS ODD, IF SO ADDS A 'Z' AT THE END
        if (preparedText.length() % 2 != 0) {
            preparedText.append('Z'); }

        return preparedText.toString();
    }

    /** checkPairs is a method used for both encryption and decryption.
     * @param text is the text that needs to be encrypted/decrypted.
     * @param encrypt is true for encryption and false for decryption.
     * @return finalMessage (the text after being encrypted/decrypted).
     */
    public String checkPairs (String text, boolean encrypt) {
        StringBuilder finalMessage = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char first = text.charAt(i);
            char second = text.charAt(i + 1);

            // LOCATE THE POSITIONS OF TWO CHARACTERS
            int[] firstPos = findPosition(first);
            int[] secondPos = findPosition(second);

            // 3 DIFFERENT CASES FOR PAIRS:
            // First Case: characters are in same row
            if (firstPos[0] == secondPos[0]) {
                finalMessage.append(this.matrix[firstPos[0]][(firstPos[1] + (encrypt ? 1 : -1) + 5) % 5]);
                finalMessage.append(this.matrix[secondPos[0]][(secondPos[1] + (encrypt ? 1 : -1) + 5) % 5]);

            // Second Case: characters are in same column
            } else if (firstPos[1] == secondPos[1]) {
                finalMessage.append(this.matrix[(firstPos[0] + (encrypt ? 1 : -1) + 5) % 5][firstPos[1]]);
                finalMessage.append(this.matrix[(secondPos[0] + (encrypt ? 1 : -1) + 5) % 5][secondPos[1]]);

           // Third Case: characters make a rectangle
            } else {
                finalMessage.append(this.matrix[firstPos[0]][secondPos[1]]);
                finalMessage.append(this.matrix[secondPos[0]][firstPos[1]]);
            }
        }
        return finalMessage.toString();
    }

    /** findPosition finds the position of characters in the matrix.
     * @param c is the character that needs its position found.
     * @return array that holds the row and column indices of c.
     * @throws IllegalArgumentException if c is not found in the matrix.
     */
    private int[] findPosition (char c) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == c) {
                    return new int[]{row, col}; }
            }
        }
        throw new IllegalArgumentException("Character " + c + " not found in matrix. ");
    }
}
