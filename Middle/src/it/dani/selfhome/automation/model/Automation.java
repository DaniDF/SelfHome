package it.dani.selfhome.automation.model;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

public class Automation {
    private String script;
    private AutomationValidity validity;

    public Automation(@NotNull String script, @NotNull AutomationValidity validity)
    {
        this.script = script;
    }

    public String getScript() {
        return this.script;
    }

    public AutomationValidity getValidity() {
        return this.validity;
    }
}
