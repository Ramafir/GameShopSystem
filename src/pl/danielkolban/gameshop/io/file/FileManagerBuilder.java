package pl.danielkolban.gameshop.io.file;

import pl.danielkolban.gameshop.io.ConsolePrinter;
import pl.danielkolban.gameshop.io.DataReader;
import pl.danielkolban.gameshop.io.file.CsvFileManager;
import pl.danielkolban.gameshop.io.file.FileManager;
import pl.danielkolban.gameshop.io.file.FileType;
import pl.danielkolban.gameshop.io.file.SerializableFileManager;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build() {
        printer.printLine("Wybierz format danych:");
        FileType fileType = getFileType();
        return switch (fileType) {
            case SERIAL -> new SerializableFileManager();
            case CSV -> new CsvFileManager();
        };
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobsługiwany typ danych, wybierz ponownie.");
            }
        } while (!typeOk);

        return result;


    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}