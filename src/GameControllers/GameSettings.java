package GameControllers;
import Pets.Playable;
import java.util.List;

/**
 * Game Settings class
 * Contains constant for random number generator
 * Contains variables to hold the number of fights for a game and the random seed generated
 */
public class GameSettings
{
    private final SeedGenerator seed; // the seed object for the game
    private final int numberOfFights; // The number of fights set for a game

    /**
     * Constructor
     * @params int numberOfFights
     * sets parameter passed (int) to this object's number of fights variable
     */
    public GameSettings(int numberOfFights, long seedNumber)
    {
        this.numberOfFights = numberOfFights;
        this.seed = SeedGenerator.INSTANCE;
        seed.setSeed(seedNumber);
    }

    /**
     * Get method for number of fights
     * returns number of fights
     */
    public int getNumberOfFights()
    {
        return numberOfFights;
    }

    /**
     * Get method for seed
     * returns the seed as an non rounded double
     */
    public SeedGenerator getSeed()
    {
        return seed;
    }

    /**
     * Create game based on game settings
     * @return Returns new BattleManager object
     */
    public BattleManager createGame(List<Playable> battlePets)
    {
        return new BattleManager(battlePets);
    }
}
