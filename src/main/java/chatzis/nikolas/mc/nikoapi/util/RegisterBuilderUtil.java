package chatzis.nikolas.mc.nikoapi.util;

import chatzis.nikolas.mc.nikoapi.packet.reader.APIListenerManager;
import chatzis.nikolas.mc.nikoapi.packet.reader.IAPIListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility to register event listeners and api commands.
 * @author Niko
 * @since 0.0.1
 */
public class RegisterBuilderUtil {

    private final JavaPlugin javaPlugin;

    private final List<Listener> listeners;
    private final List<IAPIListener> iapiListeners;

    /**
     * Creates an instance of the register util.
     * @param javaPlugin JavaPlugin - the plugin to register for.
     * @since 0.0.1
     */
    public RegisterBuilderUtil(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.listeners = new ArrayList<>();
        this.iapiListeners = new ArrayList<>();
    }


    /**
     * Adds a list of listeners to the registerer.
     * @param listeners Listener[] - the event listeners.
     * @return {@link RegisterBuilderUtil} - instance of this class
     * @since 0.0.1
     */
    public RegisterBuilderUtil addListeners(Listener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
        return this;
    }

    /**
     * Adds a list of api packet listeners to the registerer.
     * @param listeners IAPIListener[] - the packet listener.
     * @return {@link RegisterBuilderUtil} - instance of this class
     * @since 1.0.2
     */
    public RegisterBuilderUtil addPacketListener(IAPIListener... listeners) {
        this.iapiListeners.addAll(Arrays.asList(listeners));
        return this;
    }

    /**
     * Registers all commands, event- & packet-listeners
     * @since 0.0.1
     */
    public void register() {
        PluginManager pluginManager = javaPlugin.getServer().getPluginManager();
        listeners.forEach(listener -> pluginManager.registerEvents(listener, javaPlugin));
        iapiListeners.forEach(listener -> APIListenerManager.getInstance().registerListener(listener));
    }
}
