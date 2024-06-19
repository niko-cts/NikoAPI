package chatzis.nikolas.mc.nikoapi.actionbar;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Manager for action bars
 * @author Niko
 * @since 0.0.1
 */
public class ActionbarManager {

    private boolean runAutomatically;

    private final Map<UUID, ABMessageHolder> staticMessages;
    private final Map<UUID, List<ABMessageHolder>> messages;
    private final Map<UUID, List<ABMessageHolder>> importantMessages;
    private final Set<UUID> currentlyUpdating;

    /**
     * Creates the actionbar manager.
     * @since 0.0.1
     */
    public ActionbarManager() {
        this.staticMessages = new HashMap<>();
        this.messages = new HashMap<>();
        this.importantMessages = new HashMap<>();
        this.currentlyUpdating = new HashSet<>();
        this.runAutomatically = false;
    }

    /**
     * Adds an action bar message to a given uuid.
     * @param uuid UUID - the uuid of the target player.
     * @param message {@link ActionbarMessage} - the message to send.
     * @since 0.0.1
     */
    public void addActionbar(UUID uuid, ActionbarMessage message) {
        ABMessageHolder messageHolder = new ABMessageHolder(message.getMessage(), message.getDuration());
        addActionbar(uuid, message.getType(), messageHolder);
    }

    /**
     * Internal function to add the actionbar message to the maps.
     * @param uuid UUID - the uuid of the target player.
     * @param type {@link ActionbarMessageType} - the type of the action bar message.
     * @param messageHolder {@link ABMessageHolder} - the holder of the message.
     * @since 0.0.1
     */
    private void addActionbar(UUID uuid, ActionbarMessageType type, ABMessageHolder messageHolder) {
        List<ABMessageHolder> messageHolders;
        switch (type) {
            case STATIC -> staticMessages.put(uuid, messageHolder);
            case IMPORTANT -> {
                messageHolders = importantMessages.getOrDefault(uuid, new ArrayList<>());
                if (messageHolders.isEmpty() || !messageHolders.get(messageHolders.size() - 1).equals(messageHolder)) {
                    messageHolders.add(messageHolder);
                    importantMessages.put(uuid, messageHolders);
                    update(Bukkit.getPlayer(uuid));
                }
            }
            default -> {
                messageHolders = messages.getOrDefault(uuid, new ArrayList<>());
                if (messageHolders.isEmpty() || !messageHolders.get(messageHolders.size() - 1).equals(messageHolder)) {
                    messageHolders.add(messageHolder);
                    messages.put(uuid, messageHolders);
                }
            }
        }

        if (!runAutomatically && !this.currentlyUpdating.contains(uuid))
            update(Bukkit.getPlayer(uuid));
    }

    /**
     * Removes all action bars from a given uuid.
     * @param uuid UUID - the player to clear.
     * @since 0.0.1
     */
    public void clearActionbar(UUID uuid) {
        this.staticMessages.remove(uuid);
        this.messages.remove(uuid);
        this.importantMessages.remove(uuid);
        this.currentlyUpdating.remove(uuid);
    }

    /**
     * Stops the scheduler for sending the actionbar.
     * This is needed because an action bar is only sent for a second by default.
     * @since 0.0.1
     */
    public void stop() {
        this.runAutomatically = false;
    }

    /**
     * Starts the scheduler for sending the actionbar.
     * This is needed because an action bar is only sent for a second by default.
     * @since 0.0.1
     */
    public void start() {
        this.runAutomatically = true;
    }

    /**
     * Gets if the action bar manager should run automatically or not.
     * @return boolean - whatever it should run automatically or not.
     * @since 0.0.1
     */
    public boolean isRunningAutomatically() {
        return runAutomatically;
    }

    /**
     * Updates the action bar for all online players
     * Can be triggered manually for game countdowns or just use the start method to run the scheduler.
     * @since 0.0.1
     */
    public void update() {
        Bukkit.getOnlinePlayers().forEach(this::update);
    }

    /**
     * Updates the action bar for a specific player
     * Can be triggered manually for game countdowns or just use the start method to run the scheduler.
     * @since 0.0.1
     */
    public void update(Player onlinePlayer) {
        if(onlinePlayer == null) return;

        String targetMessage = getTargetMessage(onlinePlayer.getUniqueId());

        if (targetMessage != null) {
            sendActionbar(onlinePlayer, targetMessage);
        } else {
            this.currentlyUpdating.remove(onlinePlayer.getUniqueId());
        }
    }

    /**
     * Gets the target message which should be displayed to the uuid.
     * @param uuid UUID - the target player.
     * @return String - the target message.
     * @since 0.0.1
     */
    private String getTargetMessage(UUID uuid) {
        String importantMessage = getImportantMessage(uuid);

        if (importantMessage != null)
            return importantMessage;

        String normalMessage = getNormalMessage(uuid);

        if (normalMessage != null)
            return normalMessage;

        if (staticMessages.containsKey(uuid))
            return staticMessages.get(uuid).getMessage();

        return null;
    }

    /**
     * Gets a normal message which should be displayed to the uuid.
     * @param uuid UUID - the target player.
     * @return String - the normal message.
     * @since 0.0.1
     */
    private String getNormalMessage(UUID uuid) {
        if (!messages.containsKey(uuid) || messages.get(uuid).isEmpty())
            return null;

        return checkList(messages.get(uuid));
    }

    /**
     * Gets an important message which should be displayed to the uuid.
     * @param uuid UUID - the target player.
     * @return String - the normal message.
     * @since 0.0.1
     */
    private String getImportantMessage(UUID uuid) {
        if (!importantMessages.containsKey(uuid) || importantMessages.get(uuid).isEmpty())
            return null;

        return checkList(importantMessages.get(uuid));
    }

    /**
     * Checks a message list and gets the next entry which should be displayed.
     * @param list List<{@link ABMessageHolder}> - the list of messages.
     * @return String - the target message.
     * @since 0.0.1
     */
    private String checkList(List<ABMessageHolder> list) {
        ABMessageHolder holder = list.get(0);

        if (holder.getDuration() == 1) {
            list.remove(holder);
            return holder.getMessage();
        }

        if (!holder.hasEnded()) {
            holder.increaseTimer();
            return holder.getMessage();
        } else {
            list.remove(holder);
            return list.isEmpty() ? null : list.get(0).getMessage();
        }
    }

    /**
     * Sends an action bar to a given player with a given message.
     * @param player Player - the player to send to.
     * @param message String - the message to send.
     * @since 0.0.1
     */
    private void sendActionbar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message, ChatColor.GRAY));

        this.currentlyUpdating.add(player.getUniqueId());
        if (!isRunningAutomatically()) {
            Bukkit.getScheduler().runTaskLater(NikoAPI.getInstance(), () -> {
                if (!isRunningAutomatically())
                    update(player);
            }, 20L);
        }
    }
}
