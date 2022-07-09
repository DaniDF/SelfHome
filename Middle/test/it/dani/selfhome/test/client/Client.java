package it.dani.selfhome.test.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args)
    {
        try
        {
            if(args.length != 2) throw new IllegalArgumentException("Invalid arguments");

            InetAddress address = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);

            InputStream in = System.in;

            StringBuilder bufferS = new StringBuilder();
            int car;
            while((car = in.read()) > 0)
            {
                bufferS.append((char) car);
            }

            DatagramSocket sock = new DatagramSocket();
            byte[] buffer = bufferS.toString().getBytes();
            DatagramPacket pack = new DatagramPacket(buffer, buffer.length,address,port);
            sock.send(pack);

            sock.receive(pack);
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(pack.getData()));

            while ((car = isr.read()) > 0) {
                System.out.print((char) car);
            }

            System.out.println();

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
