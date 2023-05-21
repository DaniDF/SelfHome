package it.dani.selfhome.listener;

import it.dani.selfhome.icontroller.IController;
import it.dani.selfhome.listener.parser.DFLiteBaseVisitor;
import it.dani.selfhome.listener.parser.DFLiteLexer;
import it.dani.selfhome.listener.parser.DFLiteParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketListener extends Thread {

    private final int listenPort;
    private final IController controller;
    private final it.dani.selfhome.automation.icontroller.IController autoController;

    public SocketListener(int listenPort, @NotNull IController controller, @NotNull it.dani.selfhome.automation.icontroller.IController autoController)
    {
        this.listenPort = listenPort;
        this.controller = controller;
        this.autoController = autoController;
    }

    @Override
    public void run()
    {
        try(DatagramSocket sock = new DatagramSocket(this.listenPort))
        {
            sock.setReuseAddress(true);

            while(true)
            {
                try
                {
                    byte[] buffer = new byte[2048];
                    DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
                    Arrays.fill(buffer,(byte)0);
                    pack.setData(buffer);
                    sock.receive(pack);

                    Runnable handler = () -> {
                        try {
                            byte[] receved = Arrays.copyOf(pack.getData(), pack.getLength());

                            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(receved));
                            String response = this.handleRequest(dis);
                            dis.close();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
                            PrintWriter pw = new PrintWriter(baos);
                            pw.print(response + "\0");
                            pw.flush();
                            pack.setData(baos.toByteArray());
                            pw.close();
                            sock.send(pack);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };

                    Thread handlerThread = new Thread(handler);
                    handlerThread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private String handleRequest(InputStream in) throws IOException {
        Optional<Object> result;

        try {
            CharStream charStream = CharStreams.fromStream(in);
            DFLiteLexer lexer = new DFLiteLexer(charStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            DFLiteParser parser = new DFLiteParser(tokenStream);
            ParseTree tree = parser.program();
            DFLiteBaseVisitor visitor = new DFLiteBaseVisitor(this.controller);
            result = visitor.visit(tree);
        } catch (IOException e) {
            result = Optional.empty();
        }

        return (result.isPresent())? result.get().toString():"ERR";
    }
}
