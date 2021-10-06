package pl.danielkolban.gameshop.io.file;

import pl.danielkolban.gameshop.exceptions.DataExportException;
import pl.danielkolban.gameshop.exceptions.DataImportException;
import pl.danielkolban.gameshop.model.Shop;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private final static String FILE_NAME = "Shop.o";
    @Override
    public Shop importData() {
        try (
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
                ){
            return (Shop) ois.readObject();

        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("Błąd odczytu danych z pliku " + FILE_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Niezgodny typ danych w pliku " + FILE_NAME);
        }

    }

    @Override
    public void exportData(Shop shop) {
        try (
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ){
            oos.writeObject(shop);
        } catch (FileNotFoundException e) {
            throw new DataExportException("Brak pliku " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + FILE_NAME);
        }


    }
}
