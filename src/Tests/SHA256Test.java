package Tests;

import Classes.Sha256;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SHA256Test {
    @Test
    public void testCalculateHash() {
        Sha256 sha256 = new Sha256();
        String input = "test";
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

        String result = sha256.calculateHash(input);
        assertEquals(expectedHash, result);
    }

    @Test
    public void testEmptyInputHash() {
        Sha256 sha256 = new Sha256();
        String input = "";
        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        String result = sha256.calculateHash(input);
        assertEquals(expectedHash, result);
    }
}
