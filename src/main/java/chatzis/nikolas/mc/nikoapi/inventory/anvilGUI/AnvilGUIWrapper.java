package chatzis.nikolas.mc.nikoapi.inventory.anvilGUI;

import chatzis.nikolas.mc.nikoapi.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.v1_20_R1.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * <a href="https://github.com/WesJD/AnvilGUI/blob/master/1_19_R3/src/main/java/net/wesjd/anvilgui/version/Wrapper1_19_R3.java">Credits</a>
 * @author Wesley Smith
 * @since 1.1.1
 */
public class AnvilGUIWrapper {

    private int getRealNextContainerId(Player player) {
        return Utils.getEntityPlayer(player).nextContainerCounter();
    }

    public int getNextContainerId(Object container) {
        return ((AnvilContainer) container).getContainerId();
    }

    public void handleInventoryCloseEvent(Player player) {
        CraftEventFactory.handleInventoryCloseEvent(Utils.getEntityPlayer(player));
    }


    public void sendPacketOpenWindow(Player player, int containerId, String inventoryTitle) {
        Utils.getEntityPlayer(player).connection.send(new ClientboundOpenScreenPacket(containerId, MenuType.ANVIL, Component.literal(inventoryTitle)));
    }

    public void sendPacketCloseWindow(Player player, int containerId) {
        Utils.getEntityPlayer(player).connection.send(new ClientboundContainerClosePacket(containerId));
    }

    public void setActiveContainerDefault(Player player) {
        Utils.getEntityPlayer(player).containerMenu = Utils.getEntityPlayer(player).inventoryMenu;
    }

    public void setActiveContainer(Player player, AnvilContainer container) {
        Utils.getEntityPlayer(player).containerMenu = container;
    }

    public void setActiveContainerId(Object container, int containerId) {}

    public void addActiveContainerSlotListener(AnvilContainer container, Player player) {
        Utils.getEntityPlayer(player).initMenu(container);
    }

    public Inventory toBukkitInventory(AnvilContainer container) {
        return container.getBukkitView().getTopInventory();
    }

    public AnvilContainer newContainerAnvil(Player player, String title) {
        return new AnvilContainer(player, getRealNextContainerId(player), title);
    }

    /**
     * Modifications to ContainerAnvil that makes it, so you don't have to have xp to use this anvil
     */
    static class AnvilContainer extends AnvilMenu {

        public AnvilContainer(Player player, int containerId, String guiTitle) {
            super(containerId,
                    Utils.getEntityPlayer(player).getInventory(),
                    ContainerLevelAccess.create(Utils.getServerWorld(player.getWorld()), new BlockPos(0, 0, 0)));
            this.checkReachable = false;
            setTitle(Component.literal(guiTitle));
        }

        @Override
        public void createResult() {
            super.createResult();
            this.cost.set(0);
        }

        @Override
        public void removed(net.minecraft.world.entity.player.Player entityhuman) {

        }

        @Override
        protected void clearContainer(net.minecraft.world.entity.player.Player entityhuman, Container iinventory) {

        }

        public int getContainerId() {
            return this.containerId;
        }

    }
}
