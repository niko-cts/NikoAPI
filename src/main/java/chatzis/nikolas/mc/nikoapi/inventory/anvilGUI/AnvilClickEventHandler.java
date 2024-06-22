package chatzis.nikolas.mc.nikoapi.inventory.anvilGUI;

/**
 * Interface for the {@link AnvilGUI}
 * @author Niko
 * @since 0.0.1
 */
public interface AnvilClickEventHandler {

    /**
     * Method will be called,
     * when a player interacts with the AnvilGUI.
     * @param event AnvilClickEvent - Event that was triggered
     * @since 0.0.1
     */
    void onAnvilClick(AnvilClickEvent event);
}
