package GameControllers;

import Pets.Playable;

import java.util.List;

/**
 * BattleManager class
 * Manages a number of battle by executing each battle sequentially.
 */
public class BattleManager
{
    private Battle battle; // the battle object
    private final List<Playable> petsPlayed; // list of the pets to be in the battle

    /**
     * BattleManager constructor
     * @param petsPlayed petsPlayed
     * Takes in a list of Playable battle pets that want to battle
     */
    public BattleManager(List<Playable> petsPlayed)
    {
        this.petsPlayed = petsPlayed;
    }

    /**
     * createBattle creates a new battle with a specified number rounds, and pets,
     * from the user
     * @return a battle, which consists of fights between the battle pets
     */
    public Battle createBattle(int rounds)
    {
        this.battle = new Battle(petsPlayed, rounds);
        return this.battle;
    }
}
