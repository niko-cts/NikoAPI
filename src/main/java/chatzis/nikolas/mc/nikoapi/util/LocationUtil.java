package chatzis.nikolas.mc.nikoapi.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for working with locations.
 *
 * @author HeyImDome
 * @since 0.0.1
 */
public class LocationUtil {

    private static final Set<Material> BLACKLIST = getBlacklist();

    private LocationUtil() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    /**
     * Compares two location based on their doubles
     * @param loc1 Location - Location one
     * @param loc2 Location - Location two
     * @return boolean - are the same
     */
    public static boolean equalsLocation(Location loc1, Location loc2) {
        if(loc1 == null || loc2 == null)
            return false;
        return loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY() && loc1.getZ() == loc2.getZ();
    }

    /**
     * Compares two location based on their blocks
     * @param loc1 Location - Location one
     * @param loc2 Location - Location two
     * @return boolean - are the same
     */
    public static boolean equalsLocationBlock(Location loc1, Location loc2) {
        if(loc1 == null || loc2 == null)
            return false;
        return loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
    }


    /**
     * checks if the current Location is between min and max Location
     * (Compares in int)
     * @param minimum Location
     * @param current Location
     * @param maximum Location
     * @return true if current is between min and max
     */
    public static boolean isBetween(Location minimum, Location current, Location maximum) {
        if(minimum == null || current == null || maximum == null)
            return false;
        if (minimum.getWorld() != current.getWorld() || maximum.getWorld() != current.getWorld() || maximum.getWorld() != minimum.getWorld())
            return false;

        return current.getBlockX() >= minimum.getBlockX() && current.getBlockX() <= maximum.getBlockX() &&
                current.getBlockY() >= minimum.getBlockY() && current.getBlockY() <= maximum.getBlockY() &&
                current.getBlockZ() >= minimum.getBlockZ() && current.getBlockZ() <= maximum.getBlockZ();
    }


    /**
     * Returns an array, where [0] is the minimum coordinate and [1] the maximum of both locations
     * @param location1 Location - Location one
     * @param location2 Location - Location two
     * @return Location[] returns null if any loc is null
     */
    public static Location[] getMinAndMax(Location location1, Location location2) {
        if (location1 == null || location2 == null)
            return new Location[2];
        return new Location[]{
                new Location(location1.getWorld(), Math.min(location1.getX(), location2.getX()), Math.min(location1.getY(), location2.getY()), Math.min(location1.getZ(), location2.getZ())),
                new Location(location2.getWorld(), Math.max(location1.getX(), location2.getX()), Math.max(location1.getY(), location2.getY()), Math.max(location1.getZ(), location2.getZ()))};
    }


    /**
     * Returns a list of material representing the blacklist.
     * @return Set<Material> - the materials.
     * @since 1.0.1
     */
    private static Set<Material> getBlacklist() {
        return new HashSet<>(Arrays.asList(Material.OAK_LOG, Material.SPRUCE_LOG, Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG,
                Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES, Material.JUNGLE_LEAVES, Material.OAK_LEAVES,
                Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES,
                Material.TALL_GRASS, Material.WHEAT_SEEDS,
                Material.BARRIER, Material.BEETROOT_SEEDS,
                Material.MELON_SEEDS, Material.PUMPKIN_SEEDS,
                Material.AIR, Material.FLOWER_POT, Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.JUNGLE_FENCE, Material.WHEAT,
                Material.CHORUS_FLOWER, Material.DARK_OAK_FENCE_GATE, Material.BIRCH_FENCE_GATE));
    }

    /**
     * Filters blocks that are blacklisted and calculates the highest y coordinate.
     * Filters are for example flowers, seeds, leaves, logs etc.
     * @param world World - World of looked location
     * @param x     int - x coordinate
     * @param z     int - z coordinate
     * @return int - The y coordinate
     * @since 0.0.1
     */
    public static int getBlockHeight(World world, int x, int z) {
        return getBlockHeight(new Location(world, x, 0, z));
    }

    /**
     * Filters blocks that are blacklisted and calculates the highest y coordinate.
     * Filters are for example flowers, seeds, leaves, logs etc.
     * @param location Location - The location to get the y coordinate from
     * @return int - The y coordinate
     * @since 0.0.1
     */
    public static int getBlockHeight(Location location) {
        location = location.clone();
        location.setY(location.getWorld().getHighestBlockYAt(location));
        while (location.getY() > 0 && BLACKLIST.contains(location.getBlock().getType()))
            location.subtract(0, 1, 0);
        return location.getBlockY();
    }

    /**
     * Get the target block with default blacklist materials.
     * @param player Player - player to get the aiming block
     * @param maxDistance int - maximum distance
     * @return Block - the targetted block
     * @since 0.0.1
     */
    public static Block getTargetBlock(Player player, int maxDistance) {
        return player.getTargetBlock(new HashSet<>(BLACKLIST), maxDistance);
    }

    /**
     * Transfers a location to the String.
     * String will be in this format: blockX blockY blockZ
     * @param location Location to transfer
     * @return String - Transferred String
     * @since 0.0.1
     */
    public static String locationToString(Location location) {
        return location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();
    }

    /**
     * Returns the closest Location from an 2D rectangle to the given current.
     * @param minRect Location - minimum coordinate of rectangle
     * @param maxRect Location - maximum coordinate of rectangle
     * @param current Location - given position.
     * @return Location - the closest location of rectangle to the position.
     * @since 1.0.1
     */
    public static Location getClosestLocationFromRectangle(Location minRect, Location maxRect, Location current) {
        return new Location(minRect.getWorld(),
                current.getX() <= minRect.getX() ? minRect.getX() : Math.min(current.getX(), maxRect.getX()),
                current.getY(),
                current.getZ() <= minRect.getZ() ? minRect.getZ() : Math.min(current.getZ(), maxRect.getZ()));
    }
}
