package it.dani.selfhome.model;

import org.jetbrains.annotations.NotNull;

public class Device implements Comparable<Device> {
    private final String name;
    private DeviceState state;

    public Device(@NotNull String name)
    {
        this.name = name;
        this.state = new DeviceState((short)0);
    }

    public String getName()
    {
        return this.name;
    }

    public DeviceState getState()
    {
        return this.state;
    }

    public void setState(DeviceState state)
    {
        this.state = (state == null)? new DeviceState((short)0) : state;
    }

    @Override
    public String toString()
    {
        return this.name + " -- " + this.state;
    }

    @Override
    public boolean equals(Object other)
    {
        boolean isEq = this.getClass().equals(other.getClass());
        Device otherD = null;
        if(isEq) otherD = (Device)other;
        return isEq && this.name.equals(otherD.name);
    }

    @Override
    public int compareTo(Device o) {
        return this.name.compareTo(o.name);
    }
}
