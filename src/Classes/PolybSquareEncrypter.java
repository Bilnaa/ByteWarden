package Classes;

/**
 * Class used to encrypt and decrypt string of text using polyb square algorithm.
 * @remarks String must be only composed of letters.
 * @remarks All space character will not be taken into account.
 */
public class PolybSquareEncrypter {

    // Fields.
    /**
     * The layout choose by the user for the polyb table encrypter.
     */
    private final PolybSquareLayout LayoutChoice;

    /**
     * The current polybTable used for this encrypter.
     */
    private final char[][] PolybSquareTable;

    // Constructor.
    public PolybSquareEncrypter(PolybSquareLayout layoutChoice) {
        this.LayoutChoice = layoutChoice;
        this.PolybSquareTable = new PolybSquareLayoutCollection().getPolybSquareLayout(layoutChoice);
    }

    // Functions.
    /**
     * Get all two-digits numbers corresponding to each character of the text to encrypt.
     * @param plainText The text to encrypt.
     */
    public final String encrypt(String plainText) {
        // The succession of numbers (XY) recovered according to the characters of the plain text to encode.
        StringBuilder encryptedCharacters = new StringBuilder("");

        for (int i = 0;i < plainText.length(); i++){
            // Polyb table contain 25 value. In consequence, we decide to interpret w as two v character.
            if (plainText.charAt(i) == 'w'){
                encryptedCharacters.append(findPolybSquareCode('v'));
                encryptedCharacters.append(findPolybSquareCode('v'));
            }
            else if (Character.isLetter(plainText.charAt(i))) // must be a letter
            {
                String code = findPolybSquareCode(plainText.charAt(i));
                encryptedCharacters.append(code);
            }
            else // other character such as number, special, space character are ignored.
            {
                break;
            }
        }

        // End of encryption.
        return encryptedCharacters.toString();
    }

    /** Get the number corresponding to the coordinate of the character in the current polyb table.
     * @param characterToFind the character to find in polyb table.
     * @return a two digits number.
     * @remarks First digit is the X coordinate in the polyb table, second is Y.
     */
    private final String findPolybSquareCode(char characterToFind) {
        // for each col -> ordinate Y
        for (int i = 0; i < PolybSquareTable.length; i++) {
            // for each row -> abscyssa X
            for (int j = 0; j < PolybSquareTable[i].length; j++) {
                // if the character at this position in the table match, get his code
                if (PolybSquareTable[i][j] == characterToFind) {
                    // XY
                    // Add one to col and row value in order to start to 1 and not 0.
                    return String.valueOf(j + 1) + String.valueOf(i + 1);
                }
            }
        }
        // If no match is found, instead of throwing error, we use a blank character.
        System.out.println("Unrecognized character");
        return null;
        //return "__";
    }

    /**
     * Get all plain text string from text encrypted with PolybSquareMethod.
     * @param encryptedText An encrypted string only composed of numbers.
     * @return A plain text string.
     * @remarks the succession of numbers of the encrypted text is a succession of two-digits numbers (XXYYZZ...)
     */
    public final String decrypt(String encryptedText)
    {
        StringBuilder decryptedCharacters = new StringBuilder("");

        // Get every pair of two-digits numbers composing the encrypted string.
        for (int i = 0;i < encryptedText.length(); i+=2) {
            char firstChar = encryptedText.charAt(i);
            char secondChar = encryptedText.charAt(i+1);

            // Get the latin alphabet letter corresponding to this two-digits number.
            decryptedCharacters.append(getCharacterFromPolybSquareCode(firstChar, secondChar));
        }

        // End of decryption.
        return decryptedCharacters.toString();
    }

    /**
     * Get one latin alphabet character using two number as coordinate in the polyb square table.
     * @param firstChar first number, used as abscyssa X
     * @param secondChar second number used as ordinate, Y
     * @return One latin alphabet character.
     */
    private final char getCharacterFromPolybSquareCode(char firstChar, char secondChar) {
        // cast the char received into int values
        int x = Character.getNumericValue(firstChar);
        int y = Character.getNumericValue(secondChar);
        // two-dimensional array are manipulated such as 2dArray[Y][X]
        // - 1 beccause table index start at 0 and not 1.
        return PolybSquareTable[y - 1][x - 1];
    }


















}
