package main;

import java.util.ArrayList;
import java.util.List;

import main.entities.Admin;
import main.entities.PortManager;
import main.entities.User;

public class main { 
    public static void main(String[] args) {
        // Create PortManager users
        PortManager portManager1 = new PortManager("portmanager1", "password1", "PortA");
        PortManager portManager2 = new PortManager("portmanager2", "password2", "PortB");
        PortManager portManager3 = new PortManager("portmanager3", "password3", "PortC");
        // Create an Admin user
        Admin adminUser = new Admin("admin", "adminpassword");

        // Create a list to store all users (assuming User is a common interface or superclass)
        List<User> users = new ArrayList<>();

        // Add users to the list
        users.add(portManager1);
        users.add(portManager2);
        users.add(portManager3);
        users.add(adminUser);
}
}