package it.dani.selfhome.automation.persistence;

import it.dani.selfhome.automation.model.Automation;

import java.util.Set;
import java.util.TreeSet;

public class SetAutomation {
    private Set<Automation> automations;

    public SetAutomation()
    {
        this.automations = new TreeSet<>();
    }

    public Set<Automation> getAutomations() {
        return automations;
    }

    public void setAutomations(Set<Automation> automations) {
        this.automations = automations;
    }
}
