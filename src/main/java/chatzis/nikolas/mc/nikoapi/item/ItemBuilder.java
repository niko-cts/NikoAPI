package chatzis.nikolas.mc.nikoapi.item;

import chatzis.nikolas.mc.nikoapi.NikoAPI;
import chatzis.nikolas.mc.nikoapi.util.EntityTypeConverterUtil;
import chatzis.nikolas.mc.nikoapi.util.ReflectionHelper;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to create items with specific properties.
 * The final ItemStack is returned by the craft() method.
 *
 * @author HeyImDome
 * @since 0.0.1
 */
public class ItemBuilder {


	private static final Logger LOG = NikoAPI.getInstance().getLogger();
	private static final String COL_COULDNTSET = "Could not set";

	private final ItemStack itemStack;
	private ItemMeta itemMeta;

	/**
	 * Creates an instance of the ItemBuilder.
	 *
	 * @param itemStack ItemStack - the ItemStack to use.
	 * @since 0.0.1
	 */
	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack.clone();
		this.itemMeta = this.itemStack.getItemMeta();

		// Necessary for 1.12+, to show the book correctly
		if (this.itemMeta instanceof BookMeta) {
			setBookAuthor("");
			setBookTitle("");
		}
	}

	/**
	 * Creates an instance of the ItemBuilder.
	 *
	 * @param material Material - the material to use.
	 * @since 0.0.1
	 */
	public ItemBuilder(Material material) {
		this(new ItemStack(material));
	}

	/**
	 * Returns the spawn head or the spawn egg of the entity type
	 *
	 * @param entityType EntityType - the entity type
	 */
	public ItemBuilder(EntityType entityType) {
		this(new ItemStack(EntityTypeConverterUtil.getHeadOrSpawnEgg(entityType)));
	}

	/**
	 * Creates an instance of the ItemBuilder.
	 *
	 * @param material Material - the material to use.
	 * @param amount   int - the item amount.
	 * @since 0.0.1
	 */
	public ItemBuilder(Material material, int amount) {
		this(new ItemStack(material, amount));
	}


	/**
	 * Creates an instance of the ItemBuilder.
	 *
	 * @param material Material - the material to use.
	 * @param amount   int - the item amount.
	 * @param subId    short - the sub id of the item.
	 * @since 0.0.1
	 */
	public ItemBuilder(Material material, int amount, short subId) {
		this(new ItemStack(material, amount, subId));
	}

	/**
	 * Creates an instance of the ItemBuilder.
	 *
	 * @param material Material - the material to use.
	 * @param subId    short - the sub id of the item.
	 * @since 0.0.1
	 */
	public ItemBuilder(Material material, short subId) {
		this(material, 1, subId);
	}

	/**
	 * Gets the current ItemStack.
	 *
	 * @return ItemStack - the current ItemStack.
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * Gets the current ItemMeta
	 *
	 * @return ItemMeta - the current ItemMeta.
	 */
	public ItemMeta getItemMeta() {
		return itemMeta;
	}

	/**
	 * Sets the display name of an item.
	 *
	 * @param displayName String - the display name.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setName(String displayName) {
		this.itemMeta.setDisplayName(displayName);
		return this;
	}

	/**
	 * Sets the lore of the item.
	 *
	 * @param lore String... - the lore to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setLore(String... lore) {
		return setLore(Arrays.asList(lore));
	}

	/**
	 * Sets the lore of the item.
	 *
	 * @param lore List<String> - the lore to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setLore(List<String> lore) {
		this.itemMeta.setLore(lore);
		return this;
	}

	/**
	 * Adds new lore to the item.
	 *
	 * @param lore String[] - the lore to add.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addLore(String... lore) {
		List<String> oldLore = new ArrayList<>(itemMeta != null && itemMeta.hasLore() && itemMeta.getLore() != null ? itemMeta.getLore() : List.of());
		oldLore.addAll(Arrays.asList(lore));
		return setLore(oldLore);
	}

	/**
	 * Sets the amount of the item.
	 *
	 * @param amount int - the amount to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setAmount(int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}

	/**
	 * Sets the material data of the item.
	 *
	 * @param materialData MaterialData - the material data to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setMaterialData(MaterialData materialData) {
		this.itemStack.setItemMeta(itemMeta);
		this.itemStack.setData(materialData);
		this.itemMeta = this.itemStack.getItemMeta();
		return this;
	}

	/**
	 * Sets the durability of the item.
	 *
	 * @param durability short - the durability to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 * @deprecated use {@link ItemBuilder#setDamage}
	 */
	@Deprecated
	public ItemBuilder setDurability(short durability) {
		this.itemStack.setDurability(durability);
		return this;
	}

	/**
	 * Sets the damage to the item.
	 *
	 * @param damage int - the damage.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.2
	 */
	public ItemBuilder setDamage(int damage) {
		if (!(itemMeta instanceof Damageable damageable)) {
			return this;
		}
		damageable.setDamage(damage);
		return this;
	}

	/**
	 * Multiplies the given damage by the maximum damage
	 *
	 * @param damage int - the damage.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.2
	 */
	public ItemBuilder setDamageByMultiply(float damage) {
		if (itemMeta instanceof Damageable damageable) {
			damageable.setDamage(Math.max(1,
					Math.min(itemStack.getType().getMaxDurability() - 1, itemStack.getType().getMaxDurability() - Math.round(itemStack.getType().getMaxDurability() * damage))));
		}
		return this;
	}


	/**
	 * Sets the item flags for the item.
	 *
	 * @param itemFlags ItemFlag... - the item flags to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
		this.itemMeta.addItemFlags(itemFlags);
		return this;
	}

	/**
	 * Adds an enchantment to the item.
	 *
	 * @param enchantment       Enchantment - the enchantment to add.
	 * @param level             int - the enchantment level.
	 * @param ignoreRestriction boolean - whatever the maximum level of the enchantment should be ignored.
	 * @param visible           boolean - whatever the enchantment should be displayed on the item.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreRestriction, boolean visible) {
		if (enchantment == null)
			return this;
		this.itemMeta.addEnchant(enchantment, level, ignoreRestriction);
		if (!visible) {
			return addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		return this;
	}

	/**
	 * Adds an enchantment to the item.
	 *
	 * @param enchantment       Enchantment - the enchantment to add.
	 * @param level             int - the enchantment level.
	 * @param ignoreRestriction boolean - whatever the maximum level of the enchantment should be ignored.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreRestriction) {
		return addEnchantment(enchantment, level, ignoreRestriction, true);
	}

	/**
	 * Adds an enchantment to the item.
	 *
	 * @param enchantment Enchantment - the enchantment to add.
	 * @param level       int - the enchantment level.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		return addEnchantment(enchantment, level, true, true);
	}

	/**
	 * Clears the item flags of the item.
	 *
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder clearItemFlags() {
		this.itemMeta.getItemFlags().clear();
		return this;
	}

	/**
	 * Sets the texture of the player head item.
	 *
	 * @param texture   String - The texture to apply.
	 * @param signature String - the signature to apply
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setSkin(String texture, String signature) {
		if (itemMeta instanceof SkullMeta skullMeta) {
			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			profile.getProperties().put("textures", new Property("textures", texture, signature));
			ReflectionHelper.set(skullMeta.getClass(), itemMeta, "profile", profile);
		} else {
			LOG.log(Level.WARNING, COL_COULDNTSET + "skull owner: item is not a player skull.");
		}
		return this;
	}

	/**
	 * Sets if the item should be unbreakable or not.
	 *
	 * @param unbreakable boolean - whatever the item should be unbreakable.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setUnbreakable(boolean unbreakable) {
		this.itemMeta.setUnbreakable(unbreakable);
		return this;
	}

	/**
	 * Sets the owner of the skull item.
	 * Only works if the item is a player skull.
	 *
	 * @param name String - the player name to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setSkullOwner(String name) {
		return setSkullOwner(Bukkit.getOfflinePlayer(name));
	}

	/**
	 * Sets the owner of the skull item.
	 * Only works if the item is a player skull.
	 *
	 * @param offlinePlayer OfflinePlayer - the offline player to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setSkullOwner(OfflinePlayer offlinePlayer) {
		if (!(itemMeta instanceof SkullMeta skullMeta)) {
			LOG.warning(COL_COULDNTSET + "skull owner: item is not a player skull.");
			return this;
		}

		if (!skullMeta.hasOwner())
			skullMeta.setOwningPlayer(offlinePlayer);
		return this;
	}

	/**
	 * Sets the type of entity to spawn with the spawn egg.
	 * Only works if the item is a spawn egg.
	 *
	 * @param entityType EntityType - the entity type to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	@Deprecated
	public ItemBuilder setSpawnEggType(EntityType entityType) {
		if (!(itemMeta instanceof SpawnEggMeta)) {
			LOG.warning(COL_COULDNTSET + "egg type: item is not an egg.");
			return this;
		}

		((SpawnEggMeta) itemMeta).setSpawnedType(entityType);
		return this;
	}

	/**
	 * Adds a potion effect to the item.
	 * Only works if the item is a potion.
	 *
	 * @param potionEffect PotionEffect - the potion effect to add.
	 * @param overwrite    boolean - whatever
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addPotionEffect(PotionEffect potionEffect, boolean overwrite) {
		if (!(itemMeta instanceof PotionMeta potionMeta)) {
			LOG.warning(COL_COULDNTSET + "potion effect: item os not a potion.");
			return this;
		}

		potionMeta.addCustomEffect(potionEffect, overwrite);
		return this;
	}

	/**
	 * Adds a potion effect to the item.
	 * Only works if the item is a potion.
	 *
	 * @param potionEffect PotionEffect - the potion effect to add.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addPotionEffect(PotionEffect potionEffect) {
		return addPotionEffect(potionEffect, true);
	}

	/**
	 * Sets the potion data of the item.
	 *
	 * @param potionData PotionData - the potion data to set.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setPotionData(PotionData potionData) {
		if (!(itemMeta instanceof PotionMeta potionMeta)) {
			LOG.warning(COL_COULDNTSET + "potion effect: item is not a potion.");
			return this;
		}

		potionMeta.setBasePotionData(potionData);
		return this;
	}

	/**
	 * Sets the leather color of the item.
	 * Only works if the item is a piece of leather armor.
	 *
	 * @param color Color - the color for the item.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setLeatherColor(Color color) {
		if (!(itemMeta instanceof LeatherArmorMeta leatherArmorMeta)) {
			LOG.warning(COL_COULDNTSET + "leather color: item is not a leather armor piece.");
			return this;
		}

		leatherArmorMeta.setColor(color);
		return this;
	}

	/**
	 * Sets if the map item should scale or not.
	 * Only works if the item is a map.
	 *
	 * @param mapScaling boolean - whatever the map should scale or not.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setMapScaling(boolean mapScaling) {
		if (!(itemMeta instanceof MapMeta mapMeta)) {
			LOG.warning(COL_COULDNTSET + "map scaling: the item is not a map.");
			return this;
		}

		mapMeta.setScaling(mapScaling);
		return this;
	}

	/**
	 * Sets the book author of the book item.
	 * Only works if the item is a book.
	 *
	 * @param bookAuthor String - the name of the book author.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setBookAuthor(String bookAuthor) {
		if (!(itemMeta instanceof BookMeta bookMeta)) {
			LOG.warning(COL_COULDNTSET + "book author: item is not a book.");
			return this;
		}

		bookMeta.setAuthor(bookAuthor);
		return this;
	}

	/**
	 * Sets the book title of the item.
	 * Only works if the item is a book.
	 *
	 * @param bookTitle String - the title of the book.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setBookTitle(String bookTitle) {
		if (!(itemMeta instanceof BookMeta bookMeta)) {
			LOG.warning(COL_COULDNTSET + "book title: item is not a book.");
			return this;
		}

		bookMeta.setTitle(bookTitle);
		return this;
	}

	/**
	 * Adds a page to the book item.
	 * Only works if the item is a book.
	 *
	 * @param text String... - the text to add to the page.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addPage(String... text) {
		if (!(itemMeta instanceof BookMeta bookMeta)) {
			LOG.warning("Could not add book page: item is not a book.");
			return this;
		}

		bookMeta.addPage(text);
		return this;
	}

	/**
	 * Adds a page to the book item.
	 * Only works if the item is a book.
	 *
	 * @param baseComponents BaseComponent... - the base components to add to the page.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder addPage(BaseComponent... baseComponents) {
		if (!(itemMeta instanceof BookMeta bookMeta)) {
			LOG.warning("Could not add book page: item is not a book.");
			return this;
		}

		bookMeta.spigot().addPage(baseComponents);
		return this;
	}

	/**
	 * Sets a specific page in the book.
	 *
	 * @param page int - the page.
	 * @param text String - the text to add to the page.
	 * @return ItemBuilder - the current item builder.
	 * @since 0.0.1
	 */
	public ItemBuilder setPage(int page, String text) {
		if (!(itemMeta instanceof BookMeta bookMeta)) {
			LOG.warning(COL_COULDNTSET + "book page: item is not a book.");
			return this;
		}

		bookMeta.setPage(page, text);
		return this;
	}

	/**
	 * Creates the item with the ItemMeta.
	 *
	 * @return ItemStack - the final ItemStack.
	 * @since 0.0.1
	 */
	public ItemStack craft() {
		this.itemStack.setItemMeta(itemMeta);
		return this.itemStack;
	}
}
