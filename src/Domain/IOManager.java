//import org.junit.platform.commons.function.Try;
package Domain;
import GameControllers.GameSettings;
import Pets.*;
import Players.*;
import Skills.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Enum singleton for accepting input and displaying output
 */

public enum IOManager
{
    INSTANCE;

    public static DecimalFormat df2 = new DecimalFormat("#.##");
    private final int ROCK_THROW_INPUT = 1; // constant value for the rock throw input
    private final int SCISSORS_POKE_INPUT = 2; // constant value for the scissor poke input
    private final int PAPER_CUT_INPUT = 3; // constant value for the paper cut input
    private final int SHOOT_THE_MOON_INPUT = 4; // constant value for the shoot the moon input
    private final int REVERSAL_OF_FORTUNE_INPUT = 5; // constant value for the reversal of fortune input

    private final int POWER_INPUT = 1; // constant value for the power pet type input
    private final int SPEED_INPUT = 2; // constant value for the speed pet type input
    private final int INTELLIGENCE_INPUT = 3; // constant value for the intelligence pet type input

    private final int HUMAN_INPUT = 1; // constant value for the human player type input
    private final int COMPUTER_INPUT = 2; // constant value for the computer player type input

    private final int INVALID_INTEGER_INPUT = -5; // used to check the scanner for invalid input
    private final double INVALID_DOUBLE_INPUT = -5; // used to check the scanner for invalid input
    private final long INVALID_LONG_INPUT = -5; // used to check the scanner for invalid input
    private final int MIN_PLAYERS = 2; // constant for a player count less then 1

    private final int ENTER_REPLAY_MODE = 1; // constant to enter replay mode
    private final int STEP_BACKWARDS = 1; // constant for command to step backwards in replay mode
    private final int STEP_FORWARDS = 2; // constant for command to step forwards in replay mode
    private final int CONTINUE = 3; // constant for command to continue in replay mode
    private final int EXIT = 4; // constant for command to exit replay mode

    private int idCounter = -1; // Increments for creation of a new pet
    Scanner scanner = new Scanner(System.in); // the scanner to take user input

    /**
     * Prompt user to get data for selecting a skill
     * @return Skill entered by user
     */
    public Skills getSkillInput(BattlePet pet)
    {
        int numericInput;
        displayOutput(pet.getPetName() + " What skill will you use next? Enter...\n1 for Rock Throw\n2 for Scissors Poke\n3 for Paper Cut" +
                "\n4 for Shoot the Moon\n5 for Reversal of Fortune");
        numericInput = INVALID_INTEGER_INPUT;

        while (numericInput == INVALID_INTEGER_INPUT)
        {
            try
            {
                numericInput = scanner.nextInt();

                while (numericInput != ROCK_THROW_INPUT && numericInput != SCISSORS_POKE_INPUT && numericInput != PAPER_CUT_INPUT
                        && numericInput != SHOOT_THE_MOON_INPUT && numericInput != REVERSAL_OF_FORTUNE_INPUT)
                {
                    displayOutput("Invalid input for skill selection. Enter...\n1 for Rock Throw\n2 for Scissors Poke\n3 for Paper Cut" +
                            "\n4 for Shoot the Moon\n5 for Reversal of Fortune");
                    numericInput = scanner.nextInt();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Invalid input for skill selection. Enter...\n1 for Rock Throw\n2 for Scissors Poke\n3 for Paper Cut" +
                        "\n4 for Shoot the Moon\n5 for Reversal of Fortune");
            }
            scanner.nextLine(); // "clearing" the scanner
        }

        if (numericInput == ROCK_THROW_INPUT)
        {
            return Skills.ROCK_THROW;
        }
        else if (numericInput == SCISSORS_POKE_INPUT)
        {
            return Skills.SCISSORS_POKE;
        }
        else if (numericInput == PAPER_CUT_INPUT)
        {
            return Skills.PAPER_CUT;
        }
        else if (numericInput == SHOOT_THE_MOON_INPUT)
        {
            return  Skills.SHOOT_THE_MOON;
        }
        else
        {
            return  Skills.REVERSAL_OF_FORTUNE;
        }
    }

