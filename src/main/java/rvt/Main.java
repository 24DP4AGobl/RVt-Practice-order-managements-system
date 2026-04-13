package rvt;

import rvt.ui.LoginInterface;
import rvt.dao.Database;

public class Main {
    public static void main(String[] args) {
        new LoginInterface(); // start with login

        Database.createTables();
    }
}
