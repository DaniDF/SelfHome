package it.dani.selfhome.automation.icontroller;

import it.dani.selfhome.automation.ipersistence.IPersistence;
import it.dani.selfhome.automation.model.Automation;
import it.dani.selfhome.automation.persistence.Persistence;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class IController {
    private static final String FILENAME = "dbAutomations.json";

    private final InetAddress coreAddr;
    private final int corePort;

    private final IPersistence automationsPersistence;
    private Set<Automation> automations;

    public IController(@NotNull String coreAddr, int corePort) throws UnknownHostException {
        if(coreAddr.length() == 0) throw new IllegalArgumentException("Invalid core address");
        if(corePort <= 0) throw new IllegalArgumentException("Invalid core port");

        this.coreAddr = InetAddress.getByName(coreAddr);
        this.corePort = corePort;

        this.automationsPersistence = new Persistence(FILENAME);

        try
        {
            this.automations = this.automationsPersistence.loadAutomations();

        } catch (IOException e) {
            this.automations = new HashSet<>();
        }
    }

    public Set<Automation> getAutomations(Predicate<Automation> predicate)
    {
        return this.automations.stream().filter(predicate).collect(Collectors.toSet());
    }

    public boolean addNewAutomation(@NotNull Automation automation)
    {
        boolean result = this.automations.add(automation);

        if(result)
        {
            try
            {
                this.automationsPersistence.storeAutomations(this.automations);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public boolean delAutomation(Automation automation)
    {
        boolean result = this.automations.remove(automation);

        if(result)
        {
            try
            {
                this.automationsPersistence.storeAutomations(this.automations);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    protected @NotNull InetAddress getCoreAddr()
    {
        return this.coreAddr;
    }

    protected int getCorePort()
    {
        return this.corePort;
    }

    public abstract boolean execute(Set<Automation> automations);
}
