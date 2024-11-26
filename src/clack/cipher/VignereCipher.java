package clack.cipher;

/** VignereCipher is a class created for encrypting and decrypting text using the Vignere cipher method.
 * This cipher uses a keyword to encrypt and decrypt text by shifting each letter of the plaintext
 * by a corresponding letter in the keyword. */
public class VignereCipher {

    private String key;

    /** Constructs an instance of VignereCipher with a given key.
     * @param key is the given keyword.
     * @throws IllegalArgumentException if key is null.
     * Converts key to uppercase letters. */
    public VignereCipher(String key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null. "); }

        this.key = key.toUpperCase();
    }

    /** encrypt performs encryption on plaintext using the Vignere cipher.
     * @param plaintext is the text that needs to be encrypted.
     * @return the encrypted text (ciphertext).
     */
    public String encrypt (String plaintext) {
        plaintext = prepareText(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char cipherChar = (char) ((plainChar + keyChar - 2 * 'A') % 26 + 'A');
            ciphertext.append(cipherChar);
        }
        return ciphertext.toString();
    }

    /** decrypt performs decryption on an encrypted message using the Vignere cipher.
     * @param ciphertext is the encrypted message that needs to be decrypted.
     * @return the decrypted text (plaintext). */
    public String decrypt (String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char plainChar = (char) ((cipherChar - keyChar + 26) % 26 + 'A');
            plaintext.append(plainChar);
        }
        return plaintext.toString();
    }

    /** prepareText prepares given text by removing all non-alphabetic characters and
     * converting the text to uppercase letters.
     * @param text is the text that needs to be prepared.
     * @return the prepared text. */
    public String prepareText (String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
}
