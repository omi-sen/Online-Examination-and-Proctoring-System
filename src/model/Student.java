package model;

public class Student extends User {
    public Student() {
        setRole("STUDENT");
    }

    public Student(int id, String username, String password) {
        super(id, username, password, "STUDENT");
    }
}
