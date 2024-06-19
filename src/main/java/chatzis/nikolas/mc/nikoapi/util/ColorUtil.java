package chatzis.nikolas.mc.nikoapi.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;

/**
 * Class to convert different color types.
 * @author Niko
 * @since 0.0.1
 */
public class ColorUtil {

    private ColorUtil() {
        throw new UnsupportedOperationException("This is a util class.");
    }

    /**
     * Convert the given chatColor to a dye color
     * @param chatColor ChatColor - Color to convert
     * @return DyeColor - The converted DyeColor
     */
    public static DyeColor convertChatToDyeColor(ChatColor chatColor) {
        if (chatColor == null)
            return DyeColor.WHITE;

        return switch (chatColor) {
            case AQUA -> DyeColor.CYAN;
            case BLUE, DARK_BLUE -> DyeColor.BLUE;
            case LIGHT_PURPLE -> DyeColor.MAGENTA;
            case DARK_PURPLE -> DyeColor.PURPLE;
            case DARK_GREEN -> DyeColor.GREEN;
            case GREEN -> DyeColor.LIME;
            case YELLOW -> DyeColor.YELLOW;
            case GOLD -> DyeColor.ORANGE;
            case GRAY -> DyeColor.LIGHT_GRAY;
            case DARK_GRAY -> DyeColor.GRAY;
            case RED, DARK_RED -> DyeColor.RED;
            default -> DyeColor.WHITE;
        };
    }

    /**
     * Convert the given DyeColor to a ChatColor
     * @param dyeColor DyeColor - Color to convert
     * @return ChatColor - The converted ChatColor
     */
    public static ChatColor convertDyeToChatColor(DyeColor dyeColor) {
        if (dyeColor == null)
            return ChatColor.WHITE;

        return switch (dyeColor) {
            case CYAN -> ChatColor.AQUA;
            case LIGHT_BLUE -> ChatColor.BLUE;
            case BLUE -> ChatColor.DARK_BLUE;
            case MAGENTA -> ChatColor.LIGHT_PURPLE;
            case PURPLE -> ChatColor.DARK_PURPLE;
            case GREEN -> ChatColor.DARK_GREEN;
            case LIME -> ChatColor.GREEN;
            case YELLOW -> ChatColor.YELLOW;
            case ORANGE -> ChatColor.GOLD;
            case GRAY -> ChatColor.DARK_GRAY;
            case PINK -> ChatColor.RED;
            case RED -> ChatColor.DARK_RED;
            case LIGHT_GRAY -> ChatColor.GRAY;
            default -> ChatColor.WHITE;
        };
    }

    /**
     * Convert the given ChatColor to a Material carpet
     * @param color ChatColor - Color to convert
     * @return Material - The converted carpet with color
     * @since 1.0.2
     */
    public static Material convertChatColorToCarpet(ChatColor color) {
        if (color == null)
            return Material.WHITE_CARPET;
        return switch (color) {
            case BLACK -> Material.BLACK_CARPET;
            case DARK_BLUE -> Material.BLUE_CARPET;
            case DARK_GREEN -> Material.GREEN_CARPET;
            case DARK_AQUA -> Material.CYAN_CARPET;
            case DARK_RED, RED -> Material.RED_CARPET;
            case DARK_PURPLE -> Material.PURPLE_CARPET;
            case GOLD -> Material.ORANGE_CARPET;
            case GRAY -> Material.LIGHT_GRAY_CARPET;
            case DARK_GRAY -> Material.GRAY_CARPET;
            case BLUE, AQUA -> Material.LIGHT_BLUE_CARPET;
            case GREEN -> Material.LIME_CARPET;
            case LIGHT_PURPLE -> Material.MAGENTA_CARPET;
            case YELLOW -> Material.YELLOW_CARPET;
            default -> Material.WHITE_CARPET;
        };
    }
    /**
     * Convert the given ChatColor to a Material carpet
     * @param color ChatColor - Color to convert
     * @return Material - The converted carpet with color
     * @since 1.0.2
     */
    public static Material convertChatColorToBanner(ChatColor color) {
        if (color == null)
            return Material.WHITE_BANNER;
        return switch (color) {
            case BLACK -> Material.BLACK_BANNER;
            case DARK_BLUE -> Material.BLUE_BANNER;
            case DARK_GREEN -> Material.GREEN_BANNER;
            case DARK_AQUA -> Material.CYAN_BANNER;
            case DARK_RED, RED -> Material.RED_BANNER;
            case DARK_PURPLE -> Material.PURPLE_BANNER;
            case GOLD -> Material.ORANGE_BANNER;
            case GRAY -> Material.LIGHT_GRAY_BANNER;
            case DARK_GRAY -> Material.GRAY_BANNER;
            case BLUE, AQUA -> Material.LIGHT_BLUE_BANNER;
            case GREEN -> Material.LIME_BANNER;
            case LIGHT_PURPLE -> Material.MAGENTA_BANNER;
            case YELLOW -> Material.YELLOW_BANNER;
            default -> Material.WHITE_BANNER;
        };
    }

    /**
     * Convert the given ChatColor to a Material color (e.g. DYE
     * @param color ChatColor - Color to convert
     * @return Material - The converted dye with color
     * @since 1.0.2
     */
    public static Material convertChatColorToMaterialDye(ChatColor color) {
        return switch (color) {
            case BLACK -> Material.BLACK_DYE;
            case DARK_BLUE -> Material.BLUE_DYE;
            case DARK_GREEN -> Material.GREEN_DYE;
            case DARK_AQUA -> Material.CYAN_DYE;
            case DARK_RED, RED -> Material.RED_DYE;
            case DARK_PURPLE -> Material.PURPLE_DYE;
            case GOLD -> Material.ORANGE_DYE;
            case GRAY -> Material.LIGHT_GRAY_DYE;
            case DARK_GRAY -> Material.GRAY_DYE;
            case BLUE, AQUA -> Material.LIGHT_BLUE_DYE;
            case GREEN -> Material.LIME_DYE;
            case LIGHT_PURPLE -> Material.MAGENTA_DYE;
            case YELLOW -> Material.YELLOW_DYE;
            default -> Material.WHITE_DYE;
        };
    }

    protected static Color convertChatColorToBukkitColor(ChatColor chatColor) {
        if (chatColor == null)
            return Color.WHITE;
        return switch (chatColor) {
            case AQUA -> Color.AQUA;
            case BLACK -> Color.BLACK;
            case BLUE, DARK_AQUA, DARK_BLUE -> Color.BLUE;
            case DARK_GRAY, GRAY -> Color.GRAY;
            case DARK_GREEN, GREEN -> Color.GREEN;
            case DARK_PURPLE, LIGHT_PURPLE -> Color.PURPLE;
            case DARK_RED, RED -> Color.RED;
            case GOLD, YELLOW -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }

}
