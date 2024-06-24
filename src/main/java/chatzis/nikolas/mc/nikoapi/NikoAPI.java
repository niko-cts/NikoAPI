package chatzis.nikolas.mc.nikoapi;

import chatzis.nikolas.mc.nikoapi.inventory.InventoryListener;
import chatzis.nikolas.mc.nikoapi.listener.APIPlayerConnectionListener;
import chatzis.nikolas.mc.nikoapi.player.PlayerHandler;
import chatzis.nikolas.mc.nikoapi.util.RegisterBuilderUtil;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This is the main NikoAPI plugin class.
 * It is used to load every necessary part at the start of the server.
 * <br>
 * The NikoAPI is used as a base api plugin for other plugins of mine.
 *
 * @author Niko
 * @since 0.0.1
 */
public final class NikoAPI extends JavaPlugin {

    private static NikoAPI instance;
    private PlayerHandler playerHandler;

    public NikoAPI() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.playerHandler = new PlayerHandler();

        new RegisterBuilderUtil(this)
                .addListeners(new APIPlayerConnectionListener(), new InventoryListener())
                .register();
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public static NikoAPI getInstance() {
        return instance;
    }
}
