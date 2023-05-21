package it.dani.selfhome;

import it.dani.selfhome.automation.executor.Executor;
import it.dani.selfhome.controller.Controller;
import it.dani.selfhome.icontroller.IController;
import it.dani.selfhome.listener.SocketListener;
import it.dani.selfhome.way.WhereAreYouListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    private static final String CORE_ADDR = "localhost";

    public static void main(String[] args)
    {
        int corePort = -1;
        int port = -1;

        try
        {
            if (args.length < 2) {
                System.err.println("Not enough parameter");
                System.exit(-1);
            }

            port = Integer.parseInt(args[0]);
            corePort = Integer.parseInt(args[1]);

        } catch (NumberFormatException e) {
            System.err.println("Is not a number: " + args[0]);
            System.exit(-2);
        }

        try {
            IController controller = new Controller(CORE_ADDR,corePort);
            it.dani.selfhome.automation.icontroller.IController autoController = new it.dani.selfhome.automation.controller.Controller(CORE_ADDR,4000);

            SocketListener socketListener = new SocketListener(port,controller,autoController);
            socketListener.start();
            System.out.println("Start Server.middle");

            Executor executor = new Executor(autoController);
            executor.start();
            System.out.println("Start Automations.service");

            WhereAreYouListener wayListener = new WhereAreYouListener(InetAddress.getByName("229.255.255.250"),4444,port);
            wayListener.start();
            System.out.println("Start WAY.service");

            executor.join();
            socketListener.join();
            wayListener.join();

        } catch (InterruptedException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
