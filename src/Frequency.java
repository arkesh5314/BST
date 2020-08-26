import java.util.Comparator;

/**
 * @author : Arkesh Rath
 * Custom comparator class to compare 2 words.
 */
public class Frequency implements Comparator<Word> {

    /**
     * Compare 2 words based on their frequencies.
     *
     * @param o1 : Word 1.
     * @param o2 : Word 2.
     * @return : Difference in values between both objects.
     */
    @Override
    public int compare(Word o1, Word o2) {
        return o2.getFrequency() - o1.getFrequency();
    }
}
