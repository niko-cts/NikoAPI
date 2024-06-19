package chatzis.nikolas.mc.nikoapi.util;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntityTypeConverterUtil {

	private EntityTypeConverterUtil() {
		throw new UnsupportedOperationException();
	}

	public static Material getHeadOrSpawnEgg(EntityType entityType) {
		Objects.requireNonNull(entityType);

		String entityName = entityType.name();
		String materialName = entityName + "_HEAD";

		try {
			return Material.valueOf(materialName);
		} catch (IllegalArgumentException e) {
			return getSpawnEgg(entityType);
		}
	}

	private static Material getSpawnEgg(EntityType entityType) {
		String entityName = entityType.name();
		String materialName = entityName + "_SPAWN_EGG";

		try {
			return Material.valueOf(materialName);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("No spawn egg material found for entity type: " + entityType.name());
		}
	}

	/**
	 * Converts the entitytype to normal name e.g., PLAYER to Player
	 *
	 * @param entityType EntityType - the entity type to convert
	 * @return String - the beautified entity type
	 */
	public static String convertEntityToName(EntityType entityType) {
		Objects.requireNonNull(entityType);

		return Arrays.stream(entityType.name().toLowerCase().split("_"))
				.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
				.collect(Collectors.joining(" "));
	}

}
