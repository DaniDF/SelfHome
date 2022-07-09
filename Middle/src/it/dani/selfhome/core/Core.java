package it.dani.selfhome.core;

import java.io.IOException;

public class Core extends Thread {
    private final String[] args;

    public Core(String... args)
    {
        this.args = args;
    }

    @Override
    public void run()
    {
        try {
            Process coreProcess = Runtime.getRuntime().exec(this.args);
            coreProcess.getInputStream().transferTo(System.out);
            coreProcess.getErrorStream().transferTo(System.out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
