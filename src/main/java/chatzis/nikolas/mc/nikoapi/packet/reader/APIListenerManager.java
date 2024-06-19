package chatzis.nikolas.mc.nikoapi.packet.reader;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import chatzis.nikolas.mc.nikoapi.player.PlayerPacketReader;
import chatzis.nikolas.mc.nikoapi.util.ReflectionHelper;
import chatzis.nikolas.mc.nikoapi.util.Utils;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.InteractionHand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.*;
import java.util.logging.Level;

/**
 * The APIListenerManager class holds all custom listeners for packages.
 * If one of the following packages got triggered by a player,
 * the listener's method (event) will be called.
 * The Plugin is able to return true to cancel the package.
 * If you want to unregister the listener call unregister(listener)
 * <p>
 * Available PackageListener:
 * {@link PacketChunkLoadListener}, {@link PacketChunkUnloadListener} and {@link PacketEntityUseListener}.
 *
 * @author Niko
 * @since 0.0.1
 */
public class APIListenerManager {

    private static APIListenerManager instance;

    /**
     * Get the instance of this class
     *
     * @return APIListenerManager - A instance of {@link APIListenerManager}
     */
    public static APIListenerManager getInstance() {
        if (instance == null)
            instance = new APIListenerManager();
        return instance;
    }

    private final Map<PacketTypes, List<IAPIListener>> packetListener;

    /**
     * Instantiate the ArrayLists of the Listeners
     */
    private APIListenerManager() {
        this.packetListener = new EnumMap<>(PacketTypes.class);
    }

    /**
     * Register a listener.
     * Register either a {@link PacketChunkLoadListener},
     * {@link PacketChunkUnloadListener} or a {@link PacketEntityUseListener}.
     * The event method in it will be called, when the package has been triggered.
     *
     * @param listener IAPIListener - The listener to register
     */
    public void registerListener(IAPIListener listener) {
        for (PacketTypes type : listener.getListenerType()) {
            List<IAPIListener> listeners = this.packetListener.getOrDefault(type, new ArrayList<>());
            listeners.add(listener);
            this.packetListener.put(type, listeners);
        }

        if (!PlayerPacketReader.isEnabled())
            PlayerPacketReader.enable();
    }

    /**
     * Unregister a listener.
     * This listener won't be called, when Packet is triggered
     *
     * @param listener IAPIListener - Listener to remove
     */
    public void unRegisterListener(IAPIListener listener) {
        for (PacketTypes type : listener.getListenerType()) {
            if (!this.packetListener.containsKey(type)) continue;
            List<IAPIListener> listeners = this.packetListener.get(type);
            listeners.remove(listener);
            if (listeners.isEmpty())
                this.packetListener.remove(type);
            else
                this.packetListener.put(type, listeners);
        }
    }

    /**
     * Event gets triggered, when a player
     *
     * @param player Player - player that triggered the event
     * @param packet Object - Packet that got triggered
     */
    public void packetWrite(Player player, Object packet) {
        if (!(packet instanceof ClientboundLevelChunkWithLightPacket || packet instanceof ClientboundForgetLevelChunkPacket))
            return;
        PacketTypes type = PacketTypes.getByPacketClass(packet);
        if (type == null || !packetListener.containsKey(type)) return;

        for (IAPIListener listener : packetListener.get(type)) {
            if (listener instanceof PacketChunkLoadListener && packet instanceof ClientboundLevelChunkWithLightPacket chunkPacket) {
                try {
                    ((PacketChunkLoadListener) listener).loadChunk(player, Utils.getChunkId(chunkPacket.getX(), chunkPacket.getZ()));
                } catch (Exception exception) {
                    NikoAPI.getInstance().getLogger().log(Level.WARNING, "Error while executing chunk load listener for {0} with {1}: {2}",
                            new String[]{packet.getClass().getSimpleName(), exception.getClass().getSimpleName(), exception.getMessage()});
                }
            } else if (listener instanceof PacketChunkUnloadListener && packet instanceof ClientboundForgetLevelChunkPacket chunkPacket) {
                try {
                    ((PacketChunkUnloadListener) listener).unloadChunk(player, Utils.getChunkId(chunkPacket.getX(), chunkPacket.getZ()));
                } catch (Exception exception) {
                    NikoAPI.getInstance().getLogger().log(Level.WARNING, "Error while executing chunk un load listener for {0} with {1}: {2}",
                            new String[]{packet.getClass().getSimpleName(), exception.getClass().getSimpleName(), exception.getMessage()});
                }
            }
        }
    }

    public void packetRead(Player player, Object packet) {
        if (!(packet instanceof ServerboundInteractPacket interactPacket))
            return;
        PacketTypes type = PacketTypes.getByPacketClass(packet);
        if (type == null || !packetListener.containsKey(type)) return;

        if (type == PacketTypes.INTERACT) {
            String action;

            Object actionInterfaceInstance = ReflectionHelper.get(ServerboundInteractPacket.class, packet, "c");

            if (actionInterfaceInstance == null) {
                NikoAPI.getInstance().getLogger().warning("Could not get the action of the InteractionPacket");
                return;
            }

            Object actionObject = ReflectionHelper.getObjectThroughMethod(actionInterfaceInstance.getClass(), "a", actionInterfaceInstance);
            action = actionObject == null ? "INTERACT" : actionObject.toString();

            // Action is interact_at that is called with interacting
            if (action.equalsIgnoreCase("c") || action.equalsIgnoreCase("interact_at"))
                return;

            Integer entityId = ReflectionHelper.get(interactPacket, "b");
            if (entityId == null) {
                NikoAPI.getInstance().getLogger().warning("Could not determine the entityId for NPCInteractPacket");
                return;
            }
            boolean shift = interactPacket.isUsingSecondaryAction();

            ClickType clickType = switch (action.toLowerCase()) {
                case "a", "attack" -> shift ? ClickType.SHIFT_LEFT: ClickType.RIGHT;
                case "b", "interact" -> shift ? ClickType.SHIFT_RIGHT : ClickType.RIGHT;
                default -> {
                    NikoAPI.getInstance().getLogger().warning("Could not find action type for NPCPacket: " + action);
                    yield ClickType.RIGHT;
                }
            };

            if (clickType.isRightClick()) {
                InteractionHand hand = ReflectionHelper.get(actionInterfaceInstance, "a");
                if (hand == InteractionHand.OFF_HAND)
                    return;
            }

            for (IAPIListener iapiListener : packetListener.get(type)) {
                if (!(iapiListener instanceof PacketEntityUseListener))
                    continue;

                try {
                    ((PacketEntityUseListener) iapiListener).useEntity(player, new EntityUsePacket(entityId, clickType));
                } catch (Exception exception) {
                    NikoAPI.getInstance().getLogger().log(Level.WARNING, "Error while executing interact listener for {0} with {1}: {2}",
                            new String[]{packet.getClass().getSimpleName(), exception.getClass().getSimpleName(), exception.getMessage()});
                }
            }
        }
    }

    public enum PacketTypes {
        LOAD(ClientboundLevelChunkWithLightPacket.class),
        UNLOAD(ClientboundForgetLevelChunkPacket.class),
        INTERACT(ServerboundInteractPacket.class);

        private final Class<?> packetClass;

        PacketTypes(Class<?> packetClass) {
            this.packetClass = packetClass;
        }

        public static PacketTypes getByPacketClass(Object packet) {
            return Arrays.stream(values()).filter(p -> p.packetClass.getSimpleName().equals(packet.getClass().getSimpleName())).findFirst().orElse(null);
        }
    }

}
