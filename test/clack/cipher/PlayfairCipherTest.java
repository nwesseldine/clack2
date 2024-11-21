package clack.cipher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayfairCipherTest {

    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlayfairCipher(null));
        assertDoesNotThrow(() -> new PlayfairCipher(""));
        assertDoesNotThrow(() -> new PlayfairCipher(" "));
        assertDoesNotThrow(() -> new PlayfairCipher("A"));
        assertDoesNotThrow(() -> new PlayfairCipher("James T. Kirk, Captain"));
    }

    @Test
    void testPrep() {
        PlayfairCipher pc = new PlayfairCipher("James T. Kirk");
        assertEquals("EXERIEEBBTOXOTOXOTSOOT", pc.prep("EERIE, ebb  too?   TOOT soot!!"));
        assertEquals("EXERIEEBBTOXOTOXOTSOOTSZ", pc.prep("EERIE, ebb  too?   TOOT soots!!"));
        assertEquals("BYTHEBOXOK", pc.prep("By the book, ..."));
        assertEquals("IXIZ", pc.prep("J J"));
        assertEquals("", pc.prep(""));
        assertEquals("", pc.prep(" ... !! ..."));
    }

    @Test
    void testEncrypt() {
        PlayfairCipher pc = new PlayfairCipher("James T. Kirk");

        assertNull(pc.encrypt(null));
        assertEquals("", pc.encrypt(""));

        String prepText = pc.prep("By the book, ...");
        assertEquals("HEBDBHPWWF", pc.encrypt(prepText));

        prepText = pc.prep("... hours could seem like days.");
        assertEquals("FQPCCLPNDFISSEDSBAFIZE",
                pc.encrypt(prepText));
    }

    @Test
    void testDecrypt() {
        PlayfairCipher pc = new PlayfairCipher("James T. Kirk") ;

        assertNull(pc.decrypt(null));
        assertEquals("", pc.decrypt(""));

        String prepText = pc.prep("By the book, ...");
        assertEquals(prepText, pc.decrypt("HEBDBHPWWF"));

        prepText = pc.prep("... hours could seem ljke days.");
        assertEquals(prepText, pc.decrypt("FQPCCLPNDFISSEDSBAFIZE"));
    }

}