package it.dani.selfhome.test.core;

import it.dani.selfhome.model.Device;
import it.dani.selfhome.core.persistence.CorePersistence;

import java.net.UnknownHostException;
import java.util.Set;

public class Persistence {
    public static void main(String[] args)
    {
        try {
            CorePersistence devicePersistence = CorePersistence.getCorePersistence("192.168.0.106",4000);
            Set<Device> devices = devicePersistence.listDevice();

            for(Device d : devices)
            {
                d.setState(devicePersistence.getState(d));
                System.out.println(d);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
