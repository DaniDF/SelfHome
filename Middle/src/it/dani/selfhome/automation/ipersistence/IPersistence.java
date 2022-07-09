package it.dani.selfhome.automation.ipersistence;

import it.dani.selfhome.automation.model.Automation;

import java.io.IOException;
import java.util.Set;

public interface IPersistence {
    public Set<Automation> loadAutomations() throws IOException;
    public void storeAutomations(Set<Automation> automations) throws IOException;
}
