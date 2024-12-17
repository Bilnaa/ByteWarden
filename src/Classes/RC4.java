package Classes;

public class RC4 {

    private int[] S = new int[256];
    private int[] T = new int[256];
    public String key = "";

    // Initialisation du tableau S et du tableau T avec la clé
    public void init(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("La clé ne peut pas être nulle ou vide");
        }
        this.key = key;

        // Initialisation des tableaux S et T
        for (int i = 0; i < 256; i++) {
            S[i] = i;
            T[i] = key.charAt(i % key.length());
        }

        // Permutation initiale de S
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;
            swap(i, j);
        }
    }

    // Chiffrement du texte clair
    public String encrypt(String clearText) {
        if (clearText == null || clearText.isEmpty()) {
            throw new IllegalArgumentException("Le texte à chiffrer ne peut pas être nul ou vide");
        }

        // Réinitialisation pour assurer un état propre
        init(this.key);

        StringBuilder encrypted = new StringBuilder();
        int i = 0, j = 0;

        // Pour chaque caractère du texte clair
        for (int n = 0; n < clearText.length(); n++) {
            char c = clearText.charAt(n);
            // On incrémente i modulo 256
            i = (i + 1) & 0xFF;
            // On incrémente j modulo 256
            j = (j + S[i]) & 0xFF;
            // On échange S[i] et S[j]
            swap(i, j);
            // On calcule t
            int t = (S[i] + S[j]) & 0xFF;
            // On récupère la valeur de S[t]
            int k = S[t];
            // On chiffre le caractère
            int encryptedByte = c ^ k;

            // Conversion en binaire avec padding de 0
            encrypted.append(String.format("%8s",
                            Integer.toBinaryString(encryptedByte & 0xFF))
                    .replace(' ', '0'));

            // Ajout d'espace sauf pour le dernier caractère
            if (n < clearText.length() - 1) {
                encrypted.append(" ");
            }
        }

        return encrypted.toString();
    }

    // Déchiffrement du texte chiffré
    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            throw new IllegalArgumentException("Le texte chiffré ne peut pas être nul ou vide");
        }

        // Réinitialisation pour assurer un état propre
        init(this.key);

        StringBuilder decrypted = new StringBuilder();
        String[] binaryStrings = encryptedText.split(" ");
        int i = 0, j = 0;

        // Pour chaque octet du texte chiffré
        for (String binaryString : binaryStrings) {
            char c = (char) Integer.parseInt(binaryString, 2);
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            swap(i, j);
            int t = (S[i] + S[j]) & 0xFF;
            int k = S[t];
            decrypted.append((char) (c ^ k));
        }

        return decrypted.toString();
    }

    // Fonction d'échange de deux éléments dans le tableau S
    private void swap(int i, int j) {
        int temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }
}