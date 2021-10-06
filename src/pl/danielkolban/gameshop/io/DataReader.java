package pl.danielkolban.gameshop.io;

import pl.danielkolban.gameshop.exceptions.UnidentifiedProductConditionException;
import pl.danielkolban.gameshop.model.ConsolesAndAccessories;
import pl.danielkolban.gameshop.model.Employee;
import pl.danielkolban.gameshop.model.Game;
import pl.danielkolban.gameshop.model.User;

import java.util.Locale;
import java.util.Scanner;

public class DataReader {
    Scanner input = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public int getInt() {
        try {
            return input.nextInt();
        }finally {
            input.nextLine();
        }
    }

    public double getDouble() {
        try {
            return input.nextDouble();
        }finally {
            input.nextLine();
        }
    }

    public String getString() {
        return input.nextLine();
    }

    public void close() {
        input.close();
    }

    public Game readAndCreateGame() {
        printer.printLine("Platforma:");
        String platform = getString();
        printer.printLine("Tytuł:");
        String title = getString();
        printer.printLine("Stan:");
        boolean newOrUsed = getBooleanFromString();
        printer.printLine("Język okładki:");
        String coverLanguage = getString();
        printer.printLine("Język gry:");
        String gameLanguage = getString();
        printer.printLine("Ilość:");
        int quantity = getInt();
        printer.printLine("Cena zakupu:");
        double purchasePrice = getDouble();
        printer.printLine("Cena sprzedaży:");
        double sellingPrice = getDouble();
        return new Game(platform, title, coverLanguage, gameLanguage, purchasePrice, sellingPrice, newOrUsed, quantity);
    }

    public ConsolesAndAccessories readAndCreateConsoleOrAccessories() {
        printer.printLine("Kategoria");
        String platform = getString();
        printer.printLine("Nazwa produktu:");
        String title = getString();
        printer.printLine("Stan:");
        boolean newOrUsed = getBooleanFromString();
        printer.printLine("Ilość:");
        int quantity = getInt();
        printer.printLine("Cena zakupu:");
        double purchasePrice = getDouble();
        printer.printLine("Cena sprzedaży:");
        double sellingPrice = getDouble();
        return new ConsolesAndAccessories(platform, title,  purchasePrice, sellingPrice, newOrUsed, quantity);
    }

    public Employee readAndCreateEmployee() {
        printer.printLine("Imię:");
        String firstName = getString();
        printer.printLine("Nazwisko:");
        String lastName = getString();
        printer.printLine("Login:");
        String login = getString();
        printer.printLine("Ilość:");
        return new Employee(firstName, lastName, login);
    }

    private boolean getBooleanFromString() {
        boolean newOrUsed = false;
        String fromUser = getString().toLowerCase();
        if (fromUser.equals("nowy")) {
            newOrUsed = true;
        }else if (fromUser.equals("używany")){
            newOrUsed = false;
        }else {
            throw new UnidentifiedProductConditionException("Nieznany stan produktu");
        }
        return newOrUsed;

    }
}
