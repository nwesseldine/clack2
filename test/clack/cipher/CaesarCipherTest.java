package clack.cipher;

import org.junit.jupiter.api.Test;

import static clack.cipher.CharacterCipher.ALPHABET;
//import static clack.cipher.CharacterCipher.clean;
import static clack.cipher.CharacterCipher.clean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CaesarCipherTest {

    @Test
    void testConstructorInt() {
        CaesarCipher cc;
        for (int n = -2; n < 3; ++n) {
            int ccShift = n * ALPHABET.length();
            cc = new CaesarCipher(ccShift - 1);
            assertEquals("WXYZAB", cc.encrypt("XYZABC"));
            cc = new CaesarCipher(ccShift);
            assertEquals("XYZABC", cc.encrypt("XYZABC"));
            cc = new CaesarCipher(ccShift + 1);
            assertEquals("YZABCD", cc.encrypt("XYZABC"));
        }
    }

    @Test
    void testConstructorString() {
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(null));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(""));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(" "));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher("x"));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher("%"));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher("5"));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(" SECRET"));

        String[] testkeys =
                {"A", "A*", "AB", "P", "P*", "PQ", "Z", "Z*", "ZA"};
        for (String key : testkeys) {
            CaesarCipher cc = new CaesarCipher(key);
            // "A" should encrypt to first letter of key.
            assertEquals(key.substring(0, 1), cc.encrypt("A"));
        }

    }

    /**
     * Test prep(). Should behave exactly as CharacterCipher.clean().
     */
    @Test
    void testPrep() {
        CaesarCipher cc = new CaesarCipher(10);     // arbitrary key.
        String[] testStrs = {
                null, "", " ", " a ", " a b ", " a BbB c "
        };
        for (String ts : testStrs) {
            assertEquals(clean(ts), cc.prep(ts));
        }
    }

    @Test
    void encrypt() {
        CaesarCipher cc = new CaesarCipher(53);
        assertEquals("BCDEFGHIJKLMNOPQRSTUVWXYZA", cc.encrypt(ALPHABET)) ;
    }

    @Test
    void decrypt() {
        CaesarCipher cc = new CaesarCipher(53);
        assertEquals(ALPHABET, cc.decrypt("BCDEFGHIJKLMNOPQRSTUVWXYZA"));
    }
}