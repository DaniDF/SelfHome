package it.dani.selfhome.automation.controller;

import it.dani.selfhome.automation.icontroller.IController;
import it.dani.selfhome.automation.model.Automation;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;

public class Controller extends IController {

    public Controller(@NotNull String coreAddr, int corePort) throws UnknownHostException {
        super(coreAddr,corePort);
    }

    @Override
    public boolean execute(Set<Automation> automations)
    {
        boolean result = false;

        for(Automation a : automations)
        {
            if(a.getValidity().isValid()) result |= execute(a);
        }

        return result;
    }

    private boolean execute(@NotNull Automation automation)
    {
        boolean result = false;

        try(DatagramSocket sock = new DatagramSocket())
        {
            byte[] buffer = new byte[128];
            DatagramPacket pack = new DatagramPacket(buffer, buffer.length,super.getCoreAddr(),super.getCorePort());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(baos);

            osw.write(automation.getScript());
            osw.flush();

            pack.setData(baos.toByteArray());
            sock.send(pack);
            baos.close();
            sock.setSoTimeout(1000);

            Arrays.fill(buffer,(byte)0);
            pack.setData(buffer);
            sock.receive(pack);

            result = true;

        } catch (IOException e) {

        }

        return result;
    }
}
