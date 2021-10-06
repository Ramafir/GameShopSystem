package pl.danielkolban.gameshop.model;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {
    private List<Product> sellingHistory = new ArrayList<>();

    public List<Product> getSellingHistory() {
        return sellingHistory;
    }

    public Employee(String firstName, String lastName, String login) {
        super(firstName, lastName, login);
    }

    public String toCsv() {
        return getFirstName() + ";" + getLastName() + ";" + getLogin();
    }
}
