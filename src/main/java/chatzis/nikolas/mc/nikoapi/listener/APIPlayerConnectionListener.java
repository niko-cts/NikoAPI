package chatzis.nikolas.mc.nikoapi.listener;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import chatzis.nikolas.mc.nikoapi.player.PlayerPacketReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener that gets called if a player logs into the server.
 *
 * @author Niko
 * @since 0.0.1
 */
public class APIPlayerConnectionListener implements Listener {

    /**
     * Adds a APIPlayer to {@link chatzis.nikolas.mc.nikoapi.player.PlayerHandler}
     *
     * @param event PlayerLoginEvent - the login event.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        NikoAPI.getInstance().getPlayerHandler().addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (PlayerPacketReader.isEnabled())
            PlayerPacketReader.injectIfAbsent(event.getPlayer());
    }

    /**
     * Remove APIPlayer from {@link chatzis.nikolas.mc.nikoapi.player.PlayerHandler}
     *
     * @param event PlayerQuitEvent - the quit event.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogout(PlayerQuitEvent event) {
        PlayerPacketReader.uninject(event.getPlayer());
        NikoAPI.getInstance().getPlayerHandler().removePlayer(event.getPlayer().getUniqueId());
    }

}
