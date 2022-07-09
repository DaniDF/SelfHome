package it.dani.selfhome.controller;

import it.dani.selfhome.icontroller.IController;
import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import it.dani.selfhome.model.Group;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller extends IController {

    private final Lock lock;
    private final Condition settersDel;
    private final Condition settersAdd;
    private final Condition getters;

    private int settersDelCount;
    private int settersAddCount;
    private int gettersCount;

    private boolean inUse;

    public Controller(String coreAddress, int corePort) throws UnknownHostException {
        super(coreAddress, corePort);

        this.lock = new ReentrantLock();

        this.settersDel = lock.newCondition();
        this.settersAdd = lock.newCondition();
        this.getters = lock.newCondition();

        this.settersDelCount = 0;
        this.settersAddCount = 0;
        this.gettersCount = 0;

        this.inUse = false;
    }

    @Override
    public final Set<Group> getGroups() {
        Set<Group> result = new HashSet<>();

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0 || this.settersAddCount > 0) {
                this.gettersCount++;
                this.getters.await();
                this.gettersCount--;
            }

            this.inUse = true;

            //Start operation
            result = super.getGroups();
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public final Set<Device> getDevices() {
        Set<Device> result = new HashSet<>();

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0 || this.settersAddCount > 0) {
                this.gettersCount++;
                this.getters.await();
                this.gettersCount--;
            }

            this.inUse = true;

            //Start operation
            result = super.getDevices();
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public DeviceState getState(@NotNull String deviceName)
    {
        Device result = null;

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0 || this.settersAddCount > 0) {
                this.gettersCount++;
                this.getters.await();
                this.gettersCount--;
            }

            this.inUse = true;

            //Start operation
            for(Device d : super.devices)
            {
                if(d.getName().equals(deviceName))
                {
                    result = d;
                    result.setState(this.getDeviceStatePersistence().getState(result));
                }
            }
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (result == null)? null : result.getState();
    }

    @Override
    public boolean addNewGroup(@NotNull String groupName) {
        boolean result = false;

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0) {
                this.settersAddCount++;
                this.settersAdd.await();
                this.settersAddCount--;
            }

            this.inUse = true;

            //Start operation
            result = super.getGroups().add(new Group(groupName));
            super.store();
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean delGroup(@NotNull String groupName) {
        boolean flagFind = false;

        try {
            this.lock.lock();

            while (inUse) {
                this.settersDelCount++;
                this.settersDel.await();
                this.settersDelCount--;
            }

            this.inUse = true;

            //Start operation
            Iterator<Group> iterator = super.getGroups().iterator();
            while(iterator.hasNext())
            {
                Group g = iterator.next();

                if (g.getName().equals(groupName)) {
                    iterator.remove();
                    flagFind = true;
                }
            }

            if (flagFind) super.store();
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return flagFind;
    }

    @Override
    public boolean addDeviceToGroup(@NotNull String deviceName, @NotNull String groupName) {
        boolean flagErr = true;

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0) {
                this.settersAddCount++;
                this.settersAdd.await();
                this.settersAddCount--;
            }

            this.inUse = true;

            //Start operation
            Device device = null;
            for(Device d : super.getDevices())
            {
                if(d.getName().equals(deviceName))
                {
                    device = d;
                }
            }

            if(device != null)
            {
                device.setState(super.getDeviceStatePersistence().getState(device));

                for(Group g : super.getGroups())
                {
                    if(g.getName().equals(groupName))
                    {
                        g.getDevices().add(device);
                        flagErr = false;
                    }
                }

                super.store();
            }
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return !flagErr;
    }

    @Override
    public boolean delDeviceFromGroup(@NotNull String deviceName, @NotNull String groupName) {
        boolean flagErr = true;

        try
        {
            this.lock.lock();

            while (inUse) {
                this.settersDelCount++;
                this.settersDel.await();
                this.settersDelCount--;
            }

            this.inUse = true;

            //Start operation
            Device device = null;
            for (Device d : super.getDevices()) {
                if (d.getName().equals(deviceName)) {
                    device = d;
                }
            }

            if (device != null) {
                for (Group g : super.getGroups()) {
                    if (g.getName().equals(groupName)) {
                        g.getDevices().remove(device);
                        flagErr = false;
                    }
                }

                super.store();
            }
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return !flagErr;
    }

    @Override
    public boolean setState(@NotNull String deviceName, @NotNull DeviceState deviceState) {
        boolean flagErr = true;

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0) {
                this.settersAddCount++;
                this.settersAdd.await();
                this.settersAddCount--;
            }

            this.inUse = true;

            //Start operation
            Device device = null;
            for(Device d : super.getDevices())
            {
                if(d.getName().equals(deviceName))
                {
                    device = d;
                }
            }

            if(device != null)
            {
                device.setState(deviceState);
                flagErr = !super.getDeviceStatePersistence().setState(device);
            }
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return !flagErr;
    }

    @Override
    public boolean toggleState(@NotNull String deviceName)
    {
        boolean flagErr = true;

        try {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0) {
                this.settersAddCount++;
                this.settersAdd.await();
                this.settersAddCount--;
            }

            this.inUse = true;

            //Start operation
            Device device = null;
            for(Device d : super.getDevices())
            {
                if(d.getName().equals(deviceName))
                {
                    device = d;
                }
            }

            if(device != null)
            {
                device.setState(DeviceState.not(device.getState()));
                flagErr = !super.getDeviceStatePersistence().setState(device);
            }
            //Finish operation

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return !flagErr;
    }

    @Override
    public boolean setStateGroup(@NotNull String groupName, @NotNull DeviceState deviceState) {
        boolean flagErr = true;

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0) {
                this.settersAddCount++;
                this.settersAdd.await();
                this.settersAddCount--;
            }

            this.inUse = true;

            //Start operation
            Group group = null;
            for(Group g : super.getGroups())
            {
                if(g.getName().equals(groupName))
                {
                    group = g;
                }
            }

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

            if(group != null)
            {
                flagErr = false;

                for(Device d : group.getDevices())
                {
                    flagErr = flagErr || !this.setState(d.getName(),deviceState);
                }
            }
            //Finish operation

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return !flagErr;
    }

    @Override
    public boolean toggleGroupState(@NotNull String groupName)
    {
        boolean flagErr = true;

        try
        {
            this.lock.lock();

            while (inUse || this.settersDelCount > 0) {
                this.settersAddCount++;
                this.settersAdd.await();
                this.settersAddCount--;
            }

            this.inUse = true;

            //Start operation
            Group group = null;
            for(Group g : super.getGroups())
            {
                if(g.getName().equals(groupName))
                {
                    group = g;
                }
            }

            this.inUse = false;

            if (this.settersDelCount > 0) this.settersDel.signalAll();
            else if (this.settersAddCount > 0) this.settersAdd.signal();
            else if (this.gettersCount > 0) this.getters.signal();

            this.lock.unlock();

            if(group != null)
            {
                flagErr = false;

                for(Device d : group.getDevices())
                {
                    flagErr = flagErr || !this.setState(d.getName(),DeviceState.not(d.getState()));
                }
            }
            //Finish operation

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return !flagErr;
    }
}
