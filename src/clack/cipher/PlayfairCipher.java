package clack.cipher;

public class PlayfairCipher {

    // the PlayFair cipher is a 5x5 matrix
    private char[][] matrix;

    // this constructor initializes the PlayFair cipher by generating the key matrix using the key
    public PlayfairCipher(String key) {
        generateMatrix(key);
    }

    public void generateMatrix(String key) {
        // replaces all Js in key with letter I. J is one of the most uncommon letters in English,
        // and we need to drop one letter in the alphabet (26 letters) because 5x5=25.
        key = key.toUpperCase().replaceAll("J", "I");
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        StringBuilder matrixString = new StringBuilder(key);

        // constructs matrix by appending letters from key and then rest of alphabet (skips duplicates)
        for (char c : alphabet.toCharArray()) {
            if (matrixString.indexOf(String.valueOf(c)) == -1) {
                matrixString.append(c);
            }
        }

        // creates new 5x5 PlayFair matrix
        matrix = new char[5][5];
        int index = 0;

        // puts elements of matrixString in 5x5 grid
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = matrixString.charAt(index++);
            }
        }
    }

    // this method encrypts a given plaintext message using PlayFair cipher
    public String encrypt (String plaintext) {
        plaintext = prep(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            char first = plaintext.charAt(i);
            char second = plaintext.charAt(i + 1);
            ciphertext.append(encrypt(first, second));
        }

        return ciphertext.toString();
    }

    // this method basically reverses the encryption process
    public String decrypt (String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i  = 0; i < ciphertext.length(); i += 2) {
            char first = ciphertext.charAt(i);
            char second = ciphertext.charAt(i + 1);
            plaintext.append(decrypt(first, second));
        }

        return plaintext.toString();
    }

    // prepares the plaintext, converts to uppercase, replaces all Js with Is,
    // removes spaces, appends X if length is odd to ensure pairs
    public String prep(String text) {
        text = text.toUpperCase().replaceAll("J", "I").replaceAll("\\s", "");
        if (text.length() % 2 != 0) {
            text += "X";
        }
        return text;
    }

    // if two letters are in same row, shift right
    // if they are in same column, shift down
    // if they form a rectangle, swap the columns
    public String encrypt (char first, char second) {
        int[] firstPos = findPosition(first);
        int[] secondPos = findPosition(second);

        if (firstPos[0] == secondPos[0]) {
            return String.valueOf(matrix[firstPos[0]][(firstPos[1] + 1) % 5]) + matrix[secondPos[0]][(secondPos[1] + 1) % 5];
        } else if (firstPos[1] == secondPos[1]) {
            return String.valueOf(matrix[(firstPos[0] + 1) % 5][firstPos[1]]) + matrix[(secondPos[0] + 1) % 5][secondPos[1]];
        } else {
            return String.valueOf(matrix[firstPos[0]][secondPos[1]]) + matrix[secondPos[0]][firstPos[1]];
        }
    }

    // opposite of encryptDigraph, if in same row, shift left
    // if in same column, shift up
    // if forming a rectangle, swap the columns
    public String decrypt (char firstChar, char secondChar) {
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

    // method that finds the row and column of a character in the matrix
    public int[] findPosition (char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
