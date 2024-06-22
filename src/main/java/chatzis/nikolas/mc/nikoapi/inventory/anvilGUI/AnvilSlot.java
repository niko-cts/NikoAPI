package chatzis.nikolas.mc.nikoapi.inventory.anvilGUI;

import java.util.Arrays;

/**
 * AnvilSlot is used for the {@link AnvilGUI}
 * to declare, where an item should be displayed and where the player clicked.
 * @author Niko
 * @since 0.0.1
 */
public enum AnvilSlot {

    INPUT_LEFT(0),
    INPUT_RIGHT(1),
    OUTPUT(2);

    private final int slot;

    /**
     * Instantiate with the slot as an integer
     * @param slot int - slot
     * @since 0.0.1
     */
    AnvilSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Get the slot as an integer
     * @return int - slot
     * @since 0.0.1
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Convert the integer slot to the AnvilSlot enum
     * @param slot int - slot to convert
     * @return AnvilSlot - Converted integer slot
     * @since 0.0.1
     */
    public static AnvilSlot bySlot(int slot) {
        return Arrays.stream(values()).filter(a -> a.getSlot() == slot).findFirst().orElse(null);
    }
}
