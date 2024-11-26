package clack.cipher;
//
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
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0. "); }

        if (str == null) {
            return null; }

        StringBuilder groups = new StringBuilder();

        for (int i = 1; i * n < str.length(); i++) {
            groups.insert(i * n + (i - 1), ' ');
        }

        return groups.toString();
    }

    /** Mod computes the modulus of an integer.
     * @throws IllegalArgumentException if the modulus is negative or zero.
     * @param n is the integer that the program computes the modulus for.
     * @param modulus is the modulus value (has to be >= 1).
     * @return the positive modulus result. */
    public static int mod(int n, int modulus) {
        if (modulus < 1) {
            throw new IllegalArgumentException("Modulus must be >= 1"); }

        return ((n % modulus) + modulus) % modulus;
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
        if (ALPHABET.indexOf(c) < 0) {
            throw new IllegalArgumentException(
                    "Argument ('" + c + "') not in ALPHABET"); }

        int cPos = ALPHABET.indexOf(c);
        int shiftPos = mod(n + cPos, ALPHABET.length());
        return ALPHABET.charAt(shiftPos);
    }

    /**
     * Returns the string resulting from shifting each character of str
     * by n places.
     * @param str the string to shift.
     * @param n the amount to shift each letter.
     * @return the shifted version of str.
     */
    public static String shift(String str, int n) {
        if (str == null) {
            return null; }

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; ++i) {
            if (ALPHABET.indexOf(chars[i]) < 0) {
                throw new IllegalArgumentException("Argument not in ALPHABET. "); }
            chars[i] = shift(chars[i], n);
        }
        return new String(chars);
    }

    /**
     * Prepare cleartext for encrypting. At minimum this requires
     * removing spaces, punctuation, and non-alphabetic characters,
     * then uppercasing what's left. Other cipers, such as PLAYFAIR,
     * may have additional preparation that this method needs to do.
     * @param cleartext
     * @return a version of the cleartext ready for encrypting.
     */
    public abstract String prep(String cleartext);

    /**
     * Encrypt a string that's been prepared for encryption.
     * @param preptext a version of a cleartext string, prepared
     *                 for encryption.
     * @return the encryption of the preptext.
     */
    public abstract String encrypt(String preptext);

    /**
     * Decrypts an encrypted string. The decrypted text should match
     * the preptext that was encrypted.
     * @param ciphertext the encrypted string to decrypt.
     * @return the decryption of the ciphertext.
     */
    public abstract String decrypt(String ciphertext);

    /**
     * Removes all non-alphabet characters from a string, and
     * uppercases all remaining letters. This is a utility
     * method, useful in implementing prep(). If the argument
     * is null or empty, returns it as it is.
     *
     * @param str the string to clean
     * @return the cleaned string (which might be empty), or null.
     */
    public static String clean(String str) {
        if (str == null) {
            return null; }

        return str.toUpperCase().replaceAll("[^A-Z]", "");
    }
}
