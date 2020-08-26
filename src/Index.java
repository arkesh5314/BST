import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author : Arkesh Rath
 * <p>
 * Class which helps in building an index BST based on a text file.
 */
public class Index {

    /**
     * Builds an index tree, in string's natural order.
     *
     * @param fileName : File whose index tree has to be built.
     * @return : An indexed BST.
     */
    public BST<Word> buildIndex(String fileName) {
        File file = new File(fileName);
        if (file == null || file.length() == 0) {
            return null;
        }

        BST<Word> indexedBST = new BST<>();
        int currentLine = 1;
        Scanner scanner = null;

        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {
                    if (isWord(word)) {
                        Word currentWord = new Word(word);
                        Word existingWord = indexedBST.search(currentWord);
                        if (existingWord != null) {
                            existingWord.setFrequency(existingWord.getFrequency() + 1);
                            existingWord.addToIndex(currentLine);
                            indexedBST.insert(existingWord);
                        } else {
                            currentWord.addToIndex(currentLine);
                            indexedBST.insert(currentWord);
                        }
                    }
                }
                currentLine++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return indexedBST;
    }

    /**
     * Builds an indexed BST based on order defined by the passed comparator.
     *
     * @param fileName   : File whose index BST has to be built.
     * @param comparator : Comparator to be used to compare 2 different words.
     * @return : An indexed BST.
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        File file = new File(fileName);

        if (file == null || file.length() == 0) {
            return null;
        }

        BST<Word> indexedBST = new BST<>(comparator);
        boolean lowerCase = comparator instanceof IgnoreCase;
        int currentLine = 1;

        Scanner scanner = null;

        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {
                    if (isWord(word)) {
                        Word currentWord = new Word(word);

                        if (lowerCase) {
                            currentWord.setWord(currentWord.getWord().toLowerCase());
                        }

                        currentWord.addToIndex(currentLine);
                        Word existingWord = indexedBST.search(currentWord);

                        if (existingWord != null) {
                            existingWord.setFrequency(existingWord.getFrequency() + 1);
                            existingWord.addToIndex(currentLine);
                            indexedBST.insert(existingWord);
                        } else {
                            currentWord.addToIndex(currentLine);
                            indexedBST.insert(currentWord);
                        }
                    }
                }
                currentLine++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return indexedBST;
    }

    /**
     * Builds an indexed BST given a list of word objects, order of whom is defined by the comparator.
     *
     * @param list       : List of words whose indexed BST has to be built.
     * @param comparator : Comparator to be used to compare 2 different words.
     * @return : An indexed BST.
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        BST<Word> indexedBST = new BST<>(comparator);

        for (Word word : list) {
            indexedBST.insert(word);
        }

        return indexedBST;
    }

    /**
     * Returns a sorted list of words from the BST by using the alpha frequency comparator.
     *
     * @param tree : Indexed BST to be sorted.
     * @return : List of word objects sorted by the alpha frequency comparator.
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        /*
         * Even though there should be no ties with regard to words in BST,
         * in the spirit of using what you wrote,
         * use AlphaFreq comparator in this method.
         */
        if (tree == null) {
            return null;
        }

        ArrayList<Word> sortedAlphaList = new ArrayList<>();
        Iterator<Word> bstIterator = tree.iterator();

        while (bstIterator.hasNext()) {
            sortedAlphaList.add(bstIterator.next());
        }

        Collections.sort(sortedAlphaList, new AlphaFreq());

        return sortedAlphaList;
    }

    /**
     * Returns a sorted list of words from the BST by using the frequency comparator.
     *
     * @param tree : Indexed BST to be sorted.
     * @return : List of word objects sorted by the frequency comparator.
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {

        if (tree == null) {
            return null;
        }

        ArrayList<Word> sortedFrequencyList = new ArrayList<>();
        Iterator<Word> bstIterator = tree.iterator();

        while (bstIterator.hasNext()) {
            sortedFrequencyList.add(bstIterator.next());
        }

        Collections.sort(sortedFrequencyList, new Frequency());

        return sortedFrequencyList;
    }

    /**
     * Private helper method to find maximum frequency value in an array list.
     *
     * @param words : List of word objects.
     * @return : Value of maximum frequency.
     */
    private int getMaxFrequency(ArrayList<Word> words) {
        int maxFrequency = Integer.MIN_VALUE;
        for (Word word : words) {
            if (word.getFrequency() > maxFrequency) {
                maxFrequency = word.getFrequency();
            }
        }
        return maxFrequency;
    }

    /**
     * Returns a list of word objects who have the highest frequency in the indexed BST.
     *
     * @param tree : An indexed BST.
     * @return : List of word objects with highest frequency.
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {

        if (tree == null) {
            return null;
        }

        ArrayList<Word> sortedFrequencyList = sortByFrequency(tree);
        int maxFrequency = getMaxFrequency(sortedFrequencyList);

        ArrayList<Word> highestFrequencyList = new ArrayList<>();

        for (Word word : sortedFrequencyList) {
            if (word.getFrequency() == maxFrequency) {
                highestFrequencyList.add(word);
            } else {
                break;
            }
        }
        return highestFrequencyList;

    }

    /**
     * Checks if the text is a valid word.
     *
     * @param text : String to be validated.
     * @return : true if text is valid, else false.
     */
    private boolean isWord(String text) {
        // Error Checking.
        if (text == null) {
            return false;
        }
        return text.matches("[a-zA-Z]+");
    }

}
