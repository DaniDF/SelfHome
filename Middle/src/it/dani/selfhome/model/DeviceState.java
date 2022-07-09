package it.dani.selfhome.model;

import org.jetbrains.annotations.NotNull;

public class DeviceState implements Comparable<DeviceState> {
    private final int state;

    public DeviceState(int state)
    {
        if(state < 0 || state >= 256*256*256) throw new IllegalArgumentException("Error: state out of range");

        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    @Override
    public String toString()
    {
        String result;

        if(this.state == 0) result = "OFF";
        else if(this.state == 255) result = "ON";
        else result = this.state + "";

        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        boolean isEq = this.getClass().equals(other.getClass());
        DeviceState otherD = null;
        if(isEq) otherD = (DeviceState)other;
        return isEq && this.state == otherD.state;
    }

    @Override
    public int compareTo(DeviceState o) {
        return Integer.compare(this.state,o.state);
    }

    public static DeviceState not(@NotNull DeviceState devState)
    {
        return new DeviceState((devState.getState() != 0)? 0:1);
    }
}

