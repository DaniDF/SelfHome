package it.dani.selfhome.ipersistence;

import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import org.jetbrains.annotations.NotNull;

public interface IDeviceStatePersistence {
    public DeviceState getState(@NotNull Device device);
    public boolean setState(@NotNull Device device);
}
