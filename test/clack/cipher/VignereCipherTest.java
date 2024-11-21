package clack.cipher;

import org.junit.jupiter.api.Test;

import static clack.cipher.CharacterCipher.ALPHABET;
import static org.junit.jupiter.api.Assertions.*;

class VignereCipherTest {

    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher(null));
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher("")) ;
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher("AB CD"));
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher("ABcD"));
        assertDoesNotThrow(() -> new VignereCipher("A"));
        assertDoesNotThrow(() -> new VignereCipher("AA"));
        assertDoesNotThrow(() -> new VignereCipher("ABC"));
        assertDoesNotThrow(() -> new VignereCipher("Z"));
        assertDoesNotThrow(() -> new VignereCipher("XYZ"));
        assertDoesNotThrow(() -> new VignereCipher(ALPHABET + ALPHABET));
    }

    /**
     * At present, no need to test prep() -- it is just a call
     * to CharacterCipher.clean().
     */
    @Test
    void prep() {
    }


    @Test
    void encrypt() {
        VignereCipher vc = new VignereCipher(ALPHABET);
        assertEquals(ALPHABET, vc.encrypt("A".repeat(ALPHABET.length())));

        vc = new VignereCipher("EDGARALLANPOE");
        assertEquals("XKKGFLOMUT", vc.encrypt("THEGOLDBUG"));
        assertEquals("UXUTYTSPRNKSRRHBEIMZCE",
                vc.encrypt("QUOTHTHERAVENNEVERMORE"));

    }

    @Test
    void decrypt() {
        VignereCipher vc = new VignereCipher(ALPHABET);
        assertEquals("A".repeat(ALPHABET.length()), vc.decrypt(ALPHABET));

        vc = new VignereCipher("EDGARALLANPOE");
        assertEquals("THEGOLDBUG", vc.decrypt("XKKGFLOMUT"));
        assertEquals("QUOTHTHERAVENNEVERMORE",
                vc.decrypt("UXUTYTSPRNKSRRHBEIMZCE"));
    }
}