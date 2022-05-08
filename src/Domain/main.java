package Domain;
import GameControllers.*;
import Pets.*;
import Players.*;

/**
 * Main class is the driver for the program and executes
 * the game as the user progress though different parts of
 * the game
 */
public class main {
    public static void main(String[] args) {
        try {
            GameSettings gameSettings;
            IOManager GameIO = IOManager.INSTANCE;
            PetSettings petSettings = new PetSettings();
            PlayerSettings playerSettings = new PlayerSettings();


            // getting player input and their pet creations
            int numberOfPlayers = GameIO.getNumberOfPlayers();
            for (int i = 0; i < numberOfPlayers; i++) {
                Player newPlayer = GameIO.getPlayerInput(); // creating player
                BattlePet newBattlePet = GameIO.getPetInput(newPlayer); // creating pet
                newPlayer.addBattlePet(newBattlePet); // linking the player to the pet
                playerSettings.addPlayerToGame(newPlayer); // adding the player to the list
                petSettings.addPetToGame(newBattlePet); // adding the pet to the list
            }

            // setting game settings
            gameSettings = GameIO.getGameInput();

            // starting the battle
            BattleManager newBattleManager = new BattleManager(petSettings.getPets());

            // looping battle until the users do not want to play anymore
            while (true) {
                int rounds = gameSettings.getNumberOfFights();
                Battle newBattle = newBattleManager.createBattle(rounds);

                newBattle.runBattle();

                GameIO.displayOutput("The Battle winner is: " + newBattle.findBattleWinner().getPetName());

                if (GameIO.enterReplayMode()) {
                    ReplayMode replayMode = new ReplayMode(newBattle.getCaretaker());
                    replayMode.run();
                }

                if (!(GameIO.getPlayAgainInput())) // if no replay
                    break;
            }
            // replay mode object
            GameIO.displayOutput("See you next time, thanks for playing.");
        } catch (Exception e) {
            System.out.println("an exception occurred");
            e.printStackTrace();
        }
    }
}