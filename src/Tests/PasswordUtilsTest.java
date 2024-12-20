package Tests;

import Classes.PasswordUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordUtilsTest {

    @Test
    public void testGenerateRandomPassword() {
        String password1 = PasswordUtils.generateRandomPassword(12);
        String password2 = PasswordUtils.generateRandomPassword(12);

        assertNotEquals(password1, password2);

        assertEquals(12, password1.length());
        assertEquals(12, password2.length());
    }
}