    /**
     * Prompt user to get data for a BattlePet object
     */
    public BattlePet getPetInput(Player player)
    {
        BattlePet.BattlePetBuilder newBattlePetBuilder = new BattlePet.BattlePetBuilder();

        displayOutput("What is the name of your pet?");
        newBattlePetBuilder.withName(scanner.next());

        displayOutput("What is your pet's type? Enter...\n1 for Power\n2 for Speed\n3 for Intelligence");
        int numericInput = INVALID_INTEGER_INPUT;

        while (numericInput == INVALID_INTEGER_INPUT)
        {
            try
            {
                numericInput = scanner.nextInt();
                while (numericInput != POWER_INPUT && numericInput != SPEED_INPUT && numericInput != INTELLIGENCE_INPUT)
                {
                    displayOutput("Invalid input for pet type. Enter...\n1 for Power\n2 for Speed\n3 for Intelligence");
                    numericInput = scanner.nextInt();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Invalid input for pet type. Enter...\n1 for Power\n2 for Speed\n3 for Intelligence");
                numericInput = INVALID_INTEGER_INPUT;
            }
            scanner.nextLine(); // "clearing" the scanner
        }

        if (numericInput == POWER_INPUT)
        {
            newBattlePetBuilder.withType(PetTypes.POWER);
        }
        else if (numericInput == SPEED_INPUT)
        {
            newBattlePetBuilder.withType(PetTypes.SPEED);
        }
        else
        {
            newBattlePetBuilder.withType(PetTypes.INTELLIGENCE);
        }

        displayOutput("What is the starting hp of your pet?");
        double startingHp = INVALID_DOUBLE_INPUT;
        while (startingHp == INVALID_DOUBLE_INPUT)
        {
            try
            {
                startingHp = scanner.nextDouble();
                while (startingHp <= 0)
                {
                    displayOutput("Starting hp must be a number greater than 0");
                    startingHp = scanner.nextDouble();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Starting hp must be a number greater than 0");
                startingHp = INVALID_DOUBLE_INPUT;
            }
            scanner.nextLine(); // "clearing" the scanner
        }
        newBattlePetBuilder.withStartingHp(startingHp);

        idCounter++;
        newBattlePetBuilder.withId(idCounter);
        newBattlePetBuilder.withPlayerName(player.getName());
        newBattlePetBuilder.withPlayerType(player.getType());

        BattlePet newBattlePet = newBattlePetBuilder.build();
        displayOutput(newBattlePet.getPetName() + " has entered the battle\n"
                + newBattlePet.getPetName() + "'s ID is " + newBattlePet.getPlayableId() + "\n");

        return newBattlePet;
    }

    /**
     * Prompt user to get data for a Player object
     * @return Player object
     */
    public Player getPlayerInput()
    {
        Player newPlayer = new Player();

        displayOutput("Hello!\nWelcome to Battle Pets\nTo begin, enter 1 if you are a human or 2 if you are a computer");
        int numericInput = INVALID_INTEGER_INPUT;

        while (numericInput == INVALID_INTEGER_INPUT)
        {
            try
            {
                numericInput = scanner.nextInt();
                while (numericInput != HUMAN_INPUT && numericInput != COMPUTER_INPUT)
                {
                    displayOutput("Invalid input for player type. Enter...\n1 for Human\n2 for Computer");
                    numericInput = scanner.nextInt();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Invalid input for player type. Enter...\n1 for Human\n2 for Computer");
                numericInput = INVALID_INTEGER_INPUT;
            }
            scanner.nextLine(); // "clearing" the scanner
        }

        if (numericInput == HUMAN_INPUT)
        {
            newPlayer.setType(PlayerTypes.HUMAN);

            displayOutput("What is your name?");
            newPlayer.setName(scanner.next());
        }
        else
        {
            newPlayer.setType(PlayerTypes.COMPUTER);
        }

        return newPlayer;
    }

    /**
     * Gathers all the input needed from the user for the game settings
     * @return a GameSettings object with all the setting for the game
     */
    public GameSettings getGameInput()
    {
        displayOutput("Enter a random seed for the game, or -1 for the default seed");
        long longInput = INVALID_LONG_INPUT;
        int numericInput = INVALID_INTEGER_INPUT;
        while(longInput == INVALID_LONG_INPUT)
        {
            try
            {
                longInput = scanner.nextLong();
                if(longInput == -1)
                {
                    longInput = 7;
                    break;
                }
                while(longInput <= 0)
                {
                    displayOutput("Invalid input for game seed. Please enter a number greater than 0");
                    longInput = scanner.nextLong();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Invalid input for game seed. Please enter a number greater than 0");
                longInput = INVALID_LONG_INPUT;
            }
            scanner.nextLine(); // "clearing" the scanner
        }
        displayOutput("Time to battle!\nHow many fights are in the battle?");
        while(numericInput == INVALID_INTEGER_INPUT)
        {
            try
            {
                numericInput = scanner.nextInt();
                while(numericInput <= 0)
                {
                    displayOutput("Invalid input for the number of fights");
                    numericInput = scanner.nextInt();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Invalid input for the number of fights");
                numericInput = INVALID_INTEGER_INPUT;
            }
            scanner.nextLine();
        }
        return new GameSettings(numericInput, longInput);
    }

    /**
     * Prompts the user for an input on the number of players playing the game.
     * Checks for invalid input
     * @return how many players and playing the game
     */
    public int getNumberOfPlayers()
    {
        displayOutput("How many players are playing");
        int numericInput = INVALID_INTEGER_INPUT;
        while (numericInput == INVALID_INTEGER_INPUT)
        {
            try
            {
                numericInput = scanner.nextInt();
                while (numericInput < MIN_PLAYERS)
                {
                    displayOutput("Invalid input for number of players. Enter a number greater than 1");
                    numericInput = scanner.nextInt();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Invalid input for number of players. Enter a number greater than 1");
                numericInput = INVALID_INTEGER_INPUT;
            }
            scanner.nextLine(); // "clearing" the scanner
        }
        return numericInput;
    }

    /**
     * Prompt to be displayed if an error occurs during skill selection
     */
    public void skillSelectionError()
    {
        displayOutput("That skill is on cool down");
    }

    /**
     * @return a boolean, true if user wants to play again
     */
    public boolean getPlayAgainInput()
    {
        int numericInput = -1;
        displayOutput("Enter any number to play again, or 0 to quit.");
        try
        {
            numericInput = scanner.nextInt();
        }
        catch(InputMismatchException e)
        {
            displayOutput("Invalid input. Please try again");
            getPlayerInput();
        }
        return numericInput != 0;
    }

    /**
     * Allows the player to predict the skill their opponent is going to attack with
     * @return the players skill prediction
     */
    public Skills predictSkill()
    {
        int prediction = INVALID_INTEGER_INPUT;
        displayOutput("Guess the skill the opponent will use. Enter...\n1 for Rock Throw\n2 for Scissors Poke" +
                "\n3 for Paper Cut\n4 for Shoot the Moon\n5 for Reversal of Fortune");
        while (prediction == INVALID_INTEGER_INPUT)
        {
            try
            {
                prediction = scanner.nextInt();
                while (prediction != ROCK_THROW_INPUT && prediction != SCISSORS_POKE_INPUT
                        && prediction != PAPER_CUT_INPUT && prediction!= SHOOT_THE_MOON_INPUT
                        && prediction != REVERSAL_OF_FORTUNE_INPUT)
                {
                    displayOutput("Invalid input for skill selection. Enter...\n1 for Rock Throw\n2 for Scissors Poke" +
                            "\n3 for Paper Cut\n4 for Shoot the Moon\n5 for Reversal of Fortune");
                    prediction = scanner.nextInt();
                }
            }
            catch(InputMismatchException e)
            {
                displayOutput("Invalid input for skill selection. Enter...\n1 for Rock Throw\n2 for Scissors Poke" +
                        "\n3 for Paper Cut\n4 for Shoot the Moon\n5 for Reversal of Fortune");
                prediction = INVALID_INTEGER_INPUT;
            }
            scanner.nextLine();
        }

        if (prediction == ROCK_THROW_INPUT)
        {
            return Skills.ROCK_THROW;
        }
        else if (prediction == SCISSORS_POKE_INPUT)
        {
            return Skills.SCISSORS_POKE;
        }
        else if (prediction == PAPER_CUT_INPUT)
        {
            return Skills.PAPER_CUT;
        }
        else if (prediction == SHOOT_THE_MOON_INPUT)
        {
            return Skills.SHOOT_THE_MOON;
        }
        else
        {
            return Skills.REVERSAL_OF_FORTUNE;
        }
    }

    /**
     * Display the current health of the passed Playable
     * @param battlePet the pet who information is to be displayed
     */
    public void displayPlayableHealth(Playable battlePet, double randomDamage, double conditionalDamage)
    {
        df2.setRoundingMode(RoundingMode.UP);
        displayOutput(battlePet.getPetName() + "'s current HP is: " + df2.format(battlePet.getCurrentHp())
                + "\nrandom damage dealt is: " + df2.format(randomDamage)
                + "\nconditional damage dealt is: " + df2.format(conditionalDamage)
                + "\ntotal damage dealt is: " + df2.format(randomDamage + conditionalDamage)
                + "\n");
    }
    /**
     * this method will temporarily sort the list of pets by health
     * @return sorted list of pets
     */
    public List<Playable> sortList(List<Playable> pets)
    {
        List<Double> temp = new ArrayList<>();
        List<Playable> sortedByPetsHealth = new ArrayList<>();

        for(Playable playable : pets)
        {
            temp.add(playable.getCurrentHp());
        }
        Collections.sort(temp);
        Collections.reverse(temp);

        for(int i = 0; i < temp.size(); i++)
        {
            for(int j = 0; j < pets.size(); j++)
            {
                if (temp.get(i) == pets.get(j).getCurrentHp())
                {
                    sortedByPetsHealth.add(pets.get(j));
                }
            }
        }

        return sortedByPetsHealth;
    }

    /**
     * Gets player input for enter replay mode
     * @return true if the play would like to enter reply mode
     */
    public boolean enterReplayMode()
    {
        displayOutput("Enter '1' to view replay");
        int numericInput = INVALID_INTEGER_INPUT;
        while (numericInput == INVALID_INTEGER_INPUT)
        {
            try
            {
                numericInput = scanner.nextInt();

            }
            catch (InputMismatchException e)
            {
                scanner.nextLine();
                return false;
            }
        }
        return numericInput == ENTER_REPLAY_MODE;
    }

    /**
     * Runs the replay mode menu
     * @return the players command
     */
    public int replayMode()
    {
        displayOutput("What would you like to do? Enter...\n1 to step backward\n2 to step forward\n3 to continue\n4 to exit replay mode");
        int numericInput = INVALID_INTEGER_INPUT;
        while(numericInput == INVALID_INTEGER_INPUT)
        {
            try
            {
                numericInput = scanner.nextInt();
                while (numericInput != STEP_BACKWARDS && numericInput != STEP_FORWARDS && numericInput != CONTINUE && numericInput != EXIT)
                {
                    displayOutput("Invalid input. Enter...\n1 to step backward\n2 to step forward\n3 to continue\n4 to exit replay mode");
                    numericInput = scanner.nextInt();
                }
            }
            catch (InputMismatchException e)
            {
                displayOutput("Invalid input. Enter...\n1 to step backward\n2 to step forward\n3 to continue\n4 to exit replay mode");
                numericInput = INVALID_INTEGER_INPUT;
            }
        }
        return numericInput;
    }

    /**
     * Display the currents pets cool down times
     * @param pet
     */
    public void displayCoolDowns(Playable pet)
    {
        displayOutput("\n" + pet.getPetName() + " current recharge times are:"
                + "\nPaper Cut: " + pet.getSkillRechargeTime(Skills.PAPER_CUT)
                + "\nRock Throw: " + pet.getSkillRechargeTime(Skills.ROCK_THROW)
                + "\nScissor Poke: " + pet.getSkillRechargeTime(Skills.SCISSORS_POKE)
                + "\nShoot The Moon: " + pet.getSkillRechargeTime(Skills.SHOOT_THE_MOON)
                + "\nReversal Of Fortune: " + pet.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE));
    }

    /**
     * Display a message to the user
     */
    public void displayOutput(String output)
    {
        System.out.println(output);
    }
}