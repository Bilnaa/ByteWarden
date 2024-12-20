package Tests;

import Classes.*;
import Classes.Enigma.Enigma;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabasesManagerTest {
    private DatabasesManager dbManager;
    private File testFile;
    private EncryptionStack encryptionStack;

    @Before
    public void setUp() {
        testFile = new File("src/Tests/assets/test_databases.json");
        dbManager = new DatabasesManager(testFile);
        encryptionStack = new EncryptionStack();
        encryptionStack.addAlgorithm(new RC4());
        encryptionStack.addAlgorithm(new ROTX(13));
        encryptionStack.addAlgorithm(new VigenereAlgo());
        encryptionStack.addAlgorithm(new Enigma());

        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testCreateAndVerifyDatabase() {
        String dbName = "test";
        String password = "securePassword";
        String algorithm = "RC4";

        dbManager.createDatabase(dbName, password, algorithm);
        assertTrue(dbManager.verifyDatabase(dbName, password));
    }

    @Test
    public void testDuplicateDatabaseName() {
        String dbName = "test";
        String password = "securePassword";
        String algorithm = "RC4";

        dbManager.createDatabase(dbName, password, algorithm);
        dbManager.createDatabase(dbName, password, algorithm);
        assertEquals(1, dbManager.loadDatabases().size());
    }

    @Test
    public void testLoadEmptyDatabases() {
        Map<String, DatabasesManager.DatabaseInfo> databases = dbManager.loadDatabases();
        assertTrue(databases.isEmpty());
    }
}