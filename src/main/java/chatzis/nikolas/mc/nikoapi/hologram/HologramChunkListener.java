package chatzis.nikolas.mc.nikoapi.hologram;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import chatzis.nikolas.mc.nikoapi.packet.reader.APIListenerManager;
import chatzis.nikolas.mc.nikoapi.packet.reader.PacketChunkLoadListener;
import chatzis.nikolas.mc.nikoapi.packet.reader.PacketChunkUnloadListener;
import chatzis.nikolas.mc.nikoapi.player.APIPlayer;
import chatzis.nikolas.mc.nikoapi.util.Utils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Class loads and unloads player holograms, when switching chunks.
 * @author Niko
 * @since 0.0.1
 */
public class HologramChunkListener implements PacketChunkLoadListener, PacketChunkUnloadListener {

    @Override
    public List<APIListenerManager.PacketTypes> getListenerType() {
        return Arrays.asList(APIListenerManager.PacketTypes.LOAD, APIListenerManager.PacketTypes.UNLOAD);
    }

    /**
     * Will be called if the player has sent the package.
     * @param player                Player - Player the package sent
     * @param chunkID int - the chunk id
     *
     */
    @Override
    public void loadChunk(Player player, int chunkID) {
        APIPlayer apiPlayer = NikoAPI.getInstance().getPlayerHandler().getPlayer(player);
        if (apiPlayer == null)
            return;

        apiPlayer.getShownHolograms().computeIfPresent(chunkID, (integer, apiHolograms) -> {
            for (APIHologram apiHologram : apiHolograms) {
                if (player.getWorld().equals(apiHologram.getLocation().getWorld())) {
                    for (ArmorStand armorStand : apiHologram.getArmorStands()) {
                        Utils.sendPackets(player, new ClientboundAddEntityPacket(armorStand),
                                new ClientboundSetEntityDataPacket(armorStand.getId(), armorStand.getEntityData().getNonDefaultValues()));
                    }
                }
            }
            return apiHolograms;
        });
    }

    /**
     * Will be called if the player has sent the package.
     * @param player Player - Player the package sent
     * @param chunkId int - the chunk id
     */
    @Override
    public void unloadChunk(Player player, int chunkId) {
        APIPlayer apiPlayer = NikoAPI.getInstance().getPlayerHandler().getPlayer(player);
        if (apiPlayer == null)
            return;

        apiPlayer.getShownHolograms().computeIfPresent(chunkId, (integer, apiHolograms) -> {
            List<Entity> destroyStands = new ArrayList<>();
            for (APIHologram apiHologram : apiHolograms) {
                if (player.getWorld().equals(apiHologram.getLocation().getWorld())) {
                    destroyStands.addAll(apiHologram.getArmorStands());
                }
            }
            Utils.destroyEntities(player, destroyStands);
            return apiHolograms;
        });
    }
}
