package Damage;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import Pets.*;

/**
 * DamageCounter holds and manages the running totals of
 * random damage and conditional damage that each
 * pet has done
 */
public class DamageCounter
{
    private final int TOTAL_RANDOM_DAMAGE_DEALT_INDEX = 0; // value for the index of the random damage total
    private final int TOTAL_RANDOM_DAMAGE_TAKEN_INDEX = 1; // value for the index of the random damage taken
    private final int TURN_RANDOM_DAMAGE_DEALT_INDEX = 2; // value for the index of the random damage dealt in the most recent turn
    private final int TURN_CONDITIONAL_DAMAGE_DEALT_INDEX = 3; // value for the index of the conditional damage dealt in the most recent turn
    private final double INITIAL_TOTAL_RANDOM_DAMAGE_DEALT = 0; // value for the initial total of random damage
    private final double INITIAL_TOTAL_RANDOM_DAMAGE_TAKEN = 0; // value for the initial total random damage taken
    private final double FIRST_TURN_RANDOM_DAMAGE_DEALT = 0; // value for the initial turn random damage
    private final double FIRST_TURN_CONDITIONAL_DAMAGE_DEALT = 0; // value for the initial turn conditional damage

    private final List<Playable> pets; // list of pets in the fight
    private Hashtable<String, List<Double>> petDamage; // hashtable for storing the pets run totals for random and conditional damage, pet names are the keys

    /**
     * constructor to initialize the hashtable.
     * pet names are used as keys for the has table.
     * @param petNames string for the name of the pet
     */
    public DamageCounter(List<Playable> petNames)
    {
        this.pets = petNames;
        petDamage = new Hashtable<>();
        for (Playable petName : petNames)
        {
            List<Double> newPetDamage = new ArrayList<>();
            newPetDamage.add(INITIAL_TOTAL_RANDOM_DAMAGE_DEALT);
            newPetDamage.add(INITIAL_TOTAL_RANDOM_DAMAGE_TAKEN);
            newPetDamage.add(FIRST_TURN_RANDOM_DAMAGE_DEALT);
            newPetDamage.add(FIRST_TURN_CONDITIONAL_DAMAGE_DEALT);
            petDamage.put(petName.getPetName(), newPetDamage);
        }
    }

    /**
     * Copy constructor for the DamageCounter
     * @param damageCounter the object to be deep copied
     */
    public DamageCounter(DamageCounter damageCounter)
    {
        this.pets = damageCounter.pets;
        petDamage = new Hashtable<>();
        for (Playable pet : pets)
        {
            List<Double> newPetDamage = new ArrayList<>();
            newPetDamage.add(damageCounter.getPetRandomDamageDealt(pet.getPetName()));
            newPetDamage.add(damageCounter.getPetRandomDamageTaken(pet.getPetName()));
            newPetDamage.add(damageCounter.getRoundRandomDamageDealt(pet.getPetName()));
            newPetDamage.add(damageCounter.getRoundConditionalDamageDealt(pet.getPetName()));
            petDamage.put(pet.getPetName(), newPetDamage);
        }
    }

    /**
     * pet name used to find the amount of random damage dealt
     * @param petName string for the name of the pet
     */
    public double getPetRandomDamageDealt(String petName)
    {
        return petDamage.get(petName).get(TOTAL_RANDOM_DAMAGE_DEALT_INDEX);
    }

    /**
     * addPetDamageDealt adds the new random and conditional damage dealt to the running
     * total for the pet. The hashtable is indexed based on the pets name
     * @param petName string for the name of the pet
     * @param randomDamageTaken double number for the random damage the pet id dealing
     */
    public void addPetRandomDamageDealt(String petName, double randomDamageTaken)
    {
        double randomTotal = petDamage.get(petName).get(TOTAL_RANDOM_DAMAGE_DEALT_INDEX);
        petDamage.get(petName).set(TOTAL_RANDOM_DAMAGE_DEALT_INDEX, randomTotal + randomDamageTaken);
    }

    /**
     * pet name used to find the amount of random damage received
     * @param petName string for the name of the pet
     */
    public double getPetRandomDamageTaken(String petName)
    {
        return petDamage.get(petName).get(TOTAL_RANDOM_DAMAGE_TAKEN_INDEX);
    }

    /**
     * method that updates the random damage taken in the hashtable
     * @param petName string for the name of the pet
     * @param randomDamageTaken double number for the random damage the pet is taking
     */
    public void addPetRandomDamageTaken(String petName, double randomDamageTaken)
    {
        double randomDamageTakenTotal = petDamage.get(petName).get(TOTAL_RANDOM_DAMAGE_TAKEN_INDEX);
        petDamage.get(petName).set(TOTAL_RANDOM_DAMAGE_TAKEN_INDEX, randomDamageTakenTotal + randomDamageTaken);
    }

    /**
     * setTurnRandomDamageDealt updates the pets random damage dealt in the most recent round
     * @param petName string for the name of the pet
     * @param turnRandomDamage double number for the random damage dealt by the pet in a round
     */
    public void setRoundRandomDamageDealt(String petName, double turnRandomDamage)
    {
        petDamage.get(petName).set(TURN_RANDOM_DAMAGE_DEALT_INDEX, turnRandomDamage);
    }

    /**
     * @param petName string for the name of the pet
     * @return the pets random damage dealt in the most recent round
     */
    public double getRoundRandomDamageDealt(String petName)
    {
        return petDamage.get(petName).get(TURN_RANDOM_DAMAGE_DEALT_INDEX);
    }

    /**
     * setTurnConditionalDamageDealt updates the pets conditional damage dealt in the most recent round
     * @param petName string for the name of the pet
     * @param turnConditionalDamage double number for the random damage dealt by the pet in a round
     */
    public void setRoundConditionalDamageDealt(String petName, double turnConditionalDamage)
    {
        petDamage.get(petName).set(TURN_CONDITIONAL_DAMAGE_DEALT_INDEX, turnConditionalDamage);
    }

    /**
     * @param petName string for the name of the pet
     * @return the pets conditional damage dealt in the most recent round
     */
    public double getRoundConditionalDamageDealt(String petName)
    {
        return petDamage.get(petName).get(TURN_CONDITIONAL_DAMAGE_DEALT_INDEX);
    }

    /**
     * Resets the damage counter after each fight
     */
    public void reset()
    {
        petDamage = new Hashtable<>();
        for (Playable pet : pets)
        {
            List<Double> newPetDamage = new ArrayList<>();
            newPetDamage.add(INITIAL_TOTAL_RANDOM_DAMAGE_DEALT);
            newPetDamage.add(INITIAL_TOTAL_RANDOM_DAMAGE_TAKEN);
            newPetDamage.add(FIRST_TURN_RANDOM_DAMAGE_DEALT);
            newPetDamage.add(FIRST_TURN_CONDITIONAL_DAMAGE_DEALT);
            petDamage.put(pet.getPetName(), newPetDamage);
        }
    }
}
