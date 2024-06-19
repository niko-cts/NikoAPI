package chatzis.nikolas.mc.nikoapi.packet.reader;

import org.bukkit.event.inventory.ClickType;

public record EntityUsePacket(int entityId, ClickType clickType) {
}
