package Classes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 extends Hash {

    @Override
    public String calculateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //create instance of sha256
            byte[] hashBytes = digest.digest(input.getBytes()); //hash input bytes

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); //format byte in hex string
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex); //concatenate bytes
            }
            return hexString.toString(); //return hash string

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error :"
                    , e);
        }
    }
}