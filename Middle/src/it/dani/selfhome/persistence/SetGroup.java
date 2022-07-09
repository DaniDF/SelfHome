package it.dani.selfhome.persistence;

import it.dani.selfhome.model.Group;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class SetGroup {
    private Set<Group> groups;

    public SetGroup()
    {
        this.groups = new TreeSet<>();
    }

    public Set<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetGroup setGroup = (SetGroup) o;
        return Objects.equals(groups, setGroup.groups);
    }
}
