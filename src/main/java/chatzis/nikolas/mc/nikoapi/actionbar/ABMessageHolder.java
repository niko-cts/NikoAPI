package chatzis.nikolas.mc.nikoapi.actionbar;

import java.util.Objects;

/**
 * Class to hold a translated message for the action bar
 * @author HeyImDome
 * @since 0.0.1
 */
public class ABMessageHolder {

    private final String message;
    private final int duration;
    private int timer;

    /**
     * Creating an instance of the ABMessageHolder.
     * @param message String - the message to send.
     * @param duration int - the duration it should stay.
     * @since 0.0.1
     */
    public ABMessageHolder(String message, int duration) {
        this.message = message;
        this.duration = duration;
        this.timer = 0;
    }

    /**
     * Gets the message saved in the message holder.
     * @return String - the message.
     * @since 0.0.1
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the duration the message should stay.
     * @return int - the duration.
     * @since 0.0.1
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the current timer of the message.
     * @return int - the timer.
     * @since 0.0.1
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Increases the timer by 1.
     * @since 0.0.1
     */
    public void increaseTimer() {
        this.timer = this.timer + 1;
    }

    /**
     * Gets if the timer has run as long as the duration should be.
     * @return boolean - whatever the message been displayed long enough.
     * @since 0.0.1
     */
    public boolean hasEnded() {
        return this.timer >= this.duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ABMessageHolder that = (ABMessageHolder) o;
        return duration == that.duration && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, duration);
    }
}
