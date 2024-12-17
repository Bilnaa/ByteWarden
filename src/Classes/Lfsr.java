package Classes;

public class Lfsr {          
    char state; 
    char taps; 

    public Lfsr(char state, char taps) {
        this.state = state; //représente l'état actuel du LFSR sous forme d'un entier (char sur 16 bits)
        this.taps = taps; //mask indiquant les positions (bits) pour la rétroactio
    }
    //méthode calculant la parité d'un nombre (nombre de bits à 1) pour déterminer le bit de rétroaction
    static int parity(int mask) {
        int p = 0;
        for ( ; mask > 0 ; mask >>>= 1)
            p ^= mask & 1;
        return p;
    }
    //méthode convertissant un entier en chaîne convertoraire de 16 bits.
    static String convertor(int registers) {
        return String.format("%16s",
                             Integer.toBinaryString(registers)).replace(" ", "0"); //affiche un chaine de 16bits en laissant le bit de point fort s'il est égale à 0
    }

    public String fibonacci(int loops) {
        String alea = ""; 
        for (int i = 0 ; i < loops ; i++) {
            System.out.println("Debug info: state = " + convertor(this.state));
            int bit = Lfsr.parity(this.state & this.taps); //calcul le mask à partir du XOR 
            System.out.println("bit = " + bit);
            alea += this.state & 1; // recupère le bit de poids faible 
            this.state >>= 1; //decalage des bits sur la droite 
            this.state |= bit << 15; //remet le bit de poids faible 15 positions à gauche (dernier bit)
        }
        return alea;
    }

    public String galois(int loops) {
        String alea = "";
        for (int i = 0 ; i < loops ; i++) {
            System.out.println("Debug info: state = " + convertor(this.state));
            int bit = this.state & 1; //récuère le bit de poids faible
            alea += bit;
            this.state >>= 1; //décalage des bits sur la droite
            if (bit == 1) //si le bit de poids faible est 1 on modifie le l'état par la porte Xor
                this.state ^= this.taps;
        }
        return alea;
    }
 
 
    /***
    * 
    *
    * @param tapsHex une valeur indiquant la position du bit en hexa
    * @param loops nombre de boucle pour les methodes galois et fibonacci
    * @param seedHex seed pour l'algorythme
    * @param mode choix entre les deux méthodes galois et fibonacci
    * @return un resultat (alea) en 16 bits
    */  
    public String lfsr(String tapsHex, int loops, String seedHex, String mode) {
        char taps = (char) Integer.parseInt(tapsHex, 16);
        char seed = (char) Integer.parseInt(seedHex, 16);
    
        this.state = seed;
        this.taps = taps;

        String result;
        if (mode.equalsIgnoreCase("galois")) { //Execute la méthode en fonctionne du choix de fonction de transformation
            result = this.galois(loops); 
        } else { 
            result = this.fibonacci(loops);
        }
    
        return result;
    }
    
}

