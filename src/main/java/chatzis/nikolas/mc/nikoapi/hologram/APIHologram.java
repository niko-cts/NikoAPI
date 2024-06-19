package chatzis.nikolas.mc.nikoapi.hologram;

import chatzis.nikolas.mc.nikoapi.packet.reader.APIListenerManager;
import chatzis.nikolas.mc.nikoapi.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to create holograms.
 * @author HeyImDome
 * @since 0.0.1
 */
public class APIHologram {

    static {
        // static reason: Only inject a hologram listener, when a hologram is being instantiated
        APIListenerManager.getInstance().registerListener(new HologramChunkListener());
    }

    private final Location spawnLocation;
    private final int chunkID;

    private List<String> lines;
    private final List<ArmorStand> armorStands;

    /**
     * Create the hologram object.
     * @param location Location - the location to spawn the hologram.
     * @param lines List<String> - the text to display.
     * @since 0.0.1
     */
    public APIHologram(Location location, List<String> lines) {
        this.spawnLocation = location.clone();
        this.chunkID = Utils.getChunkId(location);
        this.lines = lines;
        this.armorStands = new ArrayList<>();
    }

    /**
     * Gets the location where the hologram will be spawned.
     * @return Location
     * @since 0.0.1
     */
    public Location getLocation() {
        return spawnLocation;
    }

    /**
     * Gets the lines which are displayed by the hologram.
     * @return List<String> - the lines
     * @since 0.0.1
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * Gets the chunk id where the hologram is located.
     * @return int - the chunk id.
     * @since 0.0.1
     */
    public int getChunkID() {
        return chunkID;
    }

    /**
     * Sets the lines which should be displayed by the hologram.
     * @param lines List<String> - the lines to display.
     * @since 0.0.1
     */
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    /**
     * Sets the lines which should be displayed by the hologram.
     * @param lines String[] - the lines to display.
     * @since 0.0.1
     */
    public void setLines(String... lines) {
        setLines(Arrays.asList(lines));
    }

    /**
     * Adds a line to the lines which should be displayed.
     * @param line String - the line to add.
     * @since 0.0.1
     */
    public void addLine(String line) {
        this.lines.add(line);
    }

    /**
     * Gets all armors stands which are stored in the hologram.
     * If a new line was added, they will be re-created before.
     * @return List<EntityArmorStand> - the list of entity armor stands.
     * @since 0.0.1
     */
    public List<ArmorStand> getArmorStands() {
        if (armorStands.size() != lines.size())
            createArmorStands();

        return armorStands;
    }

    /**
     * Returns a list of all entity ids from the armor stand.
     * @return List<Integer> - the entity ids.
     * @since 1.0.2
     */
    public List<Integer> getEntityIds() {
        List<Integer> ids = new ArrayList<>();
        for (ArmorStand armorStand : getArmorStands()) {
            ids.add(armorStand.getId());
        }
        return ids;
    }

    /**
     * Creates the armor stands for a given location with a Y difference of 0.25D for each line.
     * @since 0.0.1
     */
    private void createArmorStands() {
        this.armorStands.clear();

        Location location = this.spawnLocation.clone().subtract(0, 1.975, 0);
        for (int i = lines.size() - 1; i >= 0; i--) {
            String currentLine = lines.get(i);

            System.out.println(currentLine);
            ServerLevel serverWorld = Utils.getServerWorld(location.getWorld());

            ArmorStand entityArmorStand = new ArmorStand(serverWorld, location.getX(), location.getY(), location.getZ());
            entityArmorStand.setInvisible(true); // invisible
            entityArmorStand.setCustomNameVisible(true); // name visible
            entityArmorStand.setCustomName(Component.literal(currentLine)); // current line
            entityArmorStand.setNoGravity(true); // no gravity

            this.armorStands.add(entityArmorStand);
            location.add(0.0D, 0.25D, 0.0D);
        }
    }

}
