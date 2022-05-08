package GameControllers;
import Domain.IOManager;
import Pets.Playable;
import java.util.List;

/**
 * Fight class
 * This class stores all information relating to a fight,
 * the pets involved in the fight
 */
public class Fight
{

    private final int FIRST_PET = 0; // constant value for the index of the first pet
    private final List<Playable> pets; //list of the pets
    private final IOManager io = IOManager.INSTANCE; // instance of the IOManager

    /**
     * Fight constructor
     * @param pets<Playable> pets
     * sets the list of pets for the fight
     */
    public Fight(List<Playable> pets)
    {
        this.pets = pets;
    }

    /**
     * findFightWinner fights the winning pet of the battle
     * @return a playable pet object with the most hp, or last pet to fall asleep
     */
    public Playable findFightWinner(List<Playable> petsInLastRound)
    {
        Playable winner = petsInLastRound.get(FIRST_PET);
        for (Playable playable : petsInLastRound)
        {
            if (playable.getCurrentHp() > winner.getCurrentHp())
            {
                winner = playable;
            }
            winner.reset();
        }
        io.displayOutput("The fight winner is: " + winner.getPetName()+"\n");
        return winner; // in the event of a tie, the winner is picked based on order in the list
    }
}
