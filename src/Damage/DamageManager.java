package Damage;
import GameControllers.Round;
import Pets.Playable;
import java.util.Hashtable;
import java.util.List;

/**
 * DamageManager
 * DamageManager manges all things to do will skill cool down
 * current it only gets the total damage for a skill
 */
public class DamageManager
{
    private final List<Playable> pets;
    private final Hashtable <Integer, Damage> attackDamageTable = new Hashtable<>();
    private final Hashtable <Integer, Integer> attackReceiverTable = new Hashtable<>();
    private final DamageCalculator damageCalculator = new DamageCalculator(); // object to calculate the damage of an attack
    private DamageCounter damageCounter; // Stores cumulative damage totals for each pet;

    /**
     * constructor for the skillManager
     * @param pets list of pets in the game
     */
    public DamageManager(List<Playable> pets)
    {
        this.pets = pets;
        this.damageCounter = new DamageCounter(this.pets);
        for (Playable pet : pets) {
            attackDamageTable.put(pet.getPlayableId(), new Damage(0, 0));
            attackReceiverTable.put(pet.getPlayableId(), -1);
        }
    }

    /**
     * applies the pre calculated damage from the hashtable to the intended receiver which is also
     * stored in the hashtable
     * @param round of the game
     */
    public void applyDamage(Round round)
    {
        List<Playable> petsInTurn = round.getPetsInTurn();
        for (Playable playable : petsInTurn) {
            Playable receiver;
            Damage damage;
            if (attackDamageTable.containsKey(playable.getPlayableId())) {
                receiver = getPetFromId(attackReceiverTable.get(playable.getPlayableId()));
                damage = attackDamageTable.get(playable.getPlayableId());
                double totalDamage = damage.calculateTotalDamage();
                receiver.updateHp(totalDamage);
                damageCounter.addPetRandomDamageDealt(playable.getPetName(), damage.getRandomDamage());
                damageCounter.addPetRandomDamageTaken(receiver.getPetName(), damage.getRandomDamage());
                damageCounter.setRoundRandomDamageDealt(playable.getPetName(), damage.getRandomDamage());
                damageCounter.setRoundConditionalDamageDealt(playable.getPetName(), damage.getConditionalDamage());
            }
        }
    }

    /**
     * Calculates the damage to be done based on the given pet types and skill selected
     * @param attacker the pet attacking
     * @param receiver the pet being attacked
     * @return a damage object to be applied
     */
    public Damage calculateDamage(Playable attacker, Playable receiver)
    {
        Damage damage = damageCalculator.calculateDamage(attacker, receiver, damageCounter);
        attackDamageTable.put(attacker.getPlayableId(), damage);
        attackReceiverTable.put(attacker.getPlayableId(), receiver.getPlayableId());
        return damage;
    }

    /**
     * Retrieves a pet from the game list of pets based on their playableId in the hashtable
     * @param playableId a pets game id
     * @return the pet requested based on its playableId
     */
    public Playable getPetFromId(int playableId)
    {
        for (Playable pet : pets) {
            if (pet.getPlayableId() == playableId)
                return pet;
        }
        return null;
    }

    /**
     * @param petName string name of the pet
     * @return a new damage object for the damage the pet did in the most recent round
     */
    public Damage getDamageForTurn(String petName)
    {
        return new Damage(damageCounter.getRoundRandomDamageDealt(petName),
                damageCounter.getRoundConditionalDamageDealt(petName));
    }

    /**
     * @return the DamageCounter object
     */
    public DamageCounter getDamageCounter()
    {
        return this.damageCounter;
    }

    /**
     * Resets the damage counter for a new fight
     */
    public void reset()
    {
        damageCounter.reset();
    }

}
