package it.dani.selfhomeapp.middle.ipersistence;

import androidx.annotation.NonNull;

import java.util.Set;

public interface IDevicePersistence {
    public @NonNull String freeText(@NonNull String text);
    public @NonNull boolean toggleDevice(@NonNull String text);
    public Set<String> getDevices();
    public boolean setStateDevice(@NonNull String deviceName, int state);
    public int getDeviceState(@NonNull String deviceName);
}
