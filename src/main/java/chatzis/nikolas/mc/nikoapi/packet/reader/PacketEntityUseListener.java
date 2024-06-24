package chatzis.nikolas.mc.nikoapi.packet.reader;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for the PacketPlayInUseEntity listener.
 * @since 0.0.1
 * @author Niko
 */
public interface PacketEntityUseListener extends IAPIListener {

    default List<APIListenerManager.PacketTypes> getListenerType() {
        return List.of(APIListenerManager.PacketTypes.INTERACT);
    }

    /**
     * Will be called, if the player has sent the package.
     * @param player Player - Player the package sent
     * @param packetPlayInUseEntity {@link EntityUsePacket} - Packet that got sent
     */
    void useEntity(Player player, EntityUsePacket packetPlayInUseEntity);

}
