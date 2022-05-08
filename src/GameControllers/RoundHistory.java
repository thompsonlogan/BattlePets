package GameControllers;
import java.util.ArrayList;
import java.util.List;

/**
 * RoundHistory class
 * RoundHistory store a list of rounds for a given fight of battle
 */
public class RoundHistory
{
    List<Round> rounds; // a list of past rounds

    /**
     * constructor
     */
    public RoundHistory()
    {
        this.rounds = new ArrayList<>();
    }

    /**
     * Adds a round to the round history
     * @param round each loop through each pet selecting their skill
     */
    public void addRound(Round round)
    {
        rounds.add(round);
    }

    /**
     * @return a list of the pets that played in the most recent turn
     */
    public Round getLastRound()
    {
        return rounds.get(rounds.size() - 1);
    }
}
