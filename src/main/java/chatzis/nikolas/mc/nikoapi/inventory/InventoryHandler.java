package chatzis.nikolas.mc.nikoapi.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler to manage inventories which can be saved manually.
 * Useful for inventories in different languages
 * or for inventories which could be updated.
 * @author HeyImDome
 * @since 0.0.1
 */
public class InventoryHandler {

    private final Map<String, CustomInventory> inventories;

    /**
     * Creates the instance of the inventory handler.
     * @since 0.0.1
     */
    public InventoryHandler() {
        this.inventories = new HashMap<>();
    }

    /**
     * Gets the map of all available custom inventories.
     * @see CustomInventory
     * @return Map<String, CustomInventory> - the map of inventories.
     * @since 0.0.1
     */
    public Map<String, CustomInventory> getInventories() {
        return inventories;
    }

    /**
     * Gets an inventory by a specific name.
     * @param name String - the inventory name.
     * @return {@link CustomInventory} - the custom inventory.
     * @since 0.0.1
     */
    public CustomInventory getInventory(String name) {
        return inventories.get(name);
    }

    /**
     * Adds a custom inventory with a given name.
     * @param name String - the name of the inventory.
     * @param inventory {@link CustomInventory} - the custom inventory.
     * @since 0.0.1
     */
    public void addInventory(String name, CustomInventory inventory) {
        inventories.put(name, inventory);
    }

    /**
     * Removes a custom inventory from the handler.
     * @param name String - the name of the inventory.
     * @since 0.0.1
     */
    public void removeInventory(String name) {
        inventories.remove(name);
    }

}
