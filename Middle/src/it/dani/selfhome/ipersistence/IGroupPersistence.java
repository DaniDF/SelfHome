package it.dani.selfhome.ipersistence;

import it.dani.selfhome.model.Group;

import java.io.IOException;
import java.util.Set;

public interface IGroupPersistence {
    public Set<Group> loadGroups() throws IOException;
    public void storeGroups(Set<Group> groups) throws IOException;
}
