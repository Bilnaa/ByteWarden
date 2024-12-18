package Classes;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * The class managing access to all the possible layout for the polybSquare Encrypter
 */
public class PolybSquareLayoutCollection {

    // Fields.
    // All character of the latin alphabet are listed from a to z
    private final char[][] horizontal = {
        {'a','b','c','d','e'},
        {'f','g','h','i','j'},
        {'k','l','m','n','o'},
        {'p','q','r','s','t'},
        {'u','v','x','y','z'},
    };

    private final char[][] vertical = {
            {'a','f','k','p','u'},
            {'b','g','l','q','v'},
            {'c','h','m','r','x'},
            {'d','i','n','s','y'},
            {'e','j','o','t','z'},
    };

    private final Dictionary<PolybSquareLayout, char[][]> polybSquareTables = new Hashtable<>();
    //{{
    //    put(PolybSquareAlphabet.VERTICAL, vertical);
    //}}

    // CONSTRUCTOR.
    public PolybSquareLayoutCollection() {
        polybSquareTables.put(PolybSquareLayout.VERTICAL, vertical);
        polybSquareTables.put(PolybSquareLayout.HORIZONTAL, horizontal);
    }

    // Functions.
    public char[][] getPolybSquareLayout(PolybSquareLayout alphabet)
    {
        // TODO Add error management
        return polybSquareTables.get(alphabet);
    }
}
