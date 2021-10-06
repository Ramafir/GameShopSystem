package pl.danielkolban.gameshop.io.file;

import pl.danielkolban.gameshop.exceptions.DataExportException;
import pl.danielkolban.gameshop.exceptions.DataImportException;
import pl.danielkolban.gameshop.exceptions.InvalidDataException;
import pl.danielkolban.gameshop.model.*;

import java.io.*;
import java.util.Collection;

public class CsvFileManager implements FileManager {
    private final static String PRODUCTS_FILE_NAME = "Shop.csv";
    private final static String USERS_FILE_NAME = "ShopUsers.csv";
    @Override
    public void exportData(Shop shop) {
        exportProducts(shop);
        exportUsers(shop);
    }

    @Override
    public Shop importData() {
        Shop shop = new Shop();
        importProducts(shop);
        importUsers(shop);
        return shop;
    }

    private void importUsers(Shop shop) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createUserFromString)
                    .forEach(shop::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + USERS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + USERS_FILE_NAME);
        }
    }

    private void importProducts(Shop shop) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PRODUCTS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createObjectFromString)
                    .forEach(shop::addProduct);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + PRODUCTS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + PRODUCTS_FILE_NAME);
        }
    }

    private void exportUsers(Shop shop) {
        Collection<Employee> users = shop.getUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }

    private void exportProducts(Shop shop) {
        Collection<Product> products = shop.getProducts().values();
        exportToCsv(products, PRODUCTS_FILE_NAME);
    }

    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + fileName);
        }
    }

    private Product createObjectFromString(String csvText) {
        String[] split = csvText.split(";");
        String type = split[0];
        if(Game.TYPE.equals(type)) {
            return createBook(split);
        } else if(ConsolesAndAccessories.TYPE.equals(type)) {
            return createConsoleOrAccessories(split);
        }
        throw new InvalidDataException("Nieznany typ publikacji: " + type);
    }

    private Product createConsoleOrAccessories(String[] data) {
        String category = data[1];
        String title = data[2];
        double purchasePrice = Double.parseDouble(data[3]);
        double sellingPrice = Double.parseDouble(data[4]);
        boolean newOrUsed = Boolean.parseBoolean(data[5]);
        int quantity = Integer.parseInt(data[6]);
        return new ConsolesAndAccessories(category, title, purchasePrice, sellingPrice, newOrUsed, quantity);
    }

    private Game createBook(String[] data) {
        String category = data[1];
        String title = data[2];
        String coverLanguage = data[3];
        String gameLanguage = data[4];
        double purchasePrice = Double.parseDouble(data[5]);
        double sellingPrice = Double.parseDouble(data[6]);
        boolean newOrUsed = Boolean.parseBoolean(data[7]);
        int quantity = Integer.parseInt(data[8]);
        return new Game(category, title, coverLanguage, gameLanguage, purchasePrice, sellingPrice, newOrUsed, quantity);
    }

    private Employee createUserFromString(String csvText) {
        String[] split = csvText.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String login = split[2];
        return new Employee(firstName, lastName, login);
    }
}
