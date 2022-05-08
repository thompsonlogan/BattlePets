package Pets;

import java.util.ArrayList;
import java.util.List;

/**
 * PetSetting class
 * PetSetting class manages the creating of the pets from the player
 * for the game
 */
public class PetSettings
{
    private final ArrayList<Playable> pets = new ArrayList<>(); // list of the battle pets being used in the game

    /**
     * Adds a pet to the list of pet playing in the game
     * @param pet battlePet
     */
    public void addPetToGame(BattlePet pet)
    {
        pets.add(pet);
    }

    /**
     * @return a list of the pets playing in the game
     */
    public List<Playable> getPets()
    {
        return this.pets;
    }
}
