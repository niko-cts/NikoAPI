package chatzis.nikolas.mc.nikoapi.packet.reader;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for the PacketPlayOutUnloadChunk listener.
 * @since 0.0.1
 * @author Niko
 */
public interface PacketChunkUnloadListener extends IAPIListener {

    default List<APIListenerManager.PacketTypes> getListenerType() {
        return List.of(APIListenerManager.PacketTypes.UNLOAD);
    }

    /**
     * Will be called if the player has sent the package.
     * @param player Player - Player the package sent
     * @param chunkId int - the chunk id
     */
    void unloadChunk(Player player, int chunkId);

}
