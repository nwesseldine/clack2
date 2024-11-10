package clack.cipher;

public class CaesarCipher extends CharacterCipher {

    private final int key;

    /**
     * Constructs a CaesarCipher object that encrypts by shifting
     * a letter 'key' positions further right in ALPHABET (with
     * wrap-around). 'key' may be negative, in which case the shift
     * is leftward. A key of 0 is also allowed, but results in the
     * null cipher.
     * @param key how far to shift each letter.
     */
    public CaesarCipher(int key) {
        this.key = key % ALPHABET.length();
    }

    /**
     * Constructs a CaesarCipher object, where the shift is
     * given by taking the key's first letter, then finding the
     * location of this letter in ALPHABET.
     *
     * @param key the string whose first letter determines the shift.
     */
    public CaesarCipher(String key) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(
                    "Need a non-null, non-empty string");
        }
        char shiftChar = key.charAt(0);
        this.key = ALPHABET.indexOf(shiftChar);
        if (this.key < 0) {
            throw new IllegalArgumentException(
                    "First character of 'key' argument not in ALPHABET");
        }
    }

    /**
     * Prepare cleartext for encrypting. At minimum this requires
     * removing spaces, punctuation, and non-alphabetic characters,
     * then uppercasing what's left.
     *
     * @param cleartext
     * @return a version of the cleartext ready for encrypting.
     */
    @Override
    String prep(String cleartext) {
        return cleartext.toUpperCase().replaceAll("^[A-Z]", "");
    }

    /**
     * Encrypt a string that's been prepared for encryption.
     *
     * @param preptext a version of a cleartext string, prepared
     *                 for encryption.
     * @return the encryption of the preptext.
     */
    @Override
    String encrypt(String preptext) {
        return CharacterCipher.shift(preptext, key);
    }

    /**
     * Decrypts an encrypted string. The decrypted text should match
     * the preptext that was encrypted.
     *
     * @param ciphertext the encrypted string to decrypt.
     * @return the decryption of the ciphertext.
     */
    @Override
    String decrypt(String ciphertext) {
        return CharacterCipher.shift(ciphertext, -key);
    }
}
