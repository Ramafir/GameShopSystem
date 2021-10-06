package pl.danielkolban.gameshop.io;

import pl.danielkolban.gameshop.model.*;

import java.util.Collection;

public class ConsolePrinter {

    public void printGames(Collection<Product> games) {
        long count = games.stream()
                .filter(p -> p instanceof Game)
                .map(Product::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("Brak gier w magazynie");

    }

    public void printConsolesAndAccessories(Collection<Product> consolesAndAccessories) {
        long count = consolesAndAccessories.stream()
                .filter(p -> p instanceof ConsolesAndAccessories)
                .map(Product::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("Brak konsol i akcesoriów w magazynie");

    }

    public void printEmployees(Collection<Employee> employees) {
        employees.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text) {
        System.out.println(text);
    }

}
