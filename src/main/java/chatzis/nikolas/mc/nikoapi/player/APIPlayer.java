package chatzis.nikolas.mc.nikoapi.player;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import chatzis.nikolas.mc.nikoapi.hologram.APIHologram;
import chatzis.nikolas.mc.nikoapi.util.LocationUtil;
import chatzis.nikolas.mc.nikoapi.util.Utils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class APIPlayer {

	public static final String CD_OPENINV = "openInv";


	private final Player player;
	private final Map<String, Object> customData;
	private final Map<Integer, List<APIHologram>> holograms;

	/**
	 * Creates an instance of the api player for a given player.
	 *
	 * @param player Player - the target player.
	 * @since 0.0.1
	 */
	public APIPlayer(Player player) {
		this.player = player;
		this.customData = new HashMap<>();
		this.holograms = new HashMap<>();
	}


	public void playSound(Sound sound) {
		player.playSound(player.getLocation(), sound, 5, 1);
	}

	public APIHologram showHologram(Location location, String... text) {
		APIHologram apiHologram = new APIHologram(location, Arrays.asList(text));
		showHologram(apiHologram);
		return apiHologram;
	}

	public APIHologram showHologram(Location location, int seconds, String... text) {
		APIHologram hologram = showHologram(location, text);
		Bukkit.getScheduler().runTaskLater(NikoAPI.getInstance(), () -> hideHologram(hologram), 20L * seconds);
		return hologram;
	}

	/**
	 * Shows a hologram to the player.
	 *
	 * @param apiHologram {@link APIHologram} - the hologram to show.
	 * @since 0.0.1
	 */
	public void showHologram(APIHologram apiHologram) {
		this.holograms.compute(Utils.getChunkId(apiHologram.getLocation()),
				(integer, apiHolograms) -> {
					if (apiHolograms == null) {
						apiHolograms = new ArrayList<>();
					}
					apiHolograms.add(apiHologram);
					return apiHolograms;
				});
		if (player.getWorld().equals(apiHologram.getLocation().getWorld())) {
			for (ArmorStand entityArmorStand : apiHologram.getArmorStands()) {
				sendPacket(new ClientboundAddEntityPacket(entityArmorStand),
						new ClientboundSetEntityDataPacket(entityArmorStand.getId(), entityArmorStand.getEntityData().getNonDefaultValues()));
			}
		}
	}

	/**
	 * Shows a hologram for a certain time.
	 *
	 * @param apiHologram {@link APIHologram} - the hologram.
	 * @param seconds     int - the duration in seconds.
	 * @since 0.0.1
	 */
	public void showHologram(APIHologram apiHologram, int seconds) {
		showHologram(apiHologram);
		Bukkit.getScheduler().runTaskLater(NikoAPI.getInstance(), () -> hideHologram(apiHologram), 20L * seconds);
	}

	/**
	 * Hides a hologram from a player.
	 *
	 * @param apiHologram {@link APIHologram} - the hologram to hide.
	 * @since 0.0.1
	 */
	public void hideHologram(APIHologram apiHologram) {
		hideHolograms(apiHologram.getLocation());
	}

	/**
	 * Hides holograms from a player on a specific location.
	 *
	 * @param location Location - the location where the holograms are located.
	 * @since 0.0.1
	 */
	public void hideHolograms(Location location) {
		holograms.computeIfPresent(Utils.getChunkId(location), (integer, holo) -> {
			Utils.destroyEntities(player, holo.stream()
					.filter(apiHologram -> LocationUtil.equalsLocation(location, apiHologram.getLocation()))
					.flatMap(apiHologram -> {
						holo.remove(apiHologram);
						return apiHologram.getArmorStands().stream().map(h -> (Entity) h);
					}).toList());
			return holo;
		});
	}

	/**
	 * Hides all holograms which the player can see.
	 *
	 * @since 0.0.1
	 */
	public void hideHolograms() {
		List<Entity> armorStands = getShownHolograms().values().stream().flatMap(apiHolograms -> apiHolograms.stream()
				.flatMap(h -> h.getArmorStands().stream().map(armorStand -> (Entity) armorStand))).toList();
		holograms.clear();
		Utils.destroyEntities(player, armorStands);
	}

	/**
	 * Gets all holograms which are at a specific location.
	 *
	 * @param location Location - the location where the holograms are located.
	 * @return List of {@link APIHologram} - the holograms.
	 * @since 0.0.1
	 */
	public List<APIHologram> getHolograms(Location location) {
		return getShownHolograms().getOrDefault(Utils.getChunkId(location), new ArrayList<>())
				.stream().filter(holo -> LocationUtil.equalsLocation(location, holo.getLocation())).collect(Collectors.toList());
	}


	/**
	 * Gets all the holograms that the player can see.
	 *
	 * @return Map of {@link APIHologram}
	 * @since 0.0.1
	 */
	public Map<Integer, List<APIHologram>> getShownHolograms() {
		return new HashMap<>(holograms);
	}

	/**
	 * Sends the given packets to the player.
	 *
	 * @param packets Packet[] - the nms packets.
	 * @since 1.0.2
	 */
	public void sendPacket(Object... packets) {
		for (Object packet : packets) {
			sendPacket(packet);
		}
	}

	/**
	 * Sends the given packet to the player.
	 *
	 * @param packet Packet - the nms packet.
	 * @since 1.0.2
	 */
	public void sendPacket(Object packet) {
		Utils.sendPackets(player, packet);
	}

	public UUID getUniqueId() {
		return getPlayer().getUniqueId();
	}

	/**
	 * Sets and stores player dependent data.
	 *
	 * @param name String - the name of the data.
	 * @param data Object - the object to store.
	 * @since 0.0.1
	 */
	public void setCustomData(String name, Object data) {
		this.customData.put(name, data);
	}

	/**
	 * Gets data that is stored in the player.
	 *
	 * @param name String - the name of the data.
	 * @return Object - the data itself.
	 * @since 0.0.1
	 */
	public Object getCustomData(String name) {
		return this.customData.get(name);
	}

	/**
	 * Removes data from the player.
	 *
	 * @param name String - the name of the data.
	 * @since 0.0.1
	 */
	public void removeCustomData(String name) {
		this.customData.remove(name);
	}

	/**
	 * Gets if the player has a specific data stored.
	 *
	 * @param name String - the name of the data.
	 * @return boolean - whatever the data is stored.
	 * @since 0.0.1
	 */
	public boolean hasCustomData(String name) {
		return this.customData.containsKey(name);
	}

	/**
	 * Opens a book for the player.
	 *
	 * @param bookItem ItemStack - the book item to open.
	 * @since 0.0.1
	 */
	public void openBook(ItemStack bookItem) {
		int slot = getPlayer().getInventory().getHeldItemSlot();
		ItemStack heldItem = getPlayer().getInventory().getItem(slot);
		getPlayer().getInventory().setItem(slot, bookItem);
		Utils.sendPackets(player, new ClientboundOpenBookPacket(InteractionHand.MAIN_HAND));
		getPlayer().getInventory().setItem(slot, heldItem);
	}

	public Player getPlayer() {
		return player;
	}
}
