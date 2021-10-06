package pl.danielkolban.gameshop.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable, CsvConvertible{
    private String firstName;
    private String lastName;
    private String login;

    public User(String firstName, String lastName, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, login);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + login;
    }
}
