package chatzis.nikolas.mc.nikoapi.player;

import chatzis.nikolas.mc.nikoapi.packet.reader.APIListenerManager;
import chatzis.nikolas.mc.nikoapi.util.ReflectionHelper;
import chatzis.nikolas.mc.nikoapi.util.Utils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is for the PacketReader.
 * The class should be instantiated with every player.
 * If APIListenerManager contains a listener,
 * the reader is set to enable.

 */
public class PlayerPacketReader {

    private static final Map<UUID, PlayerPacketReader> READERS = new HashMap<>();
    private static final APIListenerManager MANAGER = APIListenerManager.getInstance();
    private static boolean enabled = false;

    /**
     * Enables the Packet reader.
     * This method gets called, if a packetListener is registered in {@link APIListenerManager}.
     */
    public static void enable() {
        if (enabled) return;
        enabled = true;
        Bukkit.getOnlinePlayers().forEach(PlayerPacketReader::injectIfAbsent);
    }

    /**
     * Check if the PacketReader is enabled
     *
     * @return boolean - enabled packet reader
     */
    public static boolean isEnabled() {
        return enabled;
    }

    public static void injectIfAbsent(Player player) {
        if (!isEnabled())
            throw new IllegalStateException("PacketReader is not enabled!");

        READERS.putIfAbsent(player.getUniqueId(), new PlayerPacketReader(player));
    }

    /**
     * Uninjects the player if registerd.
     *
     * @param player Player - player to uninject
     */
    public static void uninject(Player player) {
        READERS.computeIfPresent(player.getUniqueId(), (uuid, playerPacketReader) -> {
            playerPacketReader.uninject();
            return null;
        });
    }


    private static final Logger LOG = Logger.getLogger(PlayerPacketReader.class.getSimpleName());
    private final Player player;

    /**
     * Instantiate a new PacketReader for a player.
     * Uninject the old one, if exists.
     * Injects the new one.
     * @param player Player - Player to inject the reader
     */
    private PlayerPacketReader(Player player) {
        this.player = player;
        inject();
    }

    /**
     * Injects the player the packet reader
     * Calls the {@link APIListenerManager}, to check if packet has a listener
     */
    private void inject() {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
                MANAGER.packetRead(player, packet);
                super.channelRead(ctx, packet);
            }

            @Override
            public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
                MANAGER.packetWrite(player, packet);
                super.write(ctx, packet, promise);
            }
        };

        try {
            Connection networkManager = getConnection();
            if (networkManager == null ) {
                LOG.warning("Could not cast Connection of player!");
            } else {
                networkManager.channel.pipeline().addBefore("packet_handler", player.getName(), channelDuplexHandler);
                LOG.info("Injected packet reader for " + player.getName());
            }
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            LOG.log(Level.WARNING, "Error while injecting packet reader: {0}", exception.getMessage());
        }
    }

    /**
     * Uninject player the PacketReader
     */
    private void uninject() {
        try {
            Connection networkManager = getConnection();
            if (networkManager == null ) {
                LOG.warning("Could not cast Connection of player!");
            } else {
                networkManager.channel.eventLoop().submit(() -> {
                    networkManager.channel.pipeline().remove(player.getName());
                    return null;
                });
                LOG.info("Ejected packet reader for " + player.getName());
            }
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            LOG.log(Level.WARNING, "Error while uninject packet reader: {0}", exception.getMessage());
        }
    }

    private Connection getConnection() {
        return ReflectionHelper.get(ServerGamePacketListenerImpl.class, Utils.getPlayerConnection(player), "e");
    }

}
