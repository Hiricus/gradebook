package users;

public class Administrator extends User {
    public Administrator(int id) {
        super(id);
    }

    public Administrator(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }
}
