package pl.danielkolban.gameshop.app;

import pl.danielkolban.gameshop.exceptions.DataExportException;
import pl.danielkolban.gameshop.exceptions.DataImportException;
import pl.danielkolban.gameshop.exceptions.UnidentifiedProductConditionException;
import pl.danielkolban.gameshop.io.file.FileManagerBuilder;
import pl.danielkolban.gameshop.exceptions.NoSuchOptionException;
import pl.danielkolban.gameshop.io.ConsolePrinter;
import pl.danielkolban.gameshop.io.DataReader;
import pl.danielkolban.gameshop.io.file.FileManager;
import pl.danielkolban.gameshop.model.*;

import java.util.Comparator;
import java.util.InputMismatchException;

public class GameShopController {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;
    private Shop shop;

    public GameShopController() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            shop = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku");
        }catch (DataImportException e){
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę.");
            shop = new Shop();
        }
    }

    void controlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option){
                case ADD_GAME:
                    addGame();
                    break;
                case ADD_CONSOLE_OR_ACCESSORIES:
                    addConsoleOrAccessories();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_GAMES:
                    printGames();
                    break;
                case PRINT_CONSOLES_AND_ACCESSORIES:
                    printConsolesAndAccessories();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case PRINT_CASH_BALANCE:
                    printCashBalance();
                    break;
                case FIND_PRODUCT:
                    findProduct();
                    break;
                case SELL_GAME:
                    sellGame();
                    break;
                case SELL_CONSOLE_AND_ACCESSORIES:
                    sellConsoleAndAccessories();
                    break;
                case WITHDRAW_CASH:
                    withdrawCash();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie znaleziono takiej opcji");
            }

        }while (option != Option.EXIT);

        }

    private void sellConsoleAndAccessories() {
        try {
            ConsolesAndAccessories consolesAndAccessories = dataReader.readAndCreateConsoleOrAccessories();
            if (shop.sellProduct(consolesAndAccessories))
                printer.printLine("Sprzedano produkt " + consolesAndAccessories.getName());
            else
                printer.printLine("Brak wskazanego produktu w magazynie.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć konsoli lub akcesoria, niepoprawne dane");
        }
    }

    private void sellGame() {
        try {
            Game game = dataReader.readAndCreateGame();
            if (shop.sellProduct(game))
                printer.printLine("Sprzedano grę " + game.getName());
            else
                printer.printLine("Brak wskazanej gry w magazynie.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć gry, niepoprawne dane");
        }

    }

    private void addUser() {
        try {
            Employee employee = dataReader.readAndCreateEmployee();
            shop.addUser(employee);
        }catch (InputMismatchException e) {
            printer.printLine("Nie udało się dodać użytkownika, niepoprawne dane");
        }
    }

    private void addConsoleOrAccessories() {
        try {
            ConsolesAndAccessories consoleOrAccessories = dataReader.readAndCreateConsoleOrAccessories();
            shop.addProduct(consoleOrAccessories);
        }catch (InputMismatchException e){
            printer.printLine("Nie udało się dodać konsoli lub akcesoriów, niepoprawne dane");
        }
    }

    private void addGame() {
        try {
            Game game = dataReader.readAndCreateGame();
            shop.addProduct(game);
        }catch (InputMismatchException e){
            printer.printLine("Nie udało się utworzyć gry, niepoprawne dane");
        }catch (UnidentifiedProductConditionException e){
            printer.printLine(e.getMessage());
        }
    }

    private void printGames() {
        printer.printGames(shop.getSortedProducts(
                Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
        ));

    }

    private void printConsolesAndAccessories() {
        printer.printConsolesAndAccessories(shop.getSortedProducts(
                Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void printUsers() {
        printer.printEmployees(shop.getSortedUsers(
                Comparator.comparing(User::getLogin, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void printCashBalance() {
        printer.printLine(shop.getCash() + "zł");
    }

    private void withdrawCash() {
        printer.printLine("Ile pieniędzy chcesz wypłacić?");
        double cashToWithdraw = dataReader.getDouble();
        shop.withdrawCash(cashToWithdraw);
        printer.printLine("Wypłacono " + cashToWithdraw + "zł, w kasie pozostało: " + shop.getCash() + "zł");
    }

    private void findProduct() {
        printer.printLine("Podaj nazwę produktu:");
        String name = dataReader.getString();
        String notFoundMessage = "Nie znaleziono produktu o takiej nazwie";
        shop.findProductByName(name)
                .map(Product::toString)
                .ifPresentOrElse(System.out::println, () -> printer.printLine(notFoundMessage));

    }

    private void exit() {
        try {
            fileManager.exportData(shop);
            printer.printLine("Export danych do pliku zakończony powodzeniem");
        }catch (DataExportException e){
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("Koniec programu, papa!");
    }

    private Option getOption() {
         boolean optionOk = false;
         Option option = null;
         while (!optionOk){
             try {
                 option = createFromInt(dataReader.getInt());
                 optionOk = true;
             }catch (NoSuchOptionException e){
                 printer.printLine(e.getMessage() + ", podaj ponownie");
             }catch (InputMismatchException ignored){
                 printer.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
             }
         }
         return option;
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję:");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private enum Option{
        EXIT(0, "Wyjście"),
        ADD_GAME(1, "Dodaj grę"),
        ADD_CONSOLE_OR_ACCESSORIES(2, "Dodaj konsolę lub akcesoria"),
        ADD_USER(3, "Dodaj użytkownika"),
        PRINT_GAMES(4, "Wyświetl dostępne gry"),
        PRINT_CONSOLES_AND_ACCESSORIES(5, "Wyświetl dostępne konsole i akcesoria"),
        PRINT_USERS(6, "Wyświetl użytkowników"),
        PRINT_CASH_BALANCE(7, "Wyświetl stan kasy"),
        FIND_PRODUCT(8, "Znajdź produkt"),
        SELL_GAME(9, "Sprzedaj grę"),
        SELL_CONSOLE_AND_ACCESSORIES(10, "Sprzedaj konsolę lub akcesoria"),
        WITHDRAW_CASH(11, "Wypłać pieniądze");

        private final int option;

        private final String description;
        Option(int option, String description) {
            this.option = option;
            this.description = description;
        }

        @Override
        public String toString() {
            return option + "- " + description;
        }
    }
    private Option createFromInt(int option) throws NoSuchOptionException {
         try {
             return Option.values()[option];
         }catch (ArrayIndexOutOfBoundsException e) {
             throw new NoSuchOptionException("Brak opcji o id " + option);
         }

    }
}
