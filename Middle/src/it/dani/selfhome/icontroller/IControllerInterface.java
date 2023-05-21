package it.dani.selfhome.icontroller;

import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import it.dani.selfhome.model.Group;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IControllerInterface {
    public Set<Group> getGroups();
    public Set<Device> getDevices();
    public DeviceState getState(@NotNull String deviceName);
    public boolean addNewGroup(@NotNull String groupName);
    public boolean delGroup(@NotNull String groupName);
    public boolean addDeviceToGroup(@NotNull String deviceName, String groupName);
    public boolean delDeviceFromGroup(@NotNull String deviceName, String groupName);
    public boolean setState(@NotNull String deviceName, @NotNull DeviceState deviceState);
    public boolean toggleState(@NotNull String deviceName);
    public boolean setStateGroup(@NotNull String groupName, @NotNull DeviceState deviceState);
    public boolean toggleGroupState(@NotNull String groupName);
}
