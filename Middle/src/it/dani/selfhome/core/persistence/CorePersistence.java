package it.dani.selfhome.core.persistence;

import it.dani.selfhome.ipersistence.IDevicePersistence;
import it.dani.selfhome.ipersistence.IDeviceStatePersistence;
import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.TreeSet;

public class CorePersistence implements IDevicePersistence, IDeviceStatePersistence {
    private static InetAddress ADDRESS;
    private static int PORT;

    public static CorePersistence getCorePersistence(@NotNull String addr, int port) throws UnknownHostException {
        if(port < 0 || port > 65535) throw new IllegalArgumentException("Error: invalid port value");

        ADDRESS = InetAddress.getByName(addr);
        PORT = port;

        return new CorePersistence();
    }

    private CorePersistence()
    {

    }

    @Override
    public @NotNull Set<Device> listDevice() {
        Set<Device> result = new TreeSet<>();

        try(Socket sock = new Socket(ADDRESS,PORT))
        {
            sock.setSoTimeout(1000);
            InputStreamReader sockIn = new InputStreamReader(sock.getInputStream());
            OutputStreamWriter sockOut = new OutputStreamWriter(sock.getOutputStream());

            try
            {
                sockOut.write("LST\0");
                sockOut.flush();

                char car;
                String line = "";
                while((car = (char)sockIn.read()) > 0)
                {
                    if(car != '\n') line += car;
                    else
                    {
                        result.add(new Device(line));
                        line = "";
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            sock.shutdownOutput();

        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public DeviceState getState(Device device) {
        DeviceState result = new DeviceState((short)0);
        try {
            String line = this.communicate("GET;" + device.getName() + "\0");
            result = new DeviceState(Integer.parseInt(line));

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: bad response => " + e.getMessage());
        }

        return result;
    }

    @Override
    public boolean setState(@NotNull Device device) {
        boolean result = false;

        try {
            String line = this.communicate("SET;" + device.getName() + ";" + device.getState().getState() + "\0");
            result = line.equals("OK");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: bad response => " + e.getMessage());
        }

        return result;
    }

    private String communicate(String str) throws IOException {
        String result = "";

        try(DatagramSocket sock = new DatagramSocket()) {
            byte[] buffer = new byte[128];
            DatagramPacket pack = new DatagramPacket(buffer, buffer.length, ADDRESS, PORT);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(baos);
            osw.write(str);
            osw.flush();

            pack.setData(baos.toByteArray());
            sock.send(pack);
            sock.setSoTimeout(3000);
            pack.setData(buffer);
            sock.receive(pack);
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(pack.getData()));

            char car;
            while ((car = (char) isr.read()) > 0) {
                result += car;
            }

        } catch (IOException e){
            throw e;
        }

        return result;
    }
}
