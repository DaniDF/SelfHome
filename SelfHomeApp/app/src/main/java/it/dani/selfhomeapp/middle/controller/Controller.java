package it.dani.selfhomeapp.middle.controller;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.Set;
import java.util.function.Consumer;

import it.dani.selfhomeapp.middle.icontroller.IDeviceController;
import it.dani.selfhomeapp.middle.icontroller.IGroupController;
import it.dani.selfhomeapp.middle.ipersistence.IDevicePersistence;
import it.dani.selfhomeapp.middle.ipersistence.IGroupPersistence;
import it.dani.selfhomeapp.middle.persistence.Persistence;

public class Controller implements IDeviceController, IGroupController {

    private final IDevicePersistence devicePersistence;
    private final IGroupPersistence groupPersistence;

    public Controller(@NotNull String addr, int port) throws UnknownHostException {
        this.devicePersistence = Persistence.getPersitence(addr,port);
        this.groupPersistence = Persistence.getPersitence(addr,port);
    }

    @Override
    public void freeText(@NonNull String text, @NonNull Consumer<String> consumer, @NonNull Consumer<Exception> error)
    {
        ControllerSlave<String> slave = new ControllerSlave<>(() -> this.devicePersistence.freeText(text),consumer,error);
        slave.start();
    }

    @Override
    public void toggleDevice(@NonNull String deviceName, @NonNull Consumer<Boolean> consumer, @NonNull Consumer<Exception> error)
    {
        ControllerSlave<Boolean> slave = new ControllerSlave<>(() -> this.devicePersistence.toggleDevice(deviceName),consumer,error);
        slave.start();
    }

    @Override
    public void getDevices(@NotNull Consumer<Set<String>> consumer, Consumer<Exception> error) {
        ControllerSlave<Set<String>> slave = new ControllerSlave<>(this.devicePersistence::getDevices,consumer,error);
        slave.start();
    }

    @Override
    public void getDeviceState(@NotNull String deviceName, @NotNull Consumer<Integer> consumer, Consumer<Exception> error) {
        ControllerSlave<Integer> slave = new ControllerSlave<>(() -> this.devicePersistence.getDeviceState(deviceName),consumer,error);
        slave.start();
    }

    @Override
    public void setDeviceState(@NotNull String deviceName, int state, @NotNull Consumer<Boolean> consumer, Consumer<Exception> error) {
        ControllerSlave<Boolean> slave = new ControllerSlave<>(() -> this.devicePersistence.setStateDevice(deviceName,state),consumer,error);
        slave.start();
    }

    @Override
    public void getDevices(@NonNull Consumer<Set<String>> consumer) {
        this.getDevices(consumer,null);
    }

    @Override
    public void getDeviceState(@NonNull String deviceName, @NonNull Consumer<Integer> consumer) {
        this.getDeviceState(deviceName,consumer,null);
    }

    @Override
    public void setDeviceState(@NonNull String deviceName, int state, @NonNull Consumer<Boolean> consumer) {
        this.setDeviceState(deviceName,state,consumer,null);
    }

    @Override
    public void getGroups(@NotNull Consumer<Set<String>> consumer, Consumer<Exception> error) {
        ControllerSlave<Set<String>> slave = new ControllerSlave<>(this.groupPersistence::getGroups,consumer,error);
        slave.start();
    }

    @Override
    public void setGroupState(@NotNull String groupName, int state, @NotNull Consumer<Boolean> consumer, Consumer<Exception> error) {
        ControllerSlave<Boolean> slave = new ControllerSlave<>(() -> this.groupPersistence.setStateGroup(groupName,state),consumer,error);
        slave.start();
    }

    @Override
    public void addNewGroup(@NotNull String groupName, @NotNull Consumer<Boolean> consumer, Consumer<Exception> error) {
        ControllerSlave<Boolean> slave = new ControllerSlave<>(() -> this.groupPersistence.addNewGroup(groupName),consumer,error);
        slave.start();
    }

    @Override
    public void delGroup(@NonNull String groupName, @NonNull Consumer<Boolean> consumer, Consumer<Exception> error)
    {
        ControllerSlave<Boolean> slave = new ControllerSlave<>(() -> this.groupPersistence.delGroup(groupName),consumer,error);
        slave.start();
    }

    @Override
    public void getDevicesFromGroup(@NotNull String groupName, @NotNull Consumer<Set<String>> consumer, Consumer<Exception> error) {
        ControllerSlave<Set<String>> slave = new ControllerSlave<>(() -> this.groupPersistence.getDeviceFromGroup(groupName),consumer,error);
        slave.start();
    }

    @Override
    public void addDeviceToGroup(@NotNull String deviceName, @NotNull String groupName, @NotNull Consumer<Boolean> consumer, Consumer<Exception> error) {
        ControllerSlave<Boolean> slave = new ControllerSlave<>(() -> this.groupPersistence.addDeviceToGroup(deviceName,groupName),consumer,error);
        slave.start();
    }

    @Override
    public void delDeviceToGroup(@NotNull String deviceName, @NotNull String groupName, @NotNull Consumer<Boolean> consumer, Consumer<Exception> error) {
        ControllerSlave<Boolean> slave = new ControllerSlave<>(() -> this.groupPersistence.delDeviceToGroup(deviceName,groupName),consumer,error);
        slave.start();
    }

    @Override
    public void getGroups(@NonNull Consumer<Set<String>> consumer) {
        this.getGroups(consumer,null);
    }

    @Override
    public void setGroupState(@NonNull String groupName, int state, @NonNull Consumer<Boolean> consumer) {
        this.setGroupState(groupName,state,consumer,null);
    }

    @Override
    public void addNewGroup(@NonNull String groupName, @NonNull Consumer<Boolean> consumer) {
        this.addNewGroup(groupName,consumer,null);
    }

    @Override
    public void delGroup(@NonNull String groupName, @NonNull Consumer<Boolean> consumer) {
        this.delGroup(groupName,consumer,null);
    }

    @Override
    public void getDevicesFromGroup(@NonNull String groupName, @NonNull Consumer<Set<String>> consumer) {
        this.getDevicesFromGroup(groupName,consumer,null);
    }

    @Override
    public void addDeviceToGroup(@NonNull String deviceName, @NonNull String groupName, @NonNull Consumer<Boolean> consumer) {
        this.addDeviceToGroup(deviceName,groupName,consumer,null);
    }

    @Override
    public void delDeviceToGroup(@NonNull String deviceName, @NonNull String groupName, @NonNull Consumer<Boolean> consumer) {
        this.delDeviceToGroup(deviceName,groupName,consumer,null);
    }
}
