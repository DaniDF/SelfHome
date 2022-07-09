package it.dani.selfhomeapp.middle.controller;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class FindServer extends Thread implements Serializable {

    private static final String MULTICAST_ADDRESS = "229.255.255.250";
    private static final int MULTICAST_PORT = 4444;

    private String address;
    private int port;

    public FindServer()
    {
        this.address = null;
        this.port = -1;
    }

    @Override
    public void run()
    {
        try(MulticastSocket sock = new MulticastSocket()) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            sock.joinGroup(group);
            sock.setReuseAddress(true);
            sock.setSoTimeout(1000);

            byte[] buffer = new byte[128];
            DatagramPacket pack = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
            sock.send(pack);

            Arrays.fill(buffer, (byte) 0);
            pack.setData(buffer);
            sock.receive(pack);

            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(pack.getData()));
            String response = handleRequest(dis);
            dis.close();

            String[] responseP = response.split(":");
            if (responseP.length != 2) throw new IOException("Bad response: " + response);

            try {
                this.port = Integer.parseInt(responseP[1]);
                if (this.port <= 0) throw new IOException("Bad response: " + response);
            } catch (NumberFormatException e) {
                throw new IOException("Bad response: " + response, e);
            }

            this.address = responseP[0];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handleRequest(DataInputStream in) throws IOException {
        String request = "";
        char car;
        while((car = (char)in.read()) > 0)
        {
            request += car;
        }

        return request;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public String toString()
    {
        return this.address + ":" + this.port;
    }
}
