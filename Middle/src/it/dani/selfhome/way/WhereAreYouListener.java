package it.dani.selfhome.way;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

public class WhereAreYouListener extends Thread {

    private final InetAddress address;
    private final int listenPort;
    private final int servicePort;

    public WhereAreYouListener(@NotNull InetAddress address, int listenPort, int servicePort)
    {
        this.address = address;
        this.listenPort = listenPort;
        this.servicePort = servicePort;
    }

    @Override
    public void run()
    {
        try(MulticastSocket sock = new MulticastSocket(this.listenPort))
        {
            sock.setReuseAddress(true);
            sock.setSoTimeout(0);
            sock.joinGroup(this.address);
            byte[] buffer = new byte[2048];
            DatagramPacket pack = new DatagramPacket(buffer, buffer.length);

            while(true)
            {
                try
                {
                    Arrays.fill(buffer,(byte)0);
                    pack.setData(buffer);
                    sock.receive(pack);
                    System.out.println("Received (middle): WAY from " + pack.getAddress().getHostAddress());

                    String response = this.handleRequest(pack);

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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handleRequest(DatagramPacket pack) throws UnknownHostException, SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        String result = null;

        while(result == null && interfaces.hasMoreElements())
        {
            NetworkInterface networkInterface = interfaces.nextElement();

            for(InterfaceAddress ia : networkInterface.getInterfaceAddresses())
            {
                if(result == null && isInSameNet(pack.getAddress(),ia))
                {
                    result = ia.getAddress().getHostAddress();
                }
            }
        }

        return ((result == null)? InetAddress.getLocalHost().getHostAddress() : result) + ":" + this.servicePort;
    }

    private boolean isInSameNet(InetAddress addr, InterfaceAddress interfaceAddress)
    {
        boolean result = true;

        for(int count = 0; result && count < addr.getAddress().length && count < interfaceAddress.getNetworkPrefixLength()/8; count++)
        {
            result = (addr.getAddress()[count] == interfaceAddress.getAddress().getAddress()[count]);
        }

        return result;
    }
}
