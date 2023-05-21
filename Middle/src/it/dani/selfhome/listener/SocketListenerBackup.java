package it.dani.selfhome.listener;

import it.dani.selfhome.automation.model.Automation;
import it.dani.selfhome.icontroller.IController;
import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.DeviceState;
import it.dani.selfhome.model.Group;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SocketListenerBackup extends Thread {
/*
    private final Map<String, Function<String,String>> possibleCommands;

    private final int listenPort;
    private final IController controller;
    private final it.dani.selfhome.automation.icontroller.IController autoController;

    public SocketListenerBackup(int listenPort, @NotNull IController controller, @NotNull it.dani.selfhome.automation.icontroller.IController autoController)
    {
        this.listenPort = listenPort;
        this.controller = controller;
        this.autoController = autoController;

        possibleCommands = new TreeMap<>();
        possibleCommands.put("GET;",this::get); //Get device state
        possibleCommands.put("SET;",this::set); //Set device state
        possibleCommands.put("LST",this::list);//Get device list
        possibleCommands.put("LGR",this::listGroup);    //Get group list
        possibleCommands.put("NGR;",this::newGroup); //Create new group
        possibleCommands.put("DGR;",this::deleteGroup); //Delete group
        possibleCommands.put("ADG;",this::addDeviceGroup);   //Add device to group
        possibleCommands.put("DDG;",this::delDeviceGroup);   //Delete device from group
        possibleCommands.put("LDG;",this::listDeviceGroup);   //List device in a group
        possibleCommands.put("NAU;",this::newAutomation);    //New automation
        possibleCommands.put("AAU;",this::addAutomation);   //Add automation
        possibleCommands.put("DAU;",this::delAutomation);   //Del automation
        possibleCommands.put("LAU",this::listAutomation);   //List automation
    }

    @Override
    public void run()
    {
        try(DatagramSocket sock = new DatagramSocket(this.listenPort))
        {
            sock.setReuseAddress(true);
            byte[] buffer = new byte[2048];
            DatagramPacket pack = new DatagramPacket(buffer, buffer.length);

            while(true)
            {
                try
                {
                    Arrays.fill(buffer,(byte)0);
                    pack.setData(buffer);
                    sock.receive(pack);

                    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(pack.getData()));
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
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private String handleRequest(InputStream in) throws IOException {
        String[] request = new String[]{""};
        char car;
        while((car = (char)in.read()) > 0)
        {
            request[0] += car;
        }

        System.out.println("Received (middle): " + request[0]);

        Optional<String> cmd = possibleCommands.keySet()
                                               .stream()
                                               .filter(x -> request[0].startsWith(x))
                                               .findAny();
        String result;
        if(cmd.isPresent())
        {
            result = possibleCommands.get(cmd.get())
                                     .apply(request[0].substring(cmd.get().length()).trim());
        }
        else result = "ERR";

        return result;
    }

    private String get(String request)
    {
        String result = "ERR";
        String[] requestPart = request.split(";");

        if (requestPart.length == 2 && requestPart[0].equals("DISP")) {
            result = this.controller.getState(requestPart[1]) + "";
        }

        return (result.equals("null"))? "ERR" : result;
    }

    private String set(String request)
    {
        boolean result = false;
        String[] requestPart = request.split(";");

        try
        {
            int state = Integer.parseInt(requestPart[requestPart.length - 1]);

            if (requestPart.length == 3 && requestPart[0].equals("DISP")) {
                result = this.controller.setState(requestPart[1], new DeviceState(state));
            }
            else if (requestPart.length == 3 && requestPart[0].equals("GRUP"))
            {
                result = this.controller.setStateGroup(requestPart[1], new DeviceState(state));
            }

        } catch (IllegalArgumentException e) { }

        return (result)? "OK" : "ERR";
    }

    private String list(String s) {
        return this.formatString(this.controller.getDevices(), Device::getName);
    }

    private String listGroup(String s) {
        return this.formatString(this.controller.getGroups(), Group::getName);
    }

    private String newGroup(String s) {
        return (this.controller.addNewGroup(s.trim()))? "OK" : "ERR";
    }

    private String deleteGroup(String s) {
        return (this.controller.delGroup(s.trim()))? "OK" : "ERR";
    }

    private String addDeviceGroup(String s) {   //Device_name;Group_name
        String[] requestPart = s.split(";");
        boolean flagErr = true;

        if(requestPart.length == 2)
        {
            flagErr = this.controller.addDeviceToGroup(requestPart[0].trim(),requestPart[1].trim());
        }

        return (flagErr)? "OK" : "ERR";
    }

    private String delDeviceGroup(String s) {   //Device_name;Group_name
        String[] requestPart = s.split(";");
        boolean flagErr = true;

        if(requestPart.length == 2)
        {
            flagErr = this.controller.delDeviceFromGroup(requestPart[0].trim(),requestPart[1].trim());
        }

        return (flagErr)? "OK" : "ERR";
    }

    private String listDeviceGroup(String s) {
        s = s.trim();
        Set<Device> devices = new TreeSet<>();

        for(Group g : this.controller.getGroups())
        {
            if(g.getName().equals(s))
            {
                devices.addAll(g.getDevices());
            }
        }

        return this.formatString(devices, Device::getName);
    }

    private String newAutomation(String s)
    {
        return ((s.length() > 0) && this.autoController.addNewAutomation(new Automation(s)))? "OK" : "ERR";
    }

    private String addAutomation(String s)  //name;dow;HH;MM;SS;dow;HH;MM;SS;device_name;set
    {
        String[] requestPart = s.split(";");
        boolean flagErr = false;

        if(requestPart.length == 11)
        {
            try
            {
                DayOfWeek startDow = DayOfWeek.valueOf(requestPart[1]);
                int startH = Integer.parseInt(requestPart[2]);
                int startM = Integer.parseInt(requestPart[3]);
                int startS = Integer.parseInt(requestPart[4]);
                DayOfWeek finishDow = DayOfWeek.valueOf(requestPart[5]);
                int finishH = Integer.parseInt(requestPart[6]);
                int finishM = Integer.parseInt(requestPart[7]);
                int finishS = Integer.parseInt(requestPart[8]);
                short state = Short.parseShort(requestPart[10]);

                if(requestPart[0].length() == 0 ||
                        startH < 0 || startH > 23 ||
                        startM < 0 || startM > 23 ||
                        startS < 0 || startS > 23 ||
                        finishH < 0 || finishH > 23 ||
                        finishM < 0 || finishM > 23 ||
                        finishS < 0 || finishS > 23 ||
                        requestPart[9].length() == 0 ||
                        state < 0)
                {
                    flagErr = true;
                }

                Set<Device> devices = null;

                if(!flagErr)
                {
                    devices = this.controller.getDevices().stream()
                                                          .filter(x -> x.getName().equals(requestPart[9]))
                                                          .collect(Collectors.toSet());

                    if(devices.size() == 0) flagErr = true;
                }

                if(!flagErr)
                {
                    Automation automation = new Automation(requestPart[0].trim());
                    automation.setDevicesTarget(devices);
                    automation.setToSet(state);



                    flagErr = !this.autoController.addNewAutomation(automation);
                }

            } catch (IllegalArgumentException e) {
                flagErr = true;
            }
        }
        else flagErr = true;

        return (!flagErr)? "OK" : "ERR";
    }

    private String delAutomation(String s)
    {
        boolean flagErr = true;

        if(s.length() > 0)
        {
            List<Automation> automations = this.autoController.getAutomations(x -> x.getName().equals(s))
                                                              .stream()
                                                              .collect(Collectors.toList());

            if(automations.size() == 1)
            {
                flagErr = !this.autoController.delAutomation(automations.get(0));
            }
        }
        return (!flagErr)? "OK" : "ERR";
    }

    private String listAutomation(String s)
    {
        return this.formatString(this.autoController.getAutomations(x -> true),Automation::getName);
    }

    private <T> String formatString(Set<T> set, Function<T,String> function)
    {
        String result = "";

        for(T i : set)
        {
            if(result.length() != 0) result += ";";
            result += function.apply(i);
        }

        return result;
    }
 */
}
