package it.dani.selfhome.test.controller;

import it.dani.selfhome.icontroller.IController;
import it.dani.selfhome.model.Device;
import it.dani.selfhome.model.Group;

import java.io.IOException;

public class Controller {
    public static void main(String[] args)
    {
        try {
            IController controller = new it.dani.selfhome.controller.Controller("192.168.0.106",4000);

            for(Group g : controller.getGroups())
            {
                System.out.println(g);
            }

            for(Device d : controller.getDevices())
            {
                System.out.println(d);
            }

            controller.addNewGroup("Group1");
            controller.addNewGroup("Group2");

            for(Device d : controller.getDevices())
            {
                for(Group g : controller.getGroups())
                {
                    controller.addDeviceToGroup(d.getName(),g.getName());
                }
            }

            for(Group g : controller.getGroups())
            {
                System.out.println(g);
            }

            for(Device d : controller.getDevices())
            {
                System.out.println(d);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
