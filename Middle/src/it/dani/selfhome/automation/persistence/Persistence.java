package it.dani.selfhome.automation.persistence;

import com.google.gson.Gson;
import it.dani.selfhome.automation.ipersistence.IPersistence;
import it.dani.selfhome.automation.model.Automation;
import it.dani.selfhome.persistence.SetGroup;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Set;

public class Persistence implements IPersistence {
    private final File file;

    public Persistence(@NotNull String fileName)
    {
        this.file = new File(fileName);
    }

    @Override
    public Set<Automation> loadAutomations() throws IOException {
        BufferedReader fileIn = new BufferedReader(new FileReader(this.file));

        String dataS = "";
        String line;
        while((line = fileIn.readLine()) != null)
        {
            dataS += line;
        }

        fileIn.close();

        Gson gson = new Gson();
        SetAutomation automations = gson.fromJson(dataS,SetAutomation.class);

        return automations.getAutomations();
    }

    @Override
    public void storeAutomations(Set<Automation> automations) throws IOException {
        SetAutomation setAutomations = new SetAutomation();
        setAutomations.setAutomations(automations);

        PrintWriter fileOut = new PrintWriter(this.file);
        Gson gson = new Gson();
        fileOut.println(gson.toJson(setAutomations));
        fileOut.close();
    }
}
