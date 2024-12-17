package Classes;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class MD5 {
    /**
     * Calcule le hash MD5 d'une chaîne de caractères.
     * @param input La chaîne à hasher
     * @return Le hash MD5 en hexadécimal (32 caractères)
     * @throws IllegalArgumentException si input est null
     * @throws RuntimeException si une erreur survient pendant le hachage
     */
    public static String hash(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        try {
            // Obtention d'une instance de MessageDigest configurée pour MD5
            // MessageDigest est une classe qui implémente l'algorithme de hachage
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Conversion de la chaîne d'entrée en tableau de bytes avec l'encodage UTF-8
            // puis calcul du hash MD5 sur ces bytes
            // Le résultat est un tableau de 16 bytes (128 bits, taille standard MD5)
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            // Création d'un StringBuilder pour construire la représentation hexadécimale
            // Il sera plus efficace qu'une simple String pour les concaténations
            StringBuilder hexString = new StringBuilder();

            // Pour chaque byte du hash MD5
            for (byte b : messageDigest) {
                // String.format("%02x", b & 0xff) fait plusieurs choses :
                // 1. b & 0xff : convertit le byte en un entier non signé (0-255)
                //    car les bytes en Java sont signés (-128 à 127)
                // 2. %02x : formate l'entier en hexadécimal sur 2 positions
                //    - % indique le début du format
                //    - 0 indique de remplir avec des zéros
                //    - 2 indique la largeur minimale
                //    - x indique la notation hexadécimale en minuscules
                hexString.append(String.format("%02x", b & 0xff));
            }

            // Conversion finale du StringBuilder en String
            // Le résultat sera une chaîne de 32 caractères hexadécimaux
            return hexString.toString();

        } catch (Exception e) {
            // En cas d'erreur (très rare car MD5 est un algorithme standard)
            // on encapsule l'exception dans une RuntimeException
            throw new RuntimeException("Error computing MD5 hash", e);
        }
    }
}