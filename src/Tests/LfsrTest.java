package Tests;

import Classes.Lfsr;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LfsrTest {
    private Lfsr lfsr;

    @Before
    public void setUp() {
        lfsr = new Lfsr((char) 0, (char) 0);
    }

    @Test
    public void testParity() {
        // Test the parity (XOR of all bits)
        assertEquals(0, Lfsr.parity(0));      // 0000 -> parity 0
        assertEquals(1, Lfsr.parity(1));      // 0001 -> parity 1
        assertEquals(0, Lfsr.parity(3));      // 0011 -> parity 0 (1 XOR 1 = 0)
        assertEquals(1, Lfsr.parity(7));      // 0111 -> parity 1 (1 XOR 1 XOR 1 = 1)
        assertEquals(0, Lfsr.parity(15));     // 1111 -> parity 0 (1 XOR 1 XOR 1 XOR 1 = 0)
    }

    @Test
    public void testConvertor() {
        assertEquals("0000000000000000", Lfsr.convertor(0));
        assertEquals("0000000000000001", Lfsr.convertor(1));
        assertEquals("0000000000001111", Lfsr.convertor(15));
    }

    @Test
    public void testFibonacciBasic() {
        lfsr = new Lfsr((char) 1, (char) 1);  // Simple initial state
        String result = lfsr.fibonacci(4);
        assertNotNull(result);
        assertEquals(4, result.length());
    }

    @Test
    public void testGaloisBasic() {
        lfsr = new Lfsr((char) 1, (char) 1);  // Simple initial state
        String result = lfsr.galois(4);
        assertNotNull(result);
        assertEquals(4, result.length());
    }

    @Test
    public void testLfsrBasicOperation() {
        // Test with simple values
        String result = lfsr.lfsr("1", 4, "1", "galois");
        assertNotNull(result);
        assertEquals(4, result.length());
    }

    @Test
    public void testDifferentModes() {
        String galoisResult = lfsr.lfsr("1", 4, "1", "galois");
        String fibonacciResult = lfsr.lfsr("1", 4, "1", "fibonacci");
        // The results should be different for the two modes
        assertNotEquals(galoisResult, fibonacciResult);
    }

    @Test
    public void testOutputLength() {
        String result4 = lfsr.lfsr("1", 4, "1", "galois");
        String result8 = lfsr.lfsr("1", 8, "1", "galois");
        assertEquals(4, result4.length());
        assertEquals(8, result8.length());
    }

    @Test
    public void testConsistency() {
        // The same input should give the same output
        String result1 = lfsr.lfsr("1", 4, "1", "galois");
        String result2 = lfsr.lfsr("1", 4, "1", "galois");
        assertEquals(result1, result2);
    }

    @Test
    public void testModeInsensitiveCase() {
        String result1 = lfsr.lfsr("1", 4, "1", "GALOIS");
        String result2 = lfsr.lfsr("1", 4, "1", "galois");
        assertEquals(result1, result2);
    }

    @Test
    public void testWithZeroTaps() {
        String result = lfsr.lfsr("0", 4, "1", "galois");
        assertNotNull(result);
        assertEquals(4, result.length());
    }

    @Test
    public void testWithZeroSeed() {
        String result = lfsr.lfsr("1", 4, "0", "galois");
        assertNotNull(result);
        assertEquals(4, result.length());
        assertEquals("0000", result);  // With seed 0, the output should be all 0s
    }
}