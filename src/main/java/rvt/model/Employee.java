package rvt.model;

public class Employee {
    
    int id;
    String name;
    String surname;
    String position;
    String username;
    String password;
    String role;

    public Employee(int id, String name, String surname, String position, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //setter methods//
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStatId(String position) {
        this.position = position;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //getter methods//
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return name;
    }
}
