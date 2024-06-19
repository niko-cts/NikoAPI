package chatzis.nikolas.mc.nikoapi.item;

import chatzis.nikolas.mc.nikoapi.player.APIPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Class for some items which will often be used and should be the same everywhere.
 *
 * @author HeyImDome
 * @since 0.0.1
 */
public class UsefulItems {


	private UsefulItems() {
		throw new UnsupportedOperationException("UsefulItems is a utility class.");
	}

	private static final String STAINED_GLASS_PANE = "STAINED_GLASS_PANE";
	/**
	 * All colors of glass panes which are possible
	 * The item does not have a name for usage as a background in inventories.
	 */
	public static final ItemStack BACKGROUND_WHITE = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_ORANGE = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_MAGENTA = new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_LIGHT_BLUE = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_YELLOW = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_LIME = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_PINK = new ItemBuilder(Material.PINK_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_GRAY = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_LIGHT_GRAY = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_CYAN = new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_PURPLE = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_BLUE = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_BROWN = new ItemBuilder(Material.BROWN_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_GREEN = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_RED = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").craft();
	public static final ItemStack BACKGROUND_BLACK = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").craft();

	public static final ItemStack PLAYER_HEAD = new ItemBuilder(Material.PLAYER_HEAD).craft();
	public static final ItemStack SKELETON_SKULL = new ItemBuilder(Material.SKELETON_SKULL).craft();

	public static ItemBuilder ARROW_LEFT() {
		return new ItemBuilder(Material.PLAYER_HEAD)
				.setSkin("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJmMDQyNWQ2NGZkYzg5OTI5MjhkNjA4MTA5ODEwYzEyNTFmZTI0M2Q2MGQxNzViZWQ0MjdjNjUxY2JlIn19fQ==",
						"a2f0425d64fdc8992928d608109810c1251fe243d60d175bed427c651cbe");
	}

	public static ItemBuilder ARROW_RIGHT() {
		return new ItemBuilder(Material.PLAYER_HEAD)
				.setSkin("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ4NjVhYWUyNzQ2YTliOGU5YTRmZTYyOWZiMDhkMThkMGE5MjUxZTVjY2JlNWZhNzA1MWY1M2VhYjliOTQifX19",
						"6d865aae2746a9b8e9a4fe629fb08d18d0a9251e5ccbe5fa7051f53eab9b94");
	}

	public static ItemBuilder ARROW_UP() {
		return new ItemBuilder(Material.PLAYER_HEAD)
				.setSkin("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUxNDlkZGRhZGVkMjBkMjQ0ZTBiYjYyYTJkOWZhMGRjNmM2YTc4NjI1NTkzMjhhOTRmNzc3MjVmNTNjMzU4In19fQ==",
						"55149dddaded20d244e0bb62a2d9fa0dc6c6a7862559328a94f77725f53c358");
	}

	public static ItemBuilder ARROW_DOWN() {
		return new ItemBuilder(Material.PLAYER_HEAD)
				.setSkin("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ3MmM5ZDYyOGJiMzIyMWVmMzZiNGNiZDBiOWYxNWVkZDU4ZTU4NjgxODUxNGQ3ZTgyM2Q1NWM0OGMifX19",
						"9472c9d628bb3221ef36b4cbd0b9f15edd58e586818514d7e823d55c48c");
	}

	public static ItemBuilder HEAD_A() {
		return new ItemBuilder(Material.PLAYER_HEAD)
				.setSkin("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE5M2RjMGQ0YzVlODBmZjlhOGEwNWQyZmNmZTI2OTUzOWNiMzkyNzE5MGJhYzE5ZGEyZmNlNjFkNzEifX19",
						"3193dc0d4c5e80ff9a8a05d2fcfe269539cb3927190bac19da2fce61d71");
	}

	public static ItemBuilder HEAD_QUESTIONMARK() {
		return new ItemBuilder(Material.PLAYER_HEAD)
				.setSkin("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTAzNWM1MjgwMzZiMzg0YzUzYzljOGExYTEyNTY4NWUxNmJmYjM2OWMxOTdjYzlmMDNkZmEzYjgzNWIxYWE1NSJ9fX0=",
						"1035c528036b384c53c9c8a1a125685e16bfb369c197cc9f03dfa3b835b1aa55");
	}

	/**
	 * Returns a skull item with the name and texture of given player
	 *
	 * @param player APIPlayer - Player to get the head from
	 * @return ItemBuilder - Modified ItemBuilder
	 */
	public static ItemBuilder getPlayerHead(APIPlayer player) {
		return getPlayerHead(player.getPlayer());
	}

	/**
	 * Returns a skull item with the name and texture of given player
	 *
	 * @param player Player - Player to get the head from
	 * @return ItemBuilder - Modified ItemBuilder
	 */
	public static ItemBuilder getPlayerHead(Player player) {
		return new ItemBuilder(PLAYER_HEAD).setName(player.getDisplayName()).setSkullOwner(player);
	}
}
