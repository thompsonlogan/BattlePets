package GameControllers;

import Damage.*;
import Domain.IOManager;
import Pets.Playable;
import Skills.Skills;
import java.util.List;

/**
 * FightManager class
 * manager a number of fights, rounds, by executing each fight sequentially.
 * Manages damage and application of damage to battle pets, as well as
 * retrieving player input from the IOManager for players to pick their skills.
 */
public class FightManager
{
    private final int FIRST_ROUND = 0; // constant value to check the round number
    private final int ONE_PET_AWAKE = 1; // value to determine if there is only one pet awake
    private final double PET_IS_NOT_SLEEPING = 0; // value to to see if the a pet is sleeping

    private Round round; // Keeps track of everything that happens each round
    private Damage damage; // A damage object to calculate and apply damage to pets
    private final List<Playable> pets; // The list of pets fighting
    private Caretaker caretaker; // stores a list of mementos. Based on the rounds.
    private int turnIndex; //Stores turns for pets
    private final RoundHistory history; // Save a list of pets that played in each turn
    private final DamageManager damageManager; // Gives FightManager a reference to SKillManager
    private final IOManager io = IOManager.INSTANCE; // Gives FightManager a reference to IOManager

    /**
     * FightManager constructor
     * @param pets<Playable> pets
     * Sets values for the pet list
     */
    public FightManager(List<Playable> pets, Caretaker caretaker)
    {
        this.pets = pets;
        this.history = new RoundHistory();
        this.damageManager = new DamageManager(pets);
        this.turnIndex = 0;
        this.caretaker = caretaker;
    }

    /**
     * runFight loops continuously until a pet falls asleep.
     * Each turn every player picks a skill, the damage is calculate,
     * then applied to the opponent pet until someone fall asleep.
     * @return a Playable battle pet, the winner of the fight
     */
    public Playable runFight()
    {
        Fight fight = new Fight(pets); // A fight object to execute fights
        boolean isAWinner = false;

        while(!isAWinner) // until a pet falls asleep
        {
            skillSelection();// choose a skill
            calculateDamage(); // calculate damage
            decreaseRechargeTimes(); // decrement recharge times
            updateCoolDowns(); // set recharge times
            applyDamage(history.getLastRound()); // apply damage
            if(turnIndex == FIRST_ROUND)
            {
                caretaker = new Caretaker(round);
            }
            caretaker.saveMemento();
            displayGameState();
            isAWinner = isWinner();
            io.displayOutput("\n");
            turnIndex++;
        }
        return fight.findFightWinner(history.getLastRound().getPetsInTurn());
    }

    /**
     * SkillSelection iterates through each pet and allows them to pick
     * what skill they want to use for their turn
     */
    public void skillSelection()
    {
        for (Playable playable : pets)
        {
            if (playable.getCurrentHp() > PET_IS_NOT_SLEEPING)
            {
                displayRechargeTimes(playable);
                playerPickSkill(playable);
            }
        }
    }

    /**
     * playerPickSkill talks with the IOManager and gets user input for their turn.
     * This is used in the damage calculation
     * @param  pet, the pet to pick a skill
     */
    public void playerPickSkill(Playable pet)
    {
        Skills skillToBeUsed = pet.chooseSkill();
        if(pet.getSkillRechargeTime(skillToBeUsed) > 0)
        {
            io.skillSelectionError();
            playerPickSkill(pet);
        }
    }

    /**
     * calculateDamage iterates through each pet and calculates the damage to be applied to the
     * the next non sleeping pet in the list based on the skill they selected
     */
    public void calculateDamage()
    {
        round = new Round(damageManager.getDamageCounter(), turnIndex);
        boolean hasAttacked = false;
        for (int i = 0; i < pets.size(); i++)
        {
            for(int j = i+1; j < pets.size(); j++) // looking for a pet to attack right of the attacker in the list
            {
                if(pets.get(j).getCurrentHp() > PET_IS_NOT_SLEEPING)
                {
                    damage = damageManager.calculateDamage(pets.get(i), pets.get(j));
                    round.addPet(pets.get(i));
                    hasAttacked = true;
                    break;
                }
            }
            if(!hasAttacked)
            {
                for(int j = 0; j < i; j++) // looking for a pet to attack left of the attacker in the list
                {
                    if(pets.get(j).getCurrentHp() > PET_IS_NOT_SLEEPING)
                    {
                        damage = damageManager.calculateDamage(pets.get(i), pets.get(j));
                        round.addPet(pets.get(i));
                        break;
                    }
                }
            }
            hasAttacked = false; // resting for the next loop
        }
        history.addRound(round);
    }

    /**
     * decrements the recharge times for each skill for each pet
     */
    public void decreaseRechargeTimes()
    {
        for(Playable playable : pets)
        {
            playable.decrementRechargeTimes();
        }
    }

    /**
     * updates the pets cool downs to reflect the skill they chose
     */
    public void updateCoolDowns()
    {
        for (Playable pet : pets)
        {
            if(pet.getCurrentHp() >= PET_IS_NOT_SLEEPING)
            {
                pet.setRechargeTime(null, 0);
            }
        }
    }

    /**
     * applies the damages the were calculated for each pet
     * @param round a list of the pets that are alive in the round
     */
    public void applyDamage(Round round)
    {
        damageManager.applyDamage(round);
    }

    /**
     * display the game state
     */
    public void displayGameState()
    {

        List<Playable> temp = io.sortList(pets);
        for (Playable pet : temp)
        {
            Damage damage = damageManager.getDamageForTurn(pet.getPetName());
            io.displayPlayableHealth(pet, damage.getRandomDamage(), damage.getConditionalDamage());
        }
    }

    /**
     * IsWinner determines if there has been a winner for the fight.
     * @return true if there is a winner for the fight else false
     */
    public boolean isWinner()
    {
        int aliveCount = 0;
        for (Playable pet : pets)
        {
            if (pet.getCurrentHp() >= PET_IS_NOT_SLEEPING)
            {
                aliveCount++;
            }
        }
        return aliveCount <= ONE_PET_AWAKE; // winning condition
    }

    /**
     * Displays the cool downs for the pets skills
     * @param pet playable
     */
    public void displayRechargeTimes(Playable pet)
    {
        io.displayCoolDowns(pet);
    }

    /**
     * resets the hp for each pet in the fight after the fight has ended
     */
    public void resetFight()
    {
        for (Playable pet : pets)
        {
            pet.resetHp();
            pet.reset();
        }
        damageManager.reset();
    }

    /**
     * @return the Caretaker
     */
    public Caretaker getCaretaker()
    {
        return this.caretaker;
    }
}
