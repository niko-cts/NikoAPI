package chatzis.nikolas.mc.nikoapi.actionbar;

/**
 * Class for creating messages in the action bar
 * @author HeyImDome
 * @since 0.0.1
 */
public class ActionbarMessage {

    private final String message;
    private int duration;
    private ActionbarMessageType type;

    /**
     * Creates a message which can be displayed in the action bar.
     * @param message String - the message.
     */
    public ActionbarMessage(String message) {
        this.message = message;
        this.duration = 2;
        this.type = ActionbarMessageType.IMPORTANT;
    }

    /**
     * Set the duration how long the message should stay in the action bar.
     * @param seconds int - the duration.
     * @return {@link ActionbarMessage} - the current message.
     * @since 0.0.1
     */
    public ActionbarMessage setDuration(int seconds) {
        this.duration = seconds;
        return this;
    }

    /**
     * Gets the current action bar message type.
     * @return {@link ActionbarMessageType}
     * @since 0.0.1
     */
    public ActionbarMessageType getType() {
        return type;
    }

    /**
     * Set the type of the action bar message.
     * @param type {@link ActionbarMessageType} - the type to set.
     * @return {@link ActionbarMessage} - the current message.
     * @since 0.0.1
     */
    public ActionbarMessage setType(ActionbarMessageType type) {
        this.type = type;
        return this;
    }

    /**
     * Gets the message set in the action bar.
     * @return String - the message.
     * @since 0.0.1
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the duration how long the message should stay.
     * @return int - the duration.
     * @since 0.0.1
     */
    public int getDuration() {
        return duration;
    }

}
