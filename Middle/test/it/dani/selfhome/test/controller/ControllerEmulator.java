package it.dani.selfhome.test.controller;

import it.dani.selfhome.icontroller.IControllerInterface;
import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import it.dani.selfhome.model.Group;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ControllerEmulator implements IControllerInterface {


    @Override
    public Set<Group> getGroups() {
        Set<Group>result = new HashSet<>();
        result.add(new Group("G1"));
        result.add(new Group("G2"));
        result.add(new Group("G3"));
        return result;
    }

    @Override
    public Set<Device> getDevices() {
        Set<Device> result = new HashSet<>();
        result.add(new Device("D1"));
        result.add(new Device("D2"));
        result.add(new Device("D3"));
        return result;
    }

    @Override
    public DeviceState getState(@NotNull String deviceName) {
        return new DeviceState(((int)(Math.random() * 1000)) % 256);
    }

    @Override
    public boolean addNewGroup(@NotNull String groupName) {
        return true;
    }

    @Override
    public boolean delGroup(@NotNull String groupName) {
        return true;
    }

    @Override
    public boolean addDeviceToGroup(@NotNull String deviceName, String groupName) {
        return true;
    }

    @Override
    public boolean delDeviceFromGroup(@NotNull String deviceName, String groupName) {
        return true;
    }

    @Override
    public boolean setState(@NotNull String deviceName, @NotNull DeviceState deviceState) {
        return true;
    }

    @Override
    public boolean toggleState(@NotNull String deviceName) {
        return true;
    }

    @Override
    public boolean setStateGroup(@NotNull String groupName, @NotNull DeviceState deviceState) {
        return true;
    }

    @Override
    public boolean toggleGroupState(@NotNull String groupName) {
        return true;
    }
}
