import java.util.HashSet;
import java.util.Set;

/**
 * @author : Arkesh Rath
 */

/**
 * Word class holding value, frequency and line numbers that the word appears in.
 */
public class Word implements Comparable<Word> {

    /**
     * Value of the word.
     */
    private String word;
    /**
     * Set of line numbers, the word appears on.
     */
    private Set<Integer> index;
    /**
     * Frequency of current word.
     */
    private int frequency;

    public Word(String currentWord) {
        setWord(currentWord);
        setFrequency(1);
        index = new HashSet<>();
    }

    /**
     * Returns the current string representation of the word.
     *
     * @return : String value of word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Set value of word.
     *
     * @param currentWord : String to be set as word.
     */
    public void setWord(String currentWord) {
        this.word = currentWord;
    }

    /**
     * Returns frequency of the current word.
     *
     * @return : An integer representing frequency.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Set frequency of given word.
     *
     * @param currentFrequency : Frequency to be set.
     */
    public void setFrequency(int currentFrequency) {
        this.frequency = currentFrequency;
    }

    /**
     * Adds a line number to the word's index.
     *
     * @param lineNumber : Line Number to be added.
     */
    public void addToIndex(Integer lineNumber) {
        index.add(Integer.valueOf(lineNumber));
    }

    /**
     * Returns set of line numbers for given word.
     *
     * @return : Set of line numbers.
     */
    public Set<Integer> getIndex() {
        return index;
    }


    /**
     * Custom comparator for the class Word.
     *
     * @param o : Object with which current object is compared.
     * @return : Difference in values between both objects.
     */
    @Override
    public int compareTo(Word o) {
        return this.getWord().compareTo(o.getWord());
    }

    /**
     * Return string representation of current word object.
     *
     * @return : String representation.
     */
    @Override
    public String toString() {
        return word + " " + getFrequency() + " " + getIndex();
    }

}
