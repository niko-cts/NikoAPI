package chatzis.nikolas.mc.nikoapi.inventory;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import chatzis.nikolas.mc.nikoapi.player.APIPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Listener to watch for clicked and closed inventories.
 *
 * @author Niko
 * @since 0.0.1
 */
public class InventoryListener implements Listener {

	/**
	 * Event gets fired whenever an inventory was clicked.
	 *
	 * @param event InventoryClickEvent - the event.
	 * @since 0.0.1
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getSlot() < 0 || event.getRawSlot() < 0 || event.getSlot() != event.getRawSlot())
			return;

		APIPlayer apiPlayer = NikoAPI.getInstance().getPlayerHandler().getPlayer((Player) event.getWhoClicked());
		CustomInventory customInventory = (CustomInventory) apiPlayer.getCustomData(APIPlayer.CD_OPENINV);
		if (customInventory == null)
			return;

		if (!event.getInventory().equals(customInventory.getInventory()))
			return;

		event.setCancelled(true);

		int clickedSlot = event.getSlot();
		ClickAction clickAction = customInventory.getClickAction(clickedSlot);
		if (clickAction == null)
			return;

		if (event.isRightClick()) {
			if (!event.getWhoClicked().getName().startsWith(".") && event.isShiftClick()) {
				clickAction.onShiftRightClick(apiPlayer, event.getInventory().getItem(clickedSlot), clickedSlot);
			} else {
				clickAction.onRightClick(apiPlayer, event.getInventory().getItem(clickedSlot), clickedSlot);
			}
		} else {
			if (!event.getWhoClicked().getName().startsWith(".") && event.isShiftClick()) {
				clickAction.onShiftClick(apiPlayer, event.getInventory().getItem(clickedSlot), clickedSlot);
			} else {
				clickAction.onClick(apiPlayer, event.getInventory().getItem(clickedSlot), clickedSlot);
			}
		}
		Sound clickSound = clickAction.getClickSound();
		if (clickSound != null)
			apiPlayer.playSound(clickSound);

		if (clickAction.isAutoClose()) {
			apiPlayer.getPlayer().closeInventory();
		}
	}

	/**
	 * Event gets fired whenever an inventory was closed.
	 *
	 * @param event InventoryCloseEvent - the event.
	 * @since 0.0.1
	 */
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		APIPlayer apiPlayer = NikoAPI.getInstance().getPlayerHandler().getPlayer((Player) event.getPlayer());
		if (apiPlayer == null)
			return;
		CustomInventory customInventory = (CustomInventory) apiPlayer.getCustomData(APIPlayer.CD_OPENINV);
		if (customInventory == null)
			return;

		apiPlayer.removeCustomData(APIPlayer.CD_OPENINV);
	}

}
