package Classes;

import Classes.Enigma.Enigma; // Import the Enigma class for encryption and decryption


public class AlgorithmChoice {

    ////////////// ENCRYPTION ///////////
    // Methods for each encryption algorithm

    // Encrypts using the Enigma encryption algorithm
    public static String encryptEnigma(String password) {
        Enigma encryptionBox = new Enigma(); // Create an instance of the Enigma encryption box
        // Calls the Enigma's encrypt method to encrypt the password
        return encryptionBox.encrypt(password);
    }

    // Encrypts using the RC4 algorithm
    public static String encryptRC4(String password) {
        RC4 encryptionBox = new RC4(); // Create an instance of the RC4 encryption box
        String key = "key"; // The encryption key for RC4
        encryptionBox.init(key); // Initialize the RC4 encryption with the key
        // Calls the RC4's encrypt method to encrypt the password
        return encryptionBox.encrypt(password);
    }

    // Encrypts using the ROT(x) algorithm
    public static String encryptROT(String password, int x) {
        // Calls the ROTX's encrypt method to perform a ROT(x) shift on the password
        return ROTX.encryptROT(password, x);
    }

    // Encrypts using the Vigenère cipher algorithm
    public static String encryptVigenere(String password, String key) {
        // Uses the VigenereAlgo class to encrypt the password with the given key
        return VigenereAlgo.encrypt(password, key);
    }

    /////////// DECRYPTION ///////////
    // Methods for each decryption algorithm

    // Decrypts using the Enigma decryption algorithm
    public static String decryptEnigma(String encryptedMessage) {
        Enigma decryptionBox = new Enigma(); // Create an instance of the Enigma decryption box
        // Calls the Enigma's decrypt method to decrypt the encrypted message
        return decryptionBox.decrypt(encryptedMessage);
    }

    // Decrypts using the RC4 algorithm
    public static String decryptRC4(String encryptedMessage) {
        // Create an instance of the RC4 decryption box
        RC4 decryptionBox = new RC4();
        String key = "key"; // The decryption key for RC4
        decryptionBox.init(key); // Initialize the RC4 decryption with the key
        // Calls the RC4's decrypt method to decrypt the encrypted message
        return decryptionBox.decrypt(encryptedMessage);
    }

    // Decrypts using the ROT(x) algorithm
    public static String decryptROT(String input, int x) {
        // Calls the ROTX's decrypt method to reverse the ROT(x) shift on the input
        return ROTX.decryptROT(input, x);
    }

    // Decrypts using the Vigenère cipher algorithm
    public static String decryptVigenere(String encryptedMessage, String key) {
        // Uses the VigenereAlgo class to decrypt the encrypted message with the given key
        return VigenereAlgo.decrypt(encryptedMessage, key);
    }
}
