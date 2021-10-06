package pl.danielkolban.gameshop.io.file;

import pl.danielkolban.gameshop.model.Shop;

public interface FileManager {
    Shop importData();
    void exportData(Shop shop);
}
