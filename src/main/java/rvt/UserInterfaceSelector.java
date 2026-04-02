package rvt;

public class UserInterfaceSelector {
    public static void main(String[] args){
        Database.createTables();

        UserInterfaces gui = new UserInterfaces();
        gui.userSelect();
    }
}