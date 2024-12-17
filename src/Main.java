import Classes.Lfsr;

public class Main {
    public static void main(String args[]) {
        String tapsHex = "8005";
        int loops = 10;
        String seedHex = "1";
        String mode = "galois";
        Lfsr lfsr = new Lfsr((char) 0, (char) 0);
        String result = lfsr.lfsr(tapsHex, loops, seedHex, mode);
        System.out.println(result);
    }
}