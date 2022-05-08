package GameControllers;

import Domain.IOManager;
import Pets.Playable;

import java.util.ArrayList;
import java.util.List;

/**
 * Battle Class
 * Stores all information relation to a battle,
 * the pets involved in the battle, a list of winners from each fight,
 * and a number of rounds, or fights,
 */
public class Battle
{
    private int rounds; // how many fights the battle will contain
    private Caretaker caretaker; // stores the rounds as mementos for replay mode
    private final FightManager fightManager; // the fightManager to run the battle
    private final List<Playable> petsPlayed; // list of pets to be played during the battle
    private final IOManager io = IOManager.INSTANCE; // instance of the ioManager
    private final List<Playable> winners = new ArrayList<>(); // list of the winners from each fight

    /**
     * Battle constructor
     * @param petsPlayed<petsPlayed> petsPlayed, a list of all pets in the battle
     * @param rounds, number of rounds, or fights, in the battle
     */
    public Battle(List<Playable> petsPlayed, int rounds)
    {
        this.rounds = rounds;
        this.petsPlayed = petsPlayed;
        this.caretaker = new Caretaker(new Round());
        this.fightManager = new FightManager(this.petsPlayed, caretaker);
    }

    /**
     * runBattle works with the fight manager and run the requested
     * number of rounds and storing the winner from each fight
     * in the winners list
     */
    public void runBattle()
    {
        for (int currentRound = 1; rounds > 0; currentRound++)
        {
            io.displayOutput("Round " + currentRound + "!");
            addFightWinner(fightManager.runFight());
            copyCaretaker();
            fightManager.resetFight();
            caretaker.newFight();
            rounds --;
        }
    }

    /**
     * @return a Playable battle pet the won the battle
     */
    public Playable findBattleWinner()
    {
        int count = 1;
        Playable winner = winners.get(0);
        for(int i = 0; i < winners.size(); i ++)
        {
            Playable temp = winners.get(i);
            int tempCount = 0;
            for(int j = 1; j < winners.size(); j++)
            {
                if(temp == winners.get(j))
                    tempCount++;
            }
            if (tempCount > count)
            {
                winner = temp;
                count = tempCount;
            }
        }
        return winner;
    }

    /**
     * addFightWinner adds the winner of a fight to the list of
     * winner for the battle
     * @param pet, the BattlePet
     */
    public void addFightWinner(Playable pet)
    {
        winners.add(pet);
    }

    /**
     * @return a list of all pets that won a fight in the battle
     */
    public List<Playable> getWinners()
    {
        return this.winners;
    }

    /**
     * @return the number of rounds in the fight
     */
    public int getRounds()
    {
        return this.rounds;
    }

    /**
     * equals override for battle
     * @param otherObject otherObject
     * @return the boolean value if the parameter passed is the same as the object calling the equals method
     */
    @Override
    public boolean equals(Object otherObject)
    {
        if (this == otherObject)
        {
            return true;
        }
        if(!(otherObject instanceof Battle))
        {
            return false;
        }

        Battle otherBattle = (Battle) otherObject;

        return this.rounds == otherBattle.rounds
                && this.petsPlayed == otherBattle.petsPlayed;
    }

    /**
     * @return the caretaker
     */
    public Caretaker getCaretaker()
    {
        return this.caretaker;
    }

    /**
     * Copies the state of the caretaker from the fight manager after each fight
     */
    public void copyCaretaker()
    {
        this.caretaker = fightManager.getCaretaker();
    }
}
