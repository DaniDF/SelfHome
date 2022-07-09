package it.dani.selfhome.automation.executor;

import it.dani.selfhome.automation.icontroller.IController;
import org.jetbrains.annotations.NotNull;

public class Executor extends Thread {

    private final IController controller;

    public Executor(@NotNull IController controller)
    {
        this.controller = controller;
    }

    @Override
    public void run()
    {
        while(true)
        {
            this.controller.execute(this.controller.getAutomations(x -> true));
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
