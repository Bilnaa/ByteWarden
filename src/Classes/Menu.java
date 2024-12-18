package Classes;

import java.util.Scanner;
import Classes.ROTX;
import Classes.VigenereAlgo;
import Classes.RC4;
import Classes.Enigma.Enigma;
import Classes.Enigma.Plugboard;
import Classes.Enigma.Reflector;
import Classes.Enigma.Rotors;
public class Menu {


    ////////////// ENCRYPTION ///////////
    // Methods for each encryption algorithm
    public static String encryptEnigma(String password) {
        // Enigma algorithm implementation
        return "Enigma Encryption: " + password;  // Simplified example
    }

    public static String encryptRC4(String password) {
        // RC4 algorithm implementation
        return " RC4.encrypt(password);"; // Simplified example
    }

    public static String encryptROT(String password, int x) {
        // ROT(x) algorithm implementation
        return ROTX.encryptROT(password, x);
    }

    public static String encryptVigenere(String password, String key) {
        // Use the VigenereAlgo class to encrypt with the Vigenère algorithm
        return VigenereAlgo.encrypt(password, key);
    }


    /////////// DECRYPTION ///////////
    // Methods for each decryption algorithm
    public static String decryptEnigma(String encryptedMessage) {
        // Enigma algorithm decryption (adapt as per actual algorithm)
        return "Enigma Decryption: " + encryptedMessage;  // Simplified example
    }

    public static String decryptRC4(String encryptedMessage) {
        // RC4 algorithm decryption
        return "RC4 Decryption: " + encryptedMessage;  // Simplified example
    }

    public static String decryptROT(String input, int x) {
        // ROT(x) algorithm decryption
        return ROTX.decryptROT(input, x);
    }

    public static String decryptVigenere(String encryptedMessage, String key) {
        // Use the VigenereAlgo class to decrypt with the Vigenère algorithm
        return VigenereAlgo.decrypt(encryptedMessage, key);
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display the main menu
        System.out.println("Choose an option:");
        System.out.println("1. Encrypt a password");
        System.out.println("2. Decrypt a password");
        int mainChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline after an integer

        if (mainChoice == 1) {
            // Encrypt the password
            System.out.println("Enter the password to encrypt:");
            String password = scanner.nextLine();

            // Menu to choose the algorithm
            System.out.println("Choose an encryption algorithm:");
            System.out.println("1. Enigma");
            System.out.println("2. RC4");
            System.out.println("3. ROT(X)");
            System.out.println("4. Vigenère");
            int algoChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline after an integer

            String encryptedPassword = "";
            switch (algoChoice) {
                case 1:
                    encryptedPassword = encryptEnigma(password);
                    break;
                case 2:
                    encryptedPassword = encryptRC4(password);
                    break;
                case 3:
                    System.out.println("Enter the value of X for ROT(X):");
                    int x = scanner.nextInt();
                    encryptedPassword = encryptROT(password, x);
                    break;
                case 4:
                    System.out.println("Enter the key for Vigenère:");
                    String key = scanner.nextLine();
                    encryptedPassword = encryptVigenere(password, key);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;  // Exit if invalid choice
            }
            System.out.println("Encrypted password: " + encryptedPassword);
        } else if (mainChoice == 2) {
            // Decrypt the message
            System.out.println("Enter the password to decrypt:");
            String encryptedMessage = scanner.nextLine();

            // Menu to choose the decryption algorithm
            System.out.println("Choose a decryption algorithm:");
            System.out.println("1. Enigma");
            System.out.println("2. RC4");
            System.out.println("3. ROT(X)");
            System.out.println("4. Vigenère");
            int algoChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline after an integer

            String decryptedMessage = "";
            switch (algoChoice) {
                case 1:
                    decryptedMessage = decryptEnigma(encryptedMessage);
                    break;
                case 2:
                    decryptedMessage = decryptRC4(encryptedMessage);
                    break;
                case 3:
                    System.out.println("Enter the value of X for ROT(X):");
                    int x = scanner.nextInt();
                    decryptedMessage = decryptROT(encryptedMessage, x);
                    break;
                case 4:
                    System.out.println("Enter the key for Vigenère:");
                    String key = scanner.nextLine();
                    decryptedMessage = decryptVigenere(encryptedMessage, key);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;  // Exit if invalid choice
            }
            System.out.println("Decrypted password: " + decryptedMessage);
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
