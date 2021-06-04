import java.util.Random;

/**
 * An extension of Random to handle flawed randomness
 */
public class Bias extends Random {
    public Bias() {}

    /**
     * @param biasIntensity intensity of the bias (higher -> more biased)
     * @return true if biased choice was selected, false otherwise
     */
    public boolean biasedChoice(int biasIntensity) {
        return new Random().nextInt(Math.abs(biasIntensity) + 1) != 0;
    }

    /**
     * @param preference preferred Object
     * @param alternative alternative, unlikely Object
     * @param biasIntensity intensity of the bias to prefer preference with
     * @return selected Object
     */
    public Object biasedChoice(Object preference, Object alternative, int biasIntensity) {
        return biasedChoice(biasIntensity) ? preference : alternative;
    }
}
