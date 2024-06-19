package chatzis.nikolas.mc.nikoapi.util;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Utils class to provide static methods for various mechanics.
 * @author Nikolas
 * @since 0.0.1
 */
public class Utils {

    private static final String LOGGER_PREFIX = "[Utils] ";
    private static final String PACKAGE_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.").length < 4 ? "v1_20_6" :
            Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    private Utils() {
        // Needed to prevent useless instantiation of this class.
    }

    /**
     * Gets the perfect size for a minecraft inventory.
     * @param minimumSlots Integer - the minimum slots which are needed.
     * @return Integer - the size for the inventory.
     * @since 0.0.1
     */
    public static int getPerfectInventorySize(int minimumSlots) {
        int mod = minimumSlots % 9;
        if (mod == 0)
            return minimumSlots;

        return minimumSlots - mod + 9;
    }

    public static int getChunkId(Location location) {
        return getChunkId(location.getChunk().getX(), location.getChunk().getZ());
    }

    public static int getChunkId(int chunkX, int chunkZ) {
        return chunkX * 30000000 + chunkZ;
    }

    /**
     * Sends the PacketPlayOutEntityDestroy for every given entity.
     * @param player Player - player to send the packet.
     * @param entities List<Entity> - entities to destroy
     * @since 1.0.2
     */
    public static void destroyEntities(Player player, List<Entity> entities) {
        if (entities.isEmpty()) return;
        int[] ids = new int[entities.size()];
        for (int i = 0; i < entities.size(); i++) {
            ids[i] = entities.get(i).getId();
        }
        sendPackets(player, new ClientboundRemoveEntitiesPacket(ids));
    }

    /**
     * Sends the given packet to the player.
     * @param player Player - the player to send the packets.
     * @param packets Object[] - the packets.
     */
    public static void sendPackets(Player player, Object... packets) {
        assert packets instanceof Packet<?>[];
        ServerGamePacketListenerImpl playerConnection = getPlayerConnection(player);
        for (Object packet : packets) {
            playerConnection.send((Packet<?>) packet);
        }
    }

    /**
     * Gets the PlayerConnection of a given player.
     * @param player Player - the player to get the connection from.
     * @return PlayerConnection - the connection.
     */
    public static ServerGamePacketListenerImpl getPlayerConnection(Player player) {
        return getEntityPlayer(player).connection;
    }

    /**
     * Gets the nms class (net.minecraft.server.<version>.classname
     * @param className String - the name of class.
     * @return Class<?> - the class.
     */
    public static Class<?> getNMSClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + PACKAGE_VERSION + "." + className);
        } catch (ClassNotFoundException e) {
            NikoAPI.getInstance().getLogger().warning(LOGGER_PREFIX + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a class from bukkit packages.
     * @param className String - the class name.
     * @return Class<?> - the wanted class or null.
     * @since 0.0.1
     */
    public static Class<?> getBukkitClass(String className) {
        try{
            return Class.forName("org.bukkit.craftbukkit." + PACKAGE_VERSION + "." + className);
        } catch(ClassNotFoundException e) {
            NikoAPI.getInstance().getLogger().warning(LOGGER_PREFIX + e.getMessage());
            return null;
        }
    }

    public static ServerPlayer getEntityPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }


    public static ServerLevel getServerWorld(World world) {
        return ((CraftWorld) world).getHandle();
    }
}
