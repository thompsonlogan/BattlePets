package Players;

import Pets.BattlePet;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Class
 * Player class hold all information for a player
 */
public class Player
{
    private int wins; // the number of wins the player has
    private String name; // the name of the player
    private PlayerTypes type; // the type of the player
    private final List<BattlePet> playerBattlePets = new ArrayList<>(); // a list of the pets the player has

    /**
     * sets the name of player
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * sets the type of player
     */
    public void setType(PlayerTypes type)
    {
        this.type = type;
    }

    /**
     * sets the number of wins for a player
     */
    public void setWins(int wins)
    {
        this.wins = wins;
    }

    /**
     * Updates the object's list of pets with a new pet
     * @params BattlePet newBattlePet
     */
    public void addBattlePet(BattlePet newBattlePet)
    {
        playerBattlePets.add(newBattlePet);
    }

    /**
     * @return the name of player
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the type of player
     */
    public PlayerTypes getType()
    {
        return type;
    }

    /**
     * @return the number of wins for a player
     */
    public int getWins()
    {
        return wins;
    }

    /**
     * @return the pet from the list of pets that a player has
     */
    public BattlePet getBattlePetFromList(int fightNumber)
    {
        return playerBattlePets.get(fightNumber);
    }

    /**
     * toString override for player
     * returns the name of the player just created concatenated with a phrase as a string
     */
    @Override
    public String toString()
    {
        return (this.name + "Has been created!");
    }
}
