package Classes;

public class ROTX {

    // Encryption function
    public static String encryptROT(String input, int x) {
        StringBuilder encryptedString = new StringBuilder();

        // Normalisation of X to be in the interval [0, 25]
        x = x % 26; // ensure that x is in [0, 25]
        if (x < 0) {
            x += 26; // IF X is negative we adjust it to be positive
        }
        // Iterate through each character of the string
        for (char character : input.toCharArray()) {
            // Check if the character is a letter (uppercase or lowercase)
            if (Character.isLetter(character)) {
                // Determine the base depending on whether the letter is uppercase or lowercase
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                // Apply ROT(X) encryption
                char encryptedChar = (char) ((character - base + x) % 26 + base);
                encryptedString.append(encryptedChar);
            } else {
                // Append non-alphabetic characters without changing them
                encryptedString.append(character);
            }
        }

        return encryptedString.toString();
    }

    // Decryption function
    public static String decryptROT(String input, int x) {
        StringBuilder decryptedString = new StringBuilder();

        // Normalisation of X to be in the interval [0, 25]
        x = x % 26; // ensure that x is in [0, 25]
        if (x < 0) {
            x += 26; // IF X is negative we adjust it to be positive
        }
        // Iterate through each character of the string
        for (char character : input.toCharArray()) {
            // Check if the character is a letter (uppercase or lowercase)
            if (Character.isLetter(character)) {
                // Determine the base depending on whether the letter is uppercase or lowercase
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                // Apply ROT(X) decryption (subtract x instead of adding it)
                char decryptedChar = (char) ((character - base - x + 26) % 26 + base);
                decryptedString.append(decryptedChar);
            } else {
                // Append non-alphabetic characters without changing them
                decryptedString.append(character);
            }
        }

        return decryptedString.toString();
    }
}
