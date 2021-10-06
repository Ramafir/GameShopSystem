package pl.danielkolban.gameshop.model;

import pl.danielkolban.gameshop.exceptions.UserAlreadyExistException;

import java.io.Serializable;
import java.util.*;

public class Shop implements Serializable {
    private double cash = 0;
    Map<String, Product> products = new HashMap<>();
    Map<String, Employee> employees = new HashMap<>();

    public Map<String, Product> getProducts() {
        return products;
    }

    public Map<String, Employee> getUsers() {
        return employees;
    }

    public double getCash() {
        return cash;
    }

    public Collection<Product> getSortedProducts(Comparator<Product> comparator) {
        ArrayList<Product> products = new ArrayList<>(this.products.values());
        products.sort(comparator);
        return products;
    }

    public Collection<Employee> getSortedUsers(Comparator<Employee> comparator) {
        ArrayList<Employee> employees = new ArrayList<>(this.employees.values());
        employees.sort(comparator);
        return employees;
    }

    public void addProduct(Product product) {
        if (products.containsKey(product.getName())){
            product.setQuantity(product.getQuantity() + 1);
            System.out.println("Zwiększono liczbę sztuk gry " + product.getName());
        }
        products.put(product.getName(), product);
    }

    public void addUser(Employee employee) {
        if (employees.containsKey(employee.getLogin())) {
            throw new UserAlreadyExistException("Użytkownik ze wskazanym loginem już istnieje " + employee.getLogin());
        }
        employees.put(employee.getLogin(), employee);
    }

    public Optional<Product> findProductByName(String title) {
        return Optional.ofNullable(products.get(title));
    }

    public boolean sellProduct(Product product) {
        if(products.containsValue(product)) {
            products.remove(product.getName());
            cash += product.getSellingPrice();
            return true;
        } else {
            return false;
        }
    }

    public double withdrawCash(double drawCash) {
        return cash -= drawCash;
    }

}
