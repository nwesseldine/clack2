package clack.cipher;

/**
 * Abstract class for ciphers that work on character data.
 */
public abstract class CharacterCipher {
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Return a copy of a string, but reformatted into groups of
     * <em>n</em> non-whitespace characters, with groups separated
     * by a space. The last group may have fewer than <em>n</em>
     * characters.
     * @param str the string to break into groups
     * @param n how many characters in each group
     * @return the grouped version of the argument string
     */
    public static String group(String str, int n) {
        // TODO implement this
        return "";
    }

    /**
     * Returns the character that is n letters further on in ALPHABET,
     * with wrap around at the end of ALPHABET. Negative values are
     * allowed and cause a shift to the left. A shift of 0 returns
     * the original character.
     * @param c the character to shift.
     * @param n the number of places to shift the character.
     * @return the character at the location n places beyond c.
     * @throws IllegalArgumentException if c is not in ALPHABET.
     */
    public static char shift(char c, int n) {
        if (ALPHABET.indexOf(c) < 0 ) {
            throw new IllegalArgumentException(
                    "Argument ('" + c + "') not in ALPHABET");
        }
        // TODO implement remainder of this.
        return ' ';
    }

    /**
     * Returns the string resulting from shifting each character of str
     * by n places.
     * @param str the string to shift.
     * @param n the amount to shift each letter.
     * @return the shifted version of str.
     */
    public static String shift(String str, int n) {
        // TODO implement this.
        return "";
    }

    /**
     * Prepare cleartext for encrypting. At minimum this requires
     * removing spaces, punctuation, and non-alphabetic characters,
     * then uppercasing what's left. Other cipers, such as PLAYFAIR,
     * may have additional preparation that this method needs to do.
     * @param cleartext
     * @return a version of the cleartext ready for encrypting.
     */
    abstract String prep(String cleartext);

    /**
     * Encrypt a string that's been prepared for encryption.
     * @param preptext a version of a cleartext string, prepared
     *                 for encryption.
     * @return the encryption of the preptext.
     */
    abstract String encrypt(String preptext);

    /**
     * Decrypts an encrypted string. The decrypted text should match
     * the preptext that was encrypted.
     * @param ciphertext the encrypted string to decrypt.
     * @return the decryption of the ciphertext.
     */
    abstract String decrypt(String ciphertext);
}
