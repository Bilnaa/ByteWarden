package Classes;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * The succession of numbers (XY) recovered according to the characters of the plain text to encode.
     */
    private final List<String> polybCodeResults = new ArrayList<>();
    // TODO need to clear this array after sending to the rest of the system;

    // Constructor.
    public PolybSquareEncrypter(PolybSquareLayout layoutChoice) {
        this.LayoutChoice = layoutChoice;
        this.PolybSquareTable = new PolybSquareLayoutCollection().getPolybSquareLayout(layoutChoice);
    }


    /**
     * Get all two-digits numbers corresponding to each character of the text to encrypt.
     * @param plainText The text to encrypt.
     */
    public final void encrypt(String plainText) {

        for (int i = 0;i < plainText.length(); i++){
            // Polyb table contain 25 value. In consequence, we decide to interpret w as two v character.
            if (plainText.charAt(i) == 'w'){
                polybCodeResults.add(findPolybSquareCode('v'));
                polybCodeResults.add(findPolybSquareCode('v'));
            }
            else if (Character.isLetter(plainText.charAt(i))) // must be a letter
            {
                String code = findPolybSquareCode(plainText.charAt(i));
                polybCodeResults.add(code);
            }
            else // other character such as number, special, space character are ignored.
            {
                break;
            }
        }

        // End of encryption.
        System.out.println(polybCodeResults);
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

    public final String decrypt(String encryptedText)
    {
        // local
        StringBuilder decryptedCharacters = new StringBuilder("");

        for (int i = 0;i < encryptedText.length(); i+=2) {
            char firstChar = encryptedText.charAt(i);
            char secondChar = encryptedText.charAt(i+1);

            decryptedCharacters.append(getCharacterFromPolybSquareCode(firstChar, secondChar));
        }

        return decryptedCharacters.toString();
    }

    /**
     * @param firstChar X
     * @param secondChar Y
     * @return code
     */
    private final char getCharacterFromPolybSquareCode(char firstChar, char secondChar) {
        //System.out.println(firstChar + " " + secondChar);
        int x = Character.getNumericValue(firstChar);
        int y = Character.getNumericValue(secondChar);
        // table are manipulated Y X
        // - 1 beccause table index start at 0 and not 1.
        return PolybSquareTable[y - 1][x - 1];
    }


















}
