package chatzis.nikolas.mc.nikoapi.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Handler for all api players which are currently online.
 * @author Niko
 * @since 0.0.1
 */
public class PlayerHandler {

    private final ConcurrentMap<UUID, APIPlayer> onlinePlayers;

    /**
     * Creates an instance of the player handler.
     * @since 0.0.1
     */
    public PlayerHandler() {
        this.onlinePlayers = new ConcurrentHashMap<>();
    }

    /**
     * Gets a collection of all online api players.
     * @see APIPlayer
     * @return Collection<APIPlayer>
     * @since 0.0.1
     */
    public Collection<APIPlayer> getOnlinePlayers() {
        return onlinePlayers.values();
    }

    /**
     * Gets the APIPlayer instance for a given uuid
     * @param uuid UUID - the UUID of the target player
     * @return {@link APIPlayer}
     * @since 0.0.1
     */
    public APIPlayer getPlayer(UUID uuid) {
        return onlinePlayers.get(uuid);
    }

    /**
     * Gets the APIPlayer for a given name.
     * @param name String - the player name.
     * @return {@link APIPlayer}
     * @since 0.0.1
     */
    public APIPlayer getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null)
            return null;

        return getPlayer(player.getUniqueId());
    }

    /**
     * Gets the APIPlayer instance for a given player
     * @param player Player - the player
     * @return {@link APIPlayer}
     * @since 0.0.1
     */
    public APIPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    /**
     * Adds a player to the player handler.
     * @param apiPlayer APIPlayer - the player to add.
     * @since 0.0.1
     */
    public void addPlayer(APIPlayer apiPlayer) {
        onlinePlayers.put(apiPlayer.getUniqueId(), apiPlayer);
    }

    /**
     * Adds a player to the player handler and returns the api player instance.
     *
     * @param player Player - the player to add.
     * @since 0.0.1
     */
    public void addPlayer(Player player) {
        onlinePlayers.putIfAbsent(player.getUniqueId(), new APIPlayer(player));
    }

    /**
     * Removes a player from the player handler.
     * @param uuid UUID - the uuid to remove
     * @since 0.0.1
     */
    public void removePlayer(UUID uuid) {
        onlinePlayers.remove(uuid);
    }

}
