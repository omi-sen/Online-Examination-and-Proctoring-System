package model;

public class Admin extends User {
    public Admin() {
        setRole("ADMIN");
    }

    public Admin(int id, String username, String password) {
        super(id, username, password, "ADMIN");
    }
}
