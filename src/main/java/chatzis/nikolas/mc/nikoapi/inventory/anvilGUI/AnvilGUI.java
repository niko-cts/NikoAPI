package chatzis.nikolas.mc.nikoapi.inventory.anvilGUI;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class fakes an anvil GUI and lets the user interact with it.
 * Instantiate this class to create a new AnvilGUI.
 * Set the contents of the Anvil and open the GUI for a player.
 * The user is able to click on the items and write it's message in the text field.
 *
 * @author Niko
 * @since 0.0.1
 */
public class AnvilGUI {

    private static final AnvilGUIWrapper VERSION_WRAPPER = new AnvilGUIWrapper();

    private final Player player;
    private final Map<AnvilSlot, ItemStack> items;
    private final Listener listener;
    private Inventory inventory;
    private String title;
    private int containerId;
    private boolean open;

    /**
     * Create a new GUI for a player.
     *
     * @param player  Player - Player to show the GUI
     * @param handler AnvilClickEventHandler - Method gets called, when player interacts with GUI
     * @since 0.0.1
     */
    public AnvilGUI(Player player, final AnvilClickEventHandler handler) {
        this.player = player;
        this.items = new EnumMap<>(AnvilSlot.class);
        this.title = " ";

        this.listener = new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                if (event.getWhoClicked() instanceof Player) {

                    if (event.getInventory().equals(inventory)) {
                        event.setCancelled(true);

                        ItemStack item = event.getCurrentItem();
                        int slot = event.getRawSlot();
                        String name = "";

                        if (item != null && item.hasItemMeta()) {
                            ItemMeta meta = item.getItemMeta();

                            if (meta.hasDisplayName()) {
                                name = meta.getDisplayName();
                            }
                        }

                        AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name, item, event.getClick());

                        handler.onAnvilClick(clickEvent);

                        if (clickEvent.getWillClose())
                            closeInventory();
                    }
                }
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if (event.getPlayer() instanceof Player) {
                    Inventory inv = event.getInventory();

                    if (inv.equals(AnvilGUI.this.inventory)) {
                        inv.clear();
                        destroy();
                    }
                }
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                if (event.getPlayer().getUniqueId().equals(getPlayer().getUniqueId())) {
                    destroy();
                }
            }
        };

    }


    /**
     * Get the Player for the GUI
     *
     * @return Player - Given Player
     * @since 0.0.1
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set a item on a slot
     * The GUI needs to be re-opened to update the GUI.
     *
     * @param slot AnvilSlot - Slot to display the item
     * @param item ItemStack - Item that appears in the GUI
     * @since 0.0.1
     */
    public void setSlot(AnvilSlot slot, ItemStack item) {
        items.put(slot, item);
    }

    /**
     * Opens the GUI for the given player
     * @since 0.0.1
     */
    public void open() {
        VERSION_WRAPPER.handleInventoryCloseEvent(player);
        VERSION_WRAPPER.setActiveContainerDefault(player);

        final AnvilGUIWrapper.AnvilContainer container = VERSION_WRAPPER.newContainerAnvil(player, title);

        inventory = VERSION_WRAPPER.toBukkitInventory(container);

        for (Map.Entry<AnvilSlot, ItemStack> entry : items.entrySet()) {
            inventory.setItem(entry.getKey().getSlot(), entry.getValue());
        }

        containerId = VERSION_WRAPPER.getNextContainerId(container);
        VERSION_WRAPPER.sendPacketOpenWindow(player, containerId, title);
        VERSION_WRAPPER.setActiveContainer(player, container);
        VERSION_WRAPPER.addActiveContainerSlotListener(container, player);
        Bukkit.getPluginManager().registerEvents(listener, NikoAPI.getInstance());
        this.open = true;
    }

    /**
     * Sets the title of the anvil gui.
     * default: blank.
     * @param title String - title of inventory.
     * @since 1.0.1
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the title of the inventory.
     * @return String - inventory title
     * @since 1.0.1
     */
    public String getTitle() {
        return title;
    }

    /**
     * Removes the Listener.
     * Does not close the GUI!
     * @since 0.0.1
     */
    private void destroy() {
        HandlerList.unregisterAll(listener);
    }

    /**
     * Closes the GUI for the player.
     * @since 1.0.1
     */
    public void closeInventory() {
        if (!open) return;

        destroy();

        VERSION_WRAPPER.handleInventoryCloseEvent(player);
        VERSION_WRAPPER.setActiveContainerDefault(player);
        VERSION_WRAPPER.sendPacketCloseWindow(player, containerId);
    }
}
