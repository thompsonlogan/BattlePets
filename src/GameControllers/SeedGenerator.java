package GameControllers;
import java.util.Random;

/**
 * SeedGenerator
 * SeedGenerator manages the creating of random seeds use for
 * calculating random damage of pet attacks
 */
public enum SeedGenerator
{
    INSTANCE;
    private final int UPPER_LIMIT = 5; // Sets the maximum a random seed to be 5 (noninclusive)
    private Random seedGenerated;

    /**
     * takes in a long number as the seed
     * @param seed a number to represent the seed
     */
    public void setSeed(long seed)
    {
        this.seedGenerated = new Random(seed);
    }

    /**
     * @return the next portion of the seed, a number between 0 and 5
     */
    public double getSeed()
    {
        return seedGenerated.nextDouble() * UPPER_LIMIT;
    }
}
