package GameControllers;
import Damage.Damage;
import Domain.IOManager;
import Pets.Playable;

import java.util.List;

/**
 * Replay function after a battle
 */
public class ReplayMode
{
    private final int BACKWARD = 1; //Int command for stepping backward a state in list
    private final int FORWARD = 2; //Int command for stepping forward a state in list
    private final int CONTINUE = 3; //Int command for stepping forward until end of list
    private final int EXIT = 4; //Int command for exiting the replay mode

    private Caretaker caretaker; // Caretaker instance (iterable)
    private final Iterator iterator; // Iterator for the caretaker
    private final IOManager io = IOManager.INSTANCE; // Gives FightManager a reference to IOManager

    /**
     * Replay mode constructor
     * @param caretaker
     */
    public ReplayMode(Caretaker caretaker)
    {
        this.caretaker = caretaker;
        this.iterator = this.caretaker.iterator();
    }

    /**
     * Prompt/read command inputs
     */
    public void run()
    {
        int input = io.replayMode();
        while(input != EXIT)
        {
            if(input == BACKWARD) // step backward
            {
                stepBackwardCommand();
            }
            if(input == FORWARD) // step forward
            {
                stepForwardCommand();
            }
            if(input == CONTINUE) // continue
            {
                continueCommand();
            }
            input = io.replayMode();
        }
    }

    /**
     * Stepping backward in caretaker list on command
     */
    public void stepBackwardCommand()
    {
        if(iterator.hasPrevious())
        {
            Round.Memento memento = (Round.Memento) iterator.previous();
            Round round = new Round();
            round.restoreState(memento);
            io.displayOutput("Now displaying Fight #"+round.getFightNumber() + " Round #" + round.getNumberOfRound());
            displayGameState(round);
        }
        else
        {
            io.displayOutput("You have reached the first fight of the battle.");
        }
    }

    /**
     * Stepping forward in caretaker list on command
     */
    public void stepForwardCommand()
    {
        if(iterator.hasNext())
        {
            Round.Memento memento = (Round.Memento) iterator.next();
            Round round = new Round();
            round.restoreState(memento);
            io.displayOutput("Now displaying Fight #"+round.getFightNumber() + " Round #" + round.getNumberOfRound());
            displayGameState(round);
        }
        else
        {
            io.displayOutput("You have reached the last fight of the battle.");
        }
    }

    /**
     * Stepping forward in caretaker list until end is met
     */
    public void continueCommand()
    {
        while (iterator.hasNext())
        {
            stepForwardCommand();
        }
    }

    /**
     * Displays the status of a round state
     * @param round
     */
    public void displayGameState(Round round)
    {
        List<Playable> pets = io.sortList(round.getPetsInTurn());
        //for(Playable pet : round.getPetsInTurn())
        for(Playable pet : pets)
        {
            Damage damage = new Damage(
                    round.getDamageCounter().getPetRandomDamageDealt(pet.getPetName()),
                    round.getDamageCounter().getRoundRandomDamageDealt(pet.getPetName()));

            io.displayPlayableHealth(pet, damage.getRandomDamage(), damage.getConditionalDamage());
        }
    }
}
