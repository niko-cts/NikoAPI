package chatzis.nikolas.mc.nikoapi.inventory;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import chatzis.nikolas.mc.nikoapi.player.APIPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Class to create custom inventories with click actions for items.
 *
 * @author Niko
 * @since 0.0.1
 */
public class CustomInventory {

	private final Inventory inventory;
	private final Map<Integer, ClickAction> clickActions;
	private final String title;
	private Object specialHolder;

	/**
	 * Instantiates and copies the old inventory contents
	 *
	 * @param old CustomInventory - the old inventory to copy
	 * @since 1.0.1
	 */
	public CustomInventory(CustomInventory old) {
		this(old, old.getInventory().getSize());
	}

	/**
	 * Instantiates and copies the old inventory contents
	 *
	 * @param old     CustomInventory - the old inventory to copy
	 * @param newSize int - the new size of the inventory
	 * @since 1.0.1
	 */
	public CustomInventory(CustomInventory old, int newSize) {
		this(old.title, newSize);
		setSpecialHolder(old.getSpecialHolder());
		for (int i = 0; i < old.inventory.getContents().length && i < newSize; i++) {
			setItem(i, old.inventory.getItem(i), old.getClickAction(i));
		}
	}

	/**
	 * Creates an instance of the custom inventory.
	 *
	 * @param title String - the title to show.
	 * @param size  int - the inventory size.
	 * @since 0.0.1
	 */
	public CustomInventory(String title, int size) {
		this.inventory = Bukkit.createInventory(null, size, title);
		this.title = title;
		this.clickActions = new HashMap<>();
	}

	/**
	 * Creates an instance of the custom inventory.
	 *
	 * @param inventoryType InventoryType - the type of the inventory.
	 * @param title         String - the title to show.
	 * @since 0.0.1
	 */
	public CustomInventory(InventoryType inventoryType, String title) {
		this.inventory = Bukkit.createInventory(null, inventoryType, title);
		this.title = title;
		this.clickActions = new HashMap<>();
	}

	/**
	 * Creates an instance of the custom inventory.
	 *
	 * @param inventoryType InventoryType - the type of the inventory.
	 * @since 0.0.1
	 */
	public CustomInventory(InventoryType inventoryType) {
		this.inventory = Bukkit.createInventory(null, inventoryType);
		this.title = null;
		this.clickActions = new HashMap<>();
	}

	/**
	 * Creates an instance of the custom inventory.
	 *
	 * @param size int - the size of the inventory.
	 * @since 0.0.1
	 */
	public CustomInventory(int size) {
		this.inventory = Bukkit.createInventory(null, size);
		this.title = null;
		this.clickActions = new HashMap<>();
	}

	/**
	 * Adds an item to the inventory.
	 *
	 * @param itemStack ItemStack - the item to add.
	 * @since 0.0.1
	 */
	public void addItem(ItemStack itemStack) {
		this.inventory.addItem(itemStack);
	}

	/**
	 * Adds an item with click action to the inventory.
	 *
	 * @param itemStack   ItemStack - the item to add.
	 * @param clickAction {@link ClickAction} - the action on clicking the item.
	 * @since 0.0.1
	 */
	public void addItem(ItemStack itemStack, ClickAction clickAction) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
				setItem(i, itemStack, clickAction);
				break;
			}
		}
	}

	/**
	 * Sets an item with a click action to a specific slot.
	 *
	 * @param slot        int - the slot to set the item to.
	 * @param itemStack   ItemStack - the item to set.
	 * @param clickAction {@link ClickAction} - the action on clicking the item.
	 * @since 0.0.1
	 */
	public void setItem(int slot, ItemStack itemStack, ClickAction clickAction) {
		this.inventory.setItem(slot, itemStack);

		if (clickAction != null)
			setClickAction(slot, clickAction);
	}

	/**
	 * Sets an item to a specific slot.
	 *
	 * @param slot      int - the slot to set the item to.
	 * @param itemStack ItemStack - the item to set.
	 * @since 0.0.1
	 */
	public void setItem(int slot, ItemStack itemStack) {
		setItem(slot, itemStack, null);
	}

	/**
	 * Sets the click action for a specific slot.
	 *
	 * @param slot        int - the slot to set the click action for.
	 * @param clickAction {@link ClickAction} - the click action itself.
	 * @since 0.0.1
	 */
	public void setClickAction(int slot, ClickAction clickAction) {
		this.clickActions.put(slot, clickAction);
	}

	/**
	 * Fills all empty slots with a given item.
	 *
	 * @param itemStack ItemStack - the item to fill empty slots.
	 * @since 0.0.1
	 */
	public void fill(ItemStack itemStack) {
		for (int slot = 0; slot < inventory.getSize(); slot++) {
			if (inventory.getItem(slot) != null && inventory.getItem(slot).getType() != Material.AIR)
				continue;

			this.inventory.setItem(slot, itemStack);
		}
	}

	/**
	 * Gets the click action for a given slot.
	 *
	 * @param slot int - the slot to get the action for.
	 * @return {@link ClickAction} - the click action.
	 * @since 0.0.1
	 */
	public ClickAction getClickAction(int slot) {
		return clickActions.get(slot);
	}

	/**
	 * Gets the bukkit inventory stored in the custom inventory.
	 *
	 * @return Inventory - the bukkit inventory.
	 * @since 0.0.1
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Opens the inventory for a specific player.
	 *
	 * @param apiPlayer {@link APIPlayer} - the player to open the inventory for.
	 * @since 0.0.1
	 */
	public void open(APIPlayer apiPlayer) {
		//if (Utils.isOver12() && !apiPlayer.getPlayer().getInventory().getViewers().isEmpty())
		//   apiPlayer.getPlayer().closeInventory();

		try {
			CustomInventory displayInv = this;
			if (apiPlayer.getPlayer().getName().startsWith(".")) {
				displayInv = new CustomInventory(this);
				for (int i = 0; i < inventory.getSize(); i++) {
					if (displayInv.getInventory().getItem(i) != null && displayInv.getInventory().getItem(i).getType().name().contains("STAINED_GLASS_PANE"))
						displayInv.setItem(i, new ItemStack(Material.AIR));
				}
			}

			apiPlayer.getPlayer().openInventory(displayInv.inventory);
			apiPlayer.setCustomData(APIPlayer.CD_OPENINV, displayInv);
		} catch (RuntimeException exception) {
			apiPlayer.getPlayer().closeInventory();
			NikoAPI.getInstance().getLogger().log(Level.WARNING, "Error opening a inventory: {0}", exception.getMessage());
		}
	}

	/**
	 * Set an object to the holder cache.
	 *
	 * @param specialHolder Object - the new object.
	 * @since 0.0.1
	 */
	public void setSpecialHolder(Object specialHolder) {
		this.specialHolder = specialHolder;
	}

	/**
	 * Get the cached object.
	 *
	 * @return Object - the object.
	 * @since 0.0.1
	 */
	public Object getSpecialHolder() {
		return specialHolder;
	}

	/**
	 * The title of the inventory
	 *
	 * @return String - the inventory title
	 * @since 1.0.2
	 */
	public String getTitle() {
		return title;
	}
}
