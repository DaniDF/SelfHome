package it.dani.selfhomeapp.middle.ipersistence;

import androidx.annotation.NonNull;

import java.util.Set;

public interface IGroupPersistence {
    public Set<String> getGroups();
    public boolean setStateGroup(@NonNull String groupName, int state);
    public boolean addNewGroup(@NonNull String groupName);
    public boolean delGroup(@NonNull String groupName);
    public Set<String> getDeviceFromGroup(@NonNull String groupName);
    public boolean addDeviceToGroup(@NonNull String deviceName, @NonNull String groupName);
    public boolean delDeviceToGroup(@NonNull String deviceName, @NonNull String groupName);
}
