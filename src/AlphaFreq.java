import java.util.Comparator;

/**
 * @author : Arkesh Rath
 * Custom comparator class to compare 2 words.
 */
public class AlphaFreq implements Comparator<Word> {

    /**
     * Compare 2 words based on their string value.
     * If they are equal, compare their frequencies.
     *
     * @param o1 : Word 1.
     * @param o2 : Word 2.
     * @return : Difference in values between both objects.
     */
    @Override
    public int compare(Word o1, Word o2) {
        int alphaVal = o1.getWord().compareTo(o2.getWord());

        if (alphaVal == 0) {
            return o1.getFrequency() - o2.getFrequency();
        }

        return alphaVal;
    }
}
