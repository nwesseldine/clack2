package clack.cipher;

public class VignereCipher {

    private String key;

    // initializes the VignereCipher with a given key, converts to uppercase
    public VignereCipher(String key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null. ");
        }
        this.key = key.toUpperCase();
    }

    // iterates through plaintext, shifts each character by corresponding character
    // in repeating key, handles wrapping with modulo arithmetic
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

    // reverses encryption process using modular subtraction
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

    // prepares input by removing non-alphabetic characters and converting to uppercase
    public String prepareText (String text) {
        return text.toUpperCase().replaceAll("[A-Z]", "");
    }
}
