package clack.cipher;

import org.junit.jupiter.api.Test;

import static clack.cipher.CharacterCipher.*;
import static org.junit.jupiter.api.Assertions.*;

class CharacterCipherTest {

    /**
     * Test clean().
     */
    @Test
    void testClean() {
        assertNull(clean(null)) ;
        assertEquals("", clean(""));
        assertEquals("", clean(" "));
        assertEquals("A", clean("\n\t a \t \n "));
        assertEquals("ABBBC", clean(" a \n b Bb \t c\n"));
        // clean() should be idempotent.
        String s = clean("'Twas brillig, and the slithy Toves ...");
        assertEquals(s, clean(s));
    }

    @Test
    void testGroup() {
        // Bad n
        assertThrows(IllegalArgumentException.class,
                () -> group(null, 0));
        assertThrows(IllegalArgumentException.class,
                () -> group(null, -1));
        assertThrows(IllegalArgumentException.class,
                () -> group("some text", 0));
        assertThrows(IllegalArgumentException.class,
                () -> group("some text", -1));
        // str == null
        assertNull(group(null, 1));
        assertNull(group(null, 2));
        // empty string
        assertEquals("", group("", 1));
        assertEquals("", group("", 2));
        // str.length() < n
        assertEquals("a", group("a", 2));
        assertEquals("a", group("a", 3));
        // str.length() == n
        assertEquals("a", group("a", 1));
        assertEquals("ab", group("ab", 2));
        // str.length() > n but < 2n
        assertEquals("abc d", group("abcd", 3));
        assertEquals("abcd ef", group("abcdef", 4));
        assertEquals("abcde fghi", group("abcdefghi", 5));
        // str.length() == 2n
        assertEquals("ab cd", group("abcd", 2));
        assertEquals("abc def", group("abcdef", 3));
        // str.length() > 2n
        assertEquals("abcde fghij kl", group("abcdefghijkl", 5));
        // non-alphabet characters in str
        assertEquals("   ", group("  ", 1));
        assertEquals(" a b ", group(" ab ", 2));
        assertEquals(" !@#$ &* ^+ |", group(" !@#$&* ^+|", 5));

    }

    @Test
    void testMod() {
        // Bad modulus
        assertThrows(IllegalArgumentException.class,
                () -> mod(0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> mod(5, 0));
        assertThrows(IllegalArgumentException.class,
                () -> mod(5, -1));
        assertThrows(IllegalArgumentException.class,
                () -> mod(5, -5));
        assertThrows(IllegalArgumentException.class,
                () -> mod(5, -7));
        // i mod 1 == 0, for all i.
        for (int i = -2; i < 3; ++i) {
            assertEquals(0, mod(i, 1));
        }
        // 0 mod i == 0, for all positive i.
        for (int i = 1; i < 5; ++i) {
            assertEquals(0, mod(0, i));
        }
        // i mod (abs(i)) == 0 for all non-zero i.
        for (int i = 1; i < 5; ++i) {
            assertEquals(0, mod(i, i));
            assertEquals(0, mod(-i, i));
        }
        // Some non-trivial cases
        assertEquals(5, mod(12, 7));
        assertEquals(5, mod(19, 7));
        assertEquals(5, mod(26, 7));
        assertEquals(5, mod(-2, 7));
        assertEquals(5, mod(-9, 7));
        assertEquals(5, mod(-16, 7));
    }

    @Test
    void testShiftChar() {
        assertThrows(IllegalArgumentException.class,
                () -> shift(' ', 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift('a', 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift('*', 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift('5', 0));

        for (int n = -2; n < 3; ++n) {
            int shiftAmt = n * ALPHABET.length();
            assertEquals('Z', shift('A', shiftAmt - 1));
            assertEquals('A', shift('A', shiftAmt));
            assertEquals('B', shift('A', shiftAmt + 1));

            assertEquals('L', shift('M', shiftAmt - 1));
            assertEquals('M', shift('M', shiftAmt));
            assertEquals('N', shift('M', shiftAmt + 1));

            assertEquals('Y', shift('Z', shiftAmt - 1));
            assertEquals('Z', shift('Z', shiftAmt));
            assertEquals('A', shift('Z', shiftAmt + 1));
        }
    }

    @Test
    void testShiftString() {
        assertThrows(IllegalArgumentException.class,
                () -> shift(" ", 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift("a", 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift("*", 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift("5", 0));

        assertNull(shift(null, -1));
        assertNull(shift(null, 0));
        assertNull(shift(null, 1));
        assertEquals("", shift("", -1));
        assertEquals("", shift("", 0));
        assertEquals("", shift("", 1));
        assertEquals("Z", shift("A", -1));
        assertEquals("A", shift("A", 0));
        assertEquals("B", shift("A", 1));
        assertEquals("ZABCDEFGHIJKLMNOPQRSTUVWXY", shift(ALPHABET, -1));
        assertEquals(ALPHABET, shift(ALPHABET, 0));
        assertEquals("BCDEFGHIJKLMNOPQRSTUVWXYZA", shift(ALPHABET, 1));
    }
}