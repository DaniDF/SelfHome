package it.dani.selfhome.persistence;

import com.google.gson.Gson;
import it.dani.selfhome.ipersistence.IGroupPersistence;
import it.dani.selfhome.model.Group;

import java.io.*;
import java.util.Set;

public class FilePersistence implements IGroupPersistence {
    private final File file;

    public FilePersistence(String fileName)
    {
        this.file = new File(fileName);
    }

    @Override
    public Set<Group> loadGroups() throws IOException {
        BufferedReader fileIn = new BufferedReader(new FileReader(this.file));

        String dataS = "";
        String line;
        while((line = fileIn.readLine()) != null)
        {
            dataS += line;
        }

        fileIn.close();

        Gson gson = new Gson();
        SetGroup groups = gson.fromJson(dataS,SetGroup.class);

        return groups.getGroups();
    }

    @Override
    public void storeGroups(Set<Group> groups) throws IOException {
        SetGroup setGroup = new SetGroup();
        setGroup.setGroups(groups);

        PrintWriter fileOut = new PrintWriter(this.file);
        Gson gson = new Gson();
        fileOut.println(gson.toJson(setGroup));
        fileOut.close();
    }
}
