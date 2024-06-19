package chatzis.nikolas.mc.nikoapi.inventory;

import chatzis.nikolas.mc.nikoapi.player.APIPlayer;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

/**
 * Class to define actions on clicking an item in an inventory.
 * @author HeyImDome
 * @since 0.0.1
 */
public abstract class ClickAction {

    private final Sound clickSound;
    private boolean closeInventory;

    /**
     * Creates a click action.
     * @param clickSound Sound - the sound on click.
     * @param closeInventory boolean - whatever the inventory should be closed on click.
     * @since 0.0.1
     */
    public ClickAction(Sound clickSound, boolean closeInventory) {
        this.clickSound = clickSound;
        this.closeInventory = closeInventory;
    }

    /**
     * Creates a click action.
     * @param clickSound Sound - the sound on click.
     * @since 0.0.1
     */
    public ClickAction(Sound clickSound) {
        this (clickSound, false);
    }

    /**
     * Creates a click action.
     * @param closeInventory boolean - whatever the inventory should be closed on click.
     * @since 0.0.1
     */
    public ClickAction(boolean closeInventory) {
        this (null, closeInventory);
    }

    /**
     * Creates a click action.
     * @since 0.0.1
     */
    public ClickAction() {
        this (null, false);
    }

    /**
     * Gets the sound which should be played on click.
     * @return Sound - the sound to play.
     * @since 0.0.1
     */
    public Sound getClickSound() {
        return clickSound;
    }

    /**
     * Gets if the inventory should be closed on click.
     * @return boolean - whatever it should be closed.
     * @since 0.0.1
     */
    public boolean isAutoClose() {
        return closeInventory;
    }

    /**
     * Set if the inventory should be closed on click.
     * @param closeInventory boolean - inventory closes after click.
     * @since 0.0.1
     */
    public void setCloseInventory(boolean closeInventory) {
        this.closeInventory = closeInventory;
    }

    /**
     * Gets called whenever a item with that click action was left clicked.
     * @param apiPlayer {@link APIPlayer} - the player who clicked.
     * @param itemStack ItemStack - the clicked item.
     * @param slot Integer - the clicked slot.
     * @since 0.0.1
     */
    public abstract void onClick(APIPlayer apiPlayer, ItemStack itemStack, int slot);

    /**
     * Gets called whenever a item with that click action was right clicked.
     * @param apiPlayer {@link APIPlayer} - the player who clicked.
     * @param itemStack ItemStack - the clicked item.
     * @param slot Integer - the clicked slot.
     * @since 0.0.1
     */
    public void onRightClick(APIPlayer apiPlayer, ItemStack itemStack, int slot) {
        onClick(apiPlayer, itemStack, slot);
    }

    /**
     * Gets called whenever a item with that click action was left clicked with shift.
     * @param apiPlayer {@link APIPlayer} - the player who clicked.
     * @param itemStack ItemStack - the clicked item.
     * @param slot Integer - the clicked slot.
     * @since 0.0.1
     */
    public void onShiftClick(APIPlayer apiPlayer, ItemStack itemStack, int slot) {
        onClick(apiPlayer, itemStack, slot);
    }

    /**
     * Gets called whenever a item with that click action was right clicked with shift.
     * @param apiPlayer {@link APIPlayer} - the player who clicked.
     * @param itemStack ItemStack - the clicked item.
     * @param slot Integer - the clicked slot.
     * @since 0.0.1
     */
    public void onShiftRightClick(APIPlayer apiPlayer, ItemStack itemStack, int slot) {
        onClick(apiPlayer, itemStack, slot);
    }

}
