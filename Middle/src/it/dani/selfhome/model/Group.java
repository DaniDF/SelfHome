package it.dani.selfhome.model;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Group implements Comparable<Group> {
    private final String name;
    private final Set<Device> devices;

    public Group(@NotNull String name)
    {
        this.name = name;
        this.devices = new HashSet<>();
    }

    public String getName()
    {
        return this.name;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    @Override
    public String toString()
    {
        String result = this.name + "\n";

        for(Device d : this.devices)
        {
            result += "\t" + d.toString().replaceAll("\n","\n\t") + "\n";
        }

        return result;
    }

    @Override
    public boolean equals(Object other)
    {
        boolean isEq = this.getClass().equals(other.getClass());
        Group otherG = null;
        if(isEq) otherG = (Group)other;
        return isEq && this.name.equals(otherG.name);
    }

    @Override
    public int compareTo(Group o) {
        return this.name.compareTo(o.name);
    }
}
