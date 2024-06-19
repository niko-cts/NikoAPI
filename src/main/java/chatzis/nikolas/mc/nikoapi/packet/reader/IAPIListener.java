package chatzis.nikolas.mc.nikoapi.packet.reader;

import java.util.List;

/**
 * Interface for the APIListeners.
 */
public interface IAPIListener {

    List<APIListenerManager.PacketTypes> getListenerType();

}
