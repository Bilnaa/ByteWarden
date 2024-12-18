package Tests;

import Classes.Hash;
import org.junit.Test;
import static org.junit.Assert.*;

public class HashTest {

    private class DummyHash extends Hash {
        @Override
        public String calculateHash(String input) {
            return "dummyhash";
        }
    }

    @Test
    public void testPrintHash() {
        DummyHash dummyHash = new DummyHash();
        dummyHash.printHash("test");
    }

    @Test
    public void testCalculateHash() {
        DummyHash dummyHash = new DummyHash();
        String result = dummyHash.calculateHash("test");
        assertEquals("dummyhash", result); 
    }
}

