import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Scrabble Scorer that takes in user-inputted words
 * compares them to an imported Scrabble dictionary
 * declares if they are valid or not, and if they are it prints out how many points the word is
 * @version January 30, 2023
 * @author 24levinson
 */
public class ScrabbleScorer {
    private String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[] scores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    private ArrayList<ArrayList<String>> dictionary;

    /**
     * Simple Constructor; initializes new dictionary
     */
    public ScrabbleScorer() {
        dictionary = new ArrayList<>();
        for(int i = 0; i < 26; i++)
            dictionary.add(new ArrayList<String>());
        buildDictionary();
    }

    /**
     * imports .txt file and forms new dictionary of an ArrayList of ArrayList's of String's in alphabetical order
     */
    public void buildDictionary()   {
        try {
            Scanner in = new Scanner(new File("SCRABBLE_WORDS (2).txt"));
            while(in.hasNext()) {
                String word = in.nextLine();
                int index = alpha.indexOf(word.substring(0,1));
                dictionary.get(index).add(word);
            }
            in.close();
            for(int i = 0; i < dictionary.size(); i++)
                Collections.sort(dictionary.get(i));
        }
        catch(Exception e)  {
            System.out.println("Error here: " + e);
        }
    }

    /**
     * Determines validity of Scrabble word
     * @param word user imputed scrabble word
     * @return true if valid, false otherwise
     */
    public boolean isValidWord(String word) {
        if(alpha.indexOf(word.substring(0,1)) >= 0)
            return (Collections.binarySearch(dictionary.get(alpha.indexOf(word.substring(0,1))), word) >= 0);
        else
            return false;
    }

    /**
     * Method that calls int[] scores to determine the Scrabble score of a word
     * @param word user-inputted word
     * @return the score of given word
     */
    public int getWordScore(String word)    {
        int score = 0;
        for(int i = 0; i < word.length(); i++)  {
            int index = alpha.indexOf(word.substring(i, i+1));
            score += scores[index];
        }
        return score;
    }

    /**
     * Main method of Scrabble Scorer, takes user input and decides if it's valid or not
     * prints the score if it is valid, or tells you if it is not
     * finally breaks if user inputs 0 or preses enter
     * @param args Command line arguments, if needed
     */
    public static void main(String[] args) {
        ScrabbleScorer app = new ScrabbleScorer();
        System.out.println("* Welcome to the Scrabble Scorer app *");
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print("Enter a word to score or 0 to quit: ");
            String word = in.nextLine().toUpperCase();
            if(word.equals("0") || word.length() == 0)    {
                break;
            }
            if(app.isValidWord(word))
                System.out.println(word + " = " + app.getWordScore(word) + " points");
            else
                System.out.println(word + " is not a valid word in the dictionary");
        }
        System.out.println("Exiting the program thanks for playing");
    }
}
