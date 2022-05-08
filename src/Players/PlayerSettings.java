package Players;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerSettings
 * PlayerSettings manages the creating for plays for the game
 */
public class PlayerSettings extends Player
{
    private final ArrayList<Player> players = new ArrayList<>();


    /**
     * adds a player to the game
     * @param player player
     */
    public void addPlayerToGame(Player player)
    {
        players.add(player);
    }

    /**
     * @return a list of players in the game
     */
    public List<Player> getPlayers()
    {
        return this.players;
    }

    /**
     * @param index index
     * @return a player from the game given their index
     */
    public Player getPlayer(int index)
    {
        return players.get(index);
    }
}
