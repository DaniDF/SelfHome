package it.dani.selfhomeapp.middle.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import it.dani.selfhomeapp.middle.ipersistence.IDevicePersistence;
import it.dani.selfhomeapp.middle.ipersistence.IGroupPersistence;

public class Persistence implements IDevicePersistence, IGroupPersistence {
    private static InetAddress ADDRESS;
    private static int PORT;

    public static Persistence getPersitence(@NotNull String addr, int port) throws UnknownHostException {
        if(port < 0 || port > 65535) throw  new IllegalArgumentException("Error: invalid port value");
        ADDRESS = InetAddress.getByName(addr);
        PORT = port;

        return new Persistence();
    }

    @NonNull
    @Override
    public String freeText(@NonNull String text)
    {
        String result;

        try
        {
            result = this.communicate(text,PersistenceTimeout.INFINITE.valueOf());

        } catch (IOException e){
            Log.e("getDeviceFromGroup","Error: " + e.getMessage());
            result = "";
        }

        return result;
    }

    @Override
    public boolean toggleDevice(@NonNull String deviceName)
    {
        boolean result = false;

        try
        {
            String resultS = this.communicate("SET DISP <" + deviceName + "> !(GET DISP <" + deviceName + ">);");
            if(resultS != null)
            {
                result = Boolean.parseBoolean(resultS);
            }

        } catch (IOException | NumberFormatException e){
            Log.e("getDeviceFromGroup","Error: " + e.getMessage());
            result = false;
        }

        return result;
    }

    @Override
    public Set<String> getDevices() {
        Set<String> result = new TreeSet<>();

        try {
            String response = this.communicate("LST");

            if(response.length() > 0)
            {
                result.addAll(Arrays.asList(response.split(";")));
            }

        } catch (SocketTimeoutException e) {
            throw new SelfHomePersistenceException(e);

        } catch (IOException e){
            Log.e("getDevice","Error: " + e.getMessage());
        }

        return result;
    }

    @Override
    public Set<String> getGroups() {
        Set<String> result = new TreeSet<>();

        try
        {
            String response = this.communicate("LGR");

            if(response.length() > 0)
            {
                result.addAll(Arrays.asList(response.split(";")));
            }

        } catch (SocketTimeoutException e) {
            throw new SelfHomePersistenceException(e);

        } catch (IOException e){
            Log.e("getGroups","Error: " + e.getMessage());
        }

        return result;
    }

    @Override
    public Set<String> getDeviceFromGroup(@NonNull String groupName) {
        Set<String> result = new TreeSet<>();

        try
        {
            String response = this.communicate("LDG <" + groupName + ">");

            if(response.length() > 0)
            {
                result.addAll(Arrays.asList(response.split(";")));
            }

        } catch (IOException e){
            Log.e("getDeviceFromGroup","Error: " + e.getMessage());
        }

        return result;
    }

    @Override
    public boolean addDeviceToGroup(@NonNull String deviceName, @NonNull String groupName) {
        boolean flagErr = true;

        try
        {
            //flagErr = !this.communicate("ADG <" + deviceName + "> <" + groupName + ">").equals("OK");
            flagErr = !Boolean.parseBoolean(this.communicate("ADG <" + deviceName + "> <" + groupName + ">;"));
        } catch (IOException e) {
            Log.e("addDeviceToGroup","Error: " + e.getMessage());
        }

        return !flagErr;
    }

    @Override
    public boolean delDeviceToGroup(@NonNull String deviceName, @NonNull String groupName) {
        boolean flagErr = true;

        try
        {
            //flagErr = !this.communicate("DDG <" + deviceName + "> <" + groupName + ">").equals("OK");
            flagErr = !Boolean.parseBoolean(this.communicate("DDG <" + deviceName + "> <" + groupName + ">;"));
        } catch (IOException e) {
            Log.e("delDeviceToGroup","Error: " + e.getMessage());
        }

        return !flagErr;
    }

    @Override
    public boolean setStateDevice(@NonNull String deviceName, int state) {
        boolean flagErr = true;

        try
        {
            //flagErr = !this.communicate("SET DISP <" + deviceName + "> " + state).equals("OK");
            flagErr = !Boolean.parseBoolean(this.communicate("SET DISP <" + deviceName + "> " + state + ";"));
        } catch (IOException e) {
            Log.e("setDeviceState","Error: " + e.getMessage());
        }

        return !flagErr;
    }

    @Override
    public boolean setStateGroup(@NonNull String groupName, int state) {
        boolean flagErr = true;

        try
        {
            //flagErr = !this.communicate("SET GRUP <" + groupName + "> <" + state + ">").equals("OK");
            flagErr = !Boolean.parseBoolean(this.communicate("SET GRUP <" + groupName + "> <" + state + ">;"));
        } catch (IOException e) {
            Log.e("setStateGroup","Error: " + e.getMessage());
        }

        return !flagErr;
    }

    @Override
    public boolean addNewGroup(@NonNull String groupName) {
        boolean flagErr = true;

        try
        {
            //flagErr = !this.communicate("NGR <" + groupName + ">").equals("OK");
            flagErr = !Boolean.parseBoolean(this.communicate("NGR <" + groupName + ">;"));
        } catch (IOException e) {
            Log.e("addNewGroup","Error: " + e.getMessage());
        }

        return !flagErr;
    }

    @Override
    public boolean delGroup(@NonNull String groupName)
    {
        boolean flagErr = true;

        try
        {
            //flagErr = !this.communicate("DGR <" + groupName + ">").equals("OK");
            flagErr = !Boolean.parseBoolean(this.communicate("DGR <" + groupName + ">;"));
        } catch (IOException e) {
            Log.e("delGroup","Error: " + e.getMessage());
        }

        return !flagErr;
    }

    @Override
    public int getDeviceState(@NonNull String deviceName) {
        int result;

        try
        {
            String resultS = this.communicate("GET DISP <" + deviceName + ">;");
            if(resultS.equals("OFF")) result = 0;
            else result = Integer.parseInt(resultS);

        } catch (IOException | NumberFormatException e) {
            Log.e("getDeviceState","Error: " + e.getMessage());

            throw new SelfHomePersistenceException(e);
        }

        return result;
    }

    private String communicate(@NonNull String str) throws IOException
    {
        return this.communicate(str,PersistenceTimeout.STANDARD.valueOf());
    }

    private String communicate(@NonNull String str, int timeout) throws IOException
    {
        StringBuilder result = new StringBuilder();

        try(DatagramSocket sock = new DatagramSocket())
        {
            byte[] buffer = new byte[1024];
            DatagramPacket pack = new DatagramPacket(buffer,buffer.length,ADDRESS,PORT);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(baos);
            osw.write(str);
            osw.flush();
            pack.setData(baos.toByteArray());

            sock.send(pack);
            sock.setSoTimeout(timeout);

            pack.setData(buffer);
            sock.receive(pack);
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(pack.getData()));

            char car;
            while((car = (char)isr.read()) > 0)
            {
                result.append(car);
            }

        }

        return result.toString();
    }
}
