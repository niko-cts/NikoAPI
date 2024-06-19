package chatzis.nikolas.mc.nikoapi.packet.reader;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for the PacketPlayOutMapChunk listener.
 *
 * @author Niko
 * @since 0.0.1
 */
public interface PacketChunkLoadListener extends IAPIListener {

    default List<APIListenerManager.PacketTypes> getListenerType() {
        return List.of(APIListenerManager.PacketTypes.LOAD);
    }

    /**
     * Will be called if the player has sent the package.
     *
     * @param player                Player - Player the package sent
     * @param chunkId int - the chunk id
     */
    void loadChunk(Player player, int chunkId);

}
