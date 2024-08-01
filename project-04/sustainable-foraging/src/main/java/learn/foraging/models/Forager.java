package learn.foraging.models;

import java.util.Objects;

public class Forager {

    private String id;
    private String firstName;
    private String lastName;
    private String state;

    public Forager(Forager toAdd) {
        this.id = toAdd.getId();
        this.firstName = toAdd.getFirstName();
        this.lastName = toAdd.getLastName();
        this.state = toAdd.getState();
    }

    public Forager() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forager forager = (Forager) o;
        return id.equals(forager.id) && firstName.equals(forager.firstName) && lastName.equals(forager.lastName) && state.equals(forager.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, state);
    }
}
