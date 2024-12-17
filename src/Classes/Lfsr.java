package Classes;

public class Lfsr {          
    char state; 
    char taps; 

    public Lfsr(char state, char taps) {
        this.state = state; //represents the current state of the LFSR as a 16-bit integer (char)
        this.taps = taps; //mask indicating the positions (bits) for feedback
    }
    //method calculating the parity of a number (number of bits set to 1) to determine the feedback bit
    public static int parity(int mask) {
        int p = 0;
        for ( ; mask > 0 ; mask >>>= 1)
            p ^= mask & 1;
        return p;
    }
    //method converting an integer to a 16-bit binary string
    public static String convertor(int registers) {
        return String.format("%16s",
                             Integer.toBinaryString(registers)).replace(" ", "0"); //displays a 16-bit string, keeping the leading zero if it is 0
    }

    public String fibonacci(int loops) {
        String alea = ""; 
        for (int i = 0 ; i < loops ; i++) {
            System.out.println("Debug info: state = " + convertor(this.state));
            int bit = Lfsr.parity(this.state & this.taps); //calculates the mask from the XOR
            System.out.println("bit = " + bit);
            alea += this.state & 1; // retrieves the least significant bit
            this.state >>= 1; //shifts the bits to the right
            this.state |= bit << 15; //sets the least significant bit 15 positions to the left (last bit)
        }
        return alea;
    }

    public String galois(int loops) {
        String alea = "";
        for (int i = 0 ; i < loops ; i++) {
            System.out.println("Debug info: state = " + convertor(this.state));
            int bit = this.state & 1; //retrieves the least significant bit
            alea += bit;
            this.state >>= 1; //shifts the bits to the right
            if (bit == 1) //if the least significant bit is 1, modifies the state with the XOR gate
                this.state ^= this.taps;
        }
        return alea;
    }
 
 
    /***
    * 
    *
    * @param tapsHex a value indicating the bit position in hex
    * @param loops number of loops for the galois and fibonacci methods
    * @param seedHex seed for the algorithm
    * @param mode choice between the two methods galois and fibonacci
    * @return a result (alea) in 16 bits
    */  
    public String lfsr(String tapsHex, int loops, String seedHex, String mode) {
        char taps = (char) Integer.parseInt(tapsHex, 16);
        char seed = (char) Integer.parseInt(seedHex, 16);
    
        this.state = seed;
        this.taps = taps;

        String result;
        if (mode.equalsIgnoreCase("galois")) { //Executes the method based on the chosen transformation function
            result = this.galois(loops); 
        } else { 
            result = this.fibonacci(loops);
        }
    
        return result;
    }
    
}
