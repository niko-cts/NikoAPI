package chatzis.nikolas.mc.nikoapi.inventory.anvilGUI;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Event that gets triggered,
 * when a Player interacts with the {@link AnvilGUI}.
 * @author Niko
 * @since 0.0.1
 */
public class AnvilClickEvent {

    private final AnvilSlot slot;
    private final String name;

    private final ItemStack clickedItem;
    private final ClickType clickType;

    private boolean close;
    private boolean destroy;

    /**
     * Instantiate a new AnvilClickEvent.
     * @param slot AnvilSlot - Slot that was clicked
     * @param name String - Display name of the Item
     * @param clickedItem ItemStack - Clicked Item
     * @param clickType ClickType - Type of click
     * @since 0.0.1
     */
    public AnvilClickEvent(AnvilSlot slot, String name, ItemStack clickedItem, ClickType clickType) {
        this.slot = slot;
        this.name = name;
        this.clickedItem = clickedItem;
        this.clickType = clickType;
    }

    /**
     * Get the slot, the player clicked.
     * @return AnvilSlot - Slot of Clicked
     * @since 0.0.1
     */
    public AnvilSlot getSlot() {
        return slot;
    }

    /**
     * Get the display name of the item
     * @return String - display name
     * @since 0.0.1
     */
    public String getName() {
        return name;
    }

    /**
     * Get the clicked item
     * @return ItemStack - Clicked item
     * @since 0.0.1
     */
    public ItemStack getClickedItem() {
        return clickedItem;
    }

    /**
     * Get the clicked type
     * @see ClickType
     * @return ClickType - Type of click
     * @since 0.0.1
     */
    public ClickType getClickType() {
        return clickType;
    }

    /**
     * GUI will be closed after the event
     * @return boolean - GUI will close
     * @since 0.0.1
     */
    public boolean getWillClose() {
        return close;
    }

    /**
     * Set if the GUI should be closed after the event
     * @param close boolean - GUI closes
     * @since 0.0.1
     */
    public void setWillClose(boolean close) {
        this.close = close;
    }

    /**
     * GUI will be destroyed after the event
     * @return boolean - GUI will destroy itself
     * @since 0.0.1
     */
    @Deprecated
    public boolean getWillDestroy() {
        return destroy;
    }

    /**
     * Set if the GUI should be destroyed after the event
     * @param destroy boolean - GUI will destroy itself
     * @since 0.0.1
     */
    public void setWillDestroy(boolean destroy) {
        this.destroy = destroy;
    }
}
