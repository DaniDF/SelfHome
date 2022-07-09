package it.dani.selfhomeapp.middle.icontroller;

import androidx.annotation.NonNull;

import java.util.Set;
import java.util.function.Consumer;

public interface IDeviceController {
    public void freeText(@NonNull String text, @NonNull Consumer<String> consumer, @NonNull Consumer<Exception> error);
    public void toggleDevice(@NonNull String deviceName, @NonNull Consumer<Boolean> consumer, @NonNull Consumer<Exception> error);
    public void getDevices(@NonNull Consumer<Set<String>> consumer);
    public void getDeviceState(@NonNull String deviceName, @NonNull Consumer<Integer> consumer);
    public void setDeviceState(@NonNull String deviceName, int state, @NonNull Consumer<Boolean> consumer);

    public void getDevices(@NonNull Consumer<Set<String>> consumer, Consumer<Exception> error);
    public void getDeviceState(@NonNull String deviceName, @NonNull Consumer<Integer> consumer, Consumer<Exception> error);
    public void setDeviceState(@NonNull String deviceName, int state, @NonNull Consumer<Boolean> consumer, Consumer<Exception> error);
}
