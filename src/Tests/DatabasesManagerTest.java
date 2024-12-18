package Tests;

import Classes.DatabasesManager;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

public class DatabasesManagerTest {
    private DatabasesManager dbManager;
    private File testFile;

    @Before
    public void setUp() {
        testFile = new File("test_databases.json");
        dbManager = new DatabasesManager(testFile);

        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testCreateAndVerifyDatabase() {
        String dbName = "testDb";
        String password = "securePassword";

        dbManager.createDatabase(dbName, password);

        assertTrue(dbManager.verifyDatabase(dbName, password));

        assertFalse(dbManager.verifyDatabase(dbName, "wrongPassword"));
    }

    @Test
    public void testDuplicateDatabaseName() {
        String dbName = "duplicateDb";
        String password = "password";

        dbManager.createDatabase(dbName, password);

        try {
            dbManager.createDatabase(dbName, password);
            fail("Expected IllegalArgumentException for duplicate database name");
        } catch (IllegalArgumentException e) {
            assertEquals("Database already exists.", e.getMessage());
        }
    }

    @Test
    public void testLoadEmptyDatabases() {
        Map<String, String> databases = dbManager.loadDatabases();
        assertTrue(databases.isEmpty());
    }
}
