package clack.cipher;

/** PlayfairCipher class is a class used for encrypting/decrypting text using the Playfair Cipher method.
 * PlayfairCipher uses a 5x5 matrix to encrypt and decrypt pairs of letters. This class replaces the letter 'J'
 * with the letter 'I', as there are 26 letters in the alphabet and only 25 (5x5) spaces in the matrix. 'J' is one
 * of the most uncommon letters in the English language, so that is why we chose to replace it with 'I'.
 */

public class PlayfairCipher {

    private char[][] matrix;

    /** Constructs a PlayFairCipher instance and generates the 5x5 matrix.
     * @param key is the keyword that is used in the matrix.
     * @throws IllegalArgumentException if the key has a null value or is simply empty.
     */
    public PlayfairCipher(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty. ");
        }
        generateMatrix(key);
    }

    /** generateMatrix generates the 5x5 matrix that is used for encrypting/decrypting the key.
     * This matrix contains the letters in the key followed by the remaining letters of the alphabet, skipping
     * all duplicates and replacing 'J' with 'I'.
     * @param key is the keyword used in the matrix.
     */
    public void generateMatrix(String key) {
        key = key.toUpperCase().replaceAll("J", "I");
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        StringBuilder matrixString = new StringBuilder(key);

        for (char c : alphabet.toCharArray()) {
            if (matrixString.indexOf(String.valueOf(c)) == -1) {
                matrixString.append(c); }
        }

        matrix = new char[5][5];
        int index = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = matrixString.charAt(index++);
            }
        }
    }

    /** encrypt performs encryption of a plaintext message in the Playfair cipher.
     * @param plaintext is the plaintext that needs to be encrypted.
     * @return the encrypted message (if plaintext is null, encrypt returns null).
     */
    public String encrypt (String plaintext) {

        if (plaintext == null) {
            return null; }

        plaintext = prep(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            char first = plaintext.charAt(i);
            char second = plaintext.charAt(i + 1);
            ciphertext.append(encryptPair(first, second));
        }

        return ciphertext.toString();
    }

    /** decrypt performs decryption of an encrypted message using the Playfair cipher.
     * @param ciphertext is the encrypted text that needs to be decrypted.
     * @return the decrypted message (if ciphertext is null, then decrypt returns null).
     */
    public String decrypt (String ciphertext) {

        if (ciphertext == null) {
            return null; }

        StringBuilder plaintext = new StringBuilder();

        for (int i  = 0; i < ciphertext.length(); i += 2) {
            char first = ciphertext.charAt(i);
            char second = ciphertext.charAt(i + 1);
            plaintext.append(decryptPair(first, second));
        }

        return plaintext.toString();
    }

    /** prep prepares the plaintext for encryption. It converts the text to uppercase letters,
     * and it replaces all the 'J' letters with 'I' letters. prep also removes non-alphabet characters
     * and checks if the text length is even/odd.
     * @param text is the plaintext that needs to be prepared for encryption.
     * @throws IllegalArgumentException if text is null.
     * @return the prepared text that is ready for encryption.
     */
    public String prep(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null. "); }

        text = text.toUpperCase().replaceAll("[^A-Z]", "").replaceAll("J", "I");
        StringBuilder preparedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            preparedText.append(current);

            if (i < text.length() - 1 && current == text.charAt(i + 1)) {
                preparedText.append('X'); }
            else if (preparedText.length() % 2 == 0 && i + 1 < text.length()) {
                i++;
                preparedText.append(text.charAt(i)); }
        }

        if (preparedText.length() % 2 != 0) {
            preparedText.append('Z');
        }
        return preparedText.toString();
    }

    /** encryptPair encrypts two characters using the Playfair cipher.
     * @param first is the first character in the pair.
     * @param second is the second character in the pair.
     * @return the encrypted pair.
     */
    public String encryptPair (char first, char second) {

        if (first == 'J') {
            first = 'I'; }

        if (second == 'J') {
            second = 'I'; }

        int[] firstPos = findPosition(first);
        int[] secondPos = findPosition(second);

        if (firstPos[0] == secondPos[0]) {
            return String.valueOf(matrix[firstPos[0]][(firstPos[1] + 1) % 5]) + matrix[secondPos[0]][(secondPos[1] + 1) % 5]; }

        else if (firstPos[1] == secondPos[1]) {
            return String.valueOf(matrix[(firstPos[0] + 1) % 5][firstPos[1]]) + matrix[(secondPos[0] + 1) % 5][secondPos[1]]; }

        else {
            return String.valueOf(matrix[firstPos[0]][secondPos[1]]) + matrix[secondPos[0]][firstPos[1]]; }
    }

    /** decryptPair decrypts a pair of characters using the Playfair cipher.
     * @param firstChar is the first character in the pair.
     * @param secondChar is the second character in the pair.
     * @return the decrypted pair of characters.
     */
    public String decryptPair (char firstChar, char secondChar) {

        if (firstChar == 'J') {
            firstChar = 'I'; }

        if (secondChar == 'J') {
            secondChar = 'I'; }

        int[] firstPos = findPosition(firstChar);
        int[] secondPos = findPosition(secondChar);

        if (firstPos[0] == secondPos[0]) {
            return String.valueOf(matrix[firstPos[0]][(firstPos[1] + 4) % 5]) + matrix[secondPos[0]][(secondPos[1] + 4) % 5];
        } else if (firstPos[1] == secondPos[1]) {
            return String.valueOf(matrix[(firstPos[0] + 4) % 5][firstPos[1]]) + matrix[(secondPos[0] + 4) % 5][secondPos[1]];
        } else {
            return String.valueOf(matrix[firstPos[0]][secondPos[1]]) + matrix[secondPos[0]][firstPos[1]];
        }
    }

    /** findPosition finds the position of characters in the matrix.
     * @param c is the character that needs its position found.
     * @return array that holds the row and column indices of c.
     * @throws IllegalArgumentException if c is not found in the matrix.
     */
    public int[] findPosition (char c) {

        if (c == 'J') {
            c = 'I'; }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    return new int[]{i, j}; }
            }
        }
        throw new IllegalArgumentException("Character not found in matrix. ");
    }
}
