package it.dani.selfhome.ipersistence;

import it.dani.selfhome.model.Device;

import java.util.Set;

public interface IDevicePersistence {
    public Set<Device> listDevice();
}
