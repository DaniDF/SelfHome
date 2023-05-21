package it.dani.selfhome.icontroller;

import it.dani.selfhome.core.persistence.CorePersistence;
import it.dani.selfhome.ipersistence.IDevicePersistence;
import it.dani.selfhome.ipersistence.IDeviceStatePersistence;
import it.dani.selfhome.ipersistence.IGroupPersistence;
import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import it.dani.selfhome.model.Group;
import it.dani.selfhome.persistence.FilePersistence;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

public abstract class IController implements IControllerInterface {
    private static final String FILENAME = "dbGroups.json";

    private final IGroupPersistence groupPersistence;
    private final IDevicePersistence devicePersistence;
    private final IDeviceStatePersistence deviceStatePersistence;

    protected Set<Group> groups;
    protected final Set<Device> devices;

    public IController(@NotNull String coreAddress, int corePort) throws UnknownHostException {
        this.groupPersistence = new FilePersistence(FILENAME);
        this.devicePersistence = CorePersistence.getCorePersistence(coreAddress,corePort);
        this.deviceStatePersistence = CorePersistence.getCorePersistence(coreAddress,corePort);

        try {
            this.groups = this.groupPersistence.loadGroups();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            this.groups = new TreeSet<>();
        }
        this.devices = this.devicePersistence.listDevice();

        List<Group> groupL = new ArrayList<>(this.groups);
        this.groups = new TreeSet<>();

        for(Group g : groupL)
        {
            Group group = new Group(g.getName());

            for(Device d : g.getDevices())
            {
                if(this.devices.contains(d))
                {
                    Iterator<Device> iterDevice = this.devices.iterator();
                    boolean flagFind = false;
                    while(!flagFind && iterDevice.hasNext())
                    {
                        Device device = iterDevice.next();
                        if(device.equals(d))
                        {
                            group.getDevices().add(device);
                            flagFind = true;
                        }
                    }
                }
            }

            this.groups.add(group);
        }

        for(Device d : this.devices)
        {
            d.setState(this.deviceStatePersistence.getState(d));
        }
    }

    public Set<Group> getGroups() {
        return this.groups;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    protected void store()
    {
        try {
            this.groupPersistence.storeGroups(this.groups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected IDeviceStatePersistence getDeviceStatePersistence() {
        return deviceStatePersistence;
    }

    public abstract DeviceState getState(@NotNull String deviceName);
    public abstract boolean addNewGroup(@NotNull String groupName);
    public abstract boolean delGroup(@NotNull String groupName);
    public abstract boolean addDeviceToGroup(@NotNull String deviceName, String groupName);
    public abstract boolean delDeviceFromGroup(@NotNull String deviceName, String groupName);
    public abstract boolean setState(@NotNull String deviceName, @NotNull DeviceState deviceState);
    public abstract boolean toggleState(@NotNull String deviceName);
    public abstract boolean setStateGroup(@NotNull String groupName, @NotNull DeviceState deviceState);
    public abstract boolean toggleGroupState(@NotNull String groupName);
}
