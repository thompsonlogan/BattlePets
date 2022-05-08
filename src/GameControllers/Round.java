package GameControllers;
import Damage.DamageCounter;
import Pets.BattlePet;
import Pets.Playable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Round class
 * Round stores a list of pets that were alive in the turn.
 * A turn is one time though of each pet picker a skill
 */
public class Round
{
    private int numberOfRound; // Stores the total number of rounds for a fight
    private int fightNumber = 1; // the initial round number
    private List<Playable> petsInTurn; // list of the pet alive in the turn
    private DamageCounter damageCounter; // Stores cumulative damage totals for each pet

    /**
     * default constructor
     */
    public Round()
    {
        this.petsInTurn = new ArrayList<>();
        this.damageCounter = new DamageCounter(petsInTurn);
        this.numberOfRound = 0;
    }

    /**
     * constructor
     */
    public Round(DamageCounter damageCounter, int totalRounds)
    {
        this.petsInTurn = new ArrayList<>();
        this.damageCounter = damageCounter;
        this.numberOfRound = totalRounds;
    }

    /**
     * copy constructor
     */
    public Round(Round round)
    {
        this.petsInTurn = new ArrayList<>();
        this.damageCounter = new DamageCounter(round.getDamageCounter());
        for(Playable pet : round.petsInTurn)
        {
            BattlePet.BattlePetBuilder newBattlePetBuilder = new BattlePet.BattlePetBuilder();
            Playable petTemp = newBattlePetBuilder
                    .withType(pet.getPetType())
                    .withStartingHp(pet.getStartingHp())
                    .withCurrentHp(pet.getCurrentHp())
                    .withName(pet.getPetName())
                    .withPlayerName(pet.getPlayerName())
                    .withPlayerType(pet.getPlayerType())
                    .build();
            petsInTurn.add(petTemp);
        }
        this.numberOfRound = round.numberOfRound;
        this.fightNumber = round.fightNumber;
    }

    /**
     * getter for number of round
     * @return numberOfRound
     */
    public int getNumberOfRound()
    {
        return this.numberOfRound;
    }

    /**
     * @return a list of pets that played in the turn
     */
    public List<Playable> getPetsInTurn()
    {
        return this.petsInTurn;
    }

    /**
     * Adds a pet to the list of pets for the turn
     * @param pet, a battle pet
     */
    public void addPet(Playable pet)
    {
        petsInTurn.add(pet);
    }

    /**+
     * Equals method
     * @param otherObject the object to be checked for equivalence
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object otherObject)
    {
        if(this == otherObject)
            return true;
        if(!(otherObject instanceof Round))
            return false;
        Round otherRound = (Round)otherObject;

        return this.petsInTurn == otherRound.petsInTurn;
    }

    /**
     * @return the DamageCounter
     */
    public DamageCounter getDamageCounter()
    {
        return this.damageCounter;
    }

    /**
     * For memento pattern. Updates the originator for a new fight
     */
    public void newFight()
    {
        this.fightNumber++;
        this.numberOfRound = 0;
    }

    /**
     * @return the fight number for the round
     */
    public int getFightNumber()
    {
        return this.fightNumber;
    }

    /**
     * Saves the state of the rounds as a memento
     * @return the new memento object
     */
    public Memento saveState()
    {
        this.numberOfRound++;
        return new Memento(this);
    }

    /**
     * Restores the state of a round from a memento
     * @param memento of the round to be restored
     */
    public void restoreState(Memento memento)
    {
        this.petsInTurn = memento.memRound.petsInTurn;
        this.damageCounter = memento.memRound.damageCounter;
        this.numberOfRound = memento.memRound.numberOfRound;
        this.fightNumber = memento.memRound.fightNumber;
    }

    /**
     * Memento class
     */
    public static class Memento
    {
        private Round memRound; // a round in the memento

        /**
         * Memento constructor
         * @param memRound the round to be stored as a memento
         */
        public Memento(Round memRound)
        {
            this.memRound = new Round(memRound);
        }
    }
}
