package it.dani.selfhomeapp.middle.icontroller;

import androidx.annotation.NonNull;

import java.util.Set;
import java.util.function.Consumer;

public interface IGroupController {
    public void getGroups(@NonNull Consumer<Set<String>> consumer);
    public void setGroupState(@NonNull String groupName, int state, @NonNull Consumer<Boolean> consumer);
    public void addNewGroup(@NonNull String groupName, @NonNull Consumer<Boolean> consumer);
    public void delGroup(@NonNull String groupName, @NonNull Consumer<Boolean> consumer);
    public void getDevicesFromGroup(@NonNull String groupName, @NonNull Consumer<Set<String>> consumer);
    public void addDeviceToGroup(@NonNull String deviceName, @NonNull String groupName, @NonNull Consumer<Boolean> consumer);
    public void delDeviceToGroup(@NonNull String deviceName, @NonNull String groupName, @NonNull Consumer<Boolean> consumer);

    public void getGroups(@NonNull Consumer<Set<String>> consumer, Consumer<Exception> error);
    public void setGroupState(@NonNull String groupName, int state, @NonNull Consumer<Boolean> consumer, Consumer<Exception> error);
    public void addNewGroup(@NonNull String groupName, @NonNull Consumer<Boolean> consumer, Consumer<Exception> error);
    public void delGroup(@NonNull String groupName, @NonNull Consumer<Boolean> consumer, Consumer<Exception> error);
    public void getDevicesFromGroup(@NonNull String groupName, @NonNull Consumer<Set<String>> consumer, Consumer<Exception> error);
    public void addDeviceToGroup(@NonNull String deviceName, @NonNull String groupName, @NonNull Consumer<Boolean> consumer, Consumer<Exception> error);
    public void delDeviceToGroup(@NonNull String deviceName, @NonNull String groupName, @NonNull Consumer<Boolean> consumer, Consumer<Exception> error);
}
