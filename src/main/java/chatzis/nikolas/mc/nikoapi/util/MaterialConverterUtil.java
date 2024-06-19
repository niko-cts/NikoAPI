package chatzis.nikolas.mc.nikoapi.util;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class MaterialConverterUtil {

	private MaterialConverterUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Converts the material to a normal name e.g., GRAVEL to Gravel
	 *
	 * @param material Material - the material to convert
	 * @return String - the beautified material
	 */
	public static String convertMaterialToName(Material material) {
		Objects.requireNonNull(material);

		return Arrays.stream(material.name().toLowerCase().split("_"))
				.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
				.collect(Collectors.joining(" "));
	}
}
