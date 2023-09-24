package main.entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.entities.Containers.ContainerType;

public class Admin extends User {
    //constructor
    public Admin(String username, String password) {
        super(username, password);
        super.setUserRole("Admin");
    }

    //method
    @Override
    public boolean hasPermission(Operation operation) {
        // Admin has permission for all operations
        return true;
    }
}
