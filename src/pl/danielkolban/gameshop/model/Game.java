package pl.danielkolban.gameshop.model;

import java.io.Serializable;
import java.util.Objects;

public class Game extends Product{
    public static final String TYPE = "Gra";
    private String coverLanguage;
    private String gameLanguage;

    public Game(String category, String name, String coverLanguage, String gameLanguage, double purchasePrice, double sellingPrice, boolean newOrUsed, int quantity) {
        super(category, name, purchasePrice, sellingPrice, newOrUsed, quantity);
        this.coverLanguage = coverLanguage;
        this.gameLanguage = gameLanguage;
    }

    public String getCoverLanguage() {
        return coverLanguage;
    }

    public void setCoverLanguage(String coverLanguage) {
        this.coverLanguage = coverLanguage;
    }

    public String getGameLanguage() {
        return gameLanguage;
    }

    public void setGameLanguage(String gameLanguage) {
        this.gameLanguage = gameLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Game game = (Game) o;
        return Objects.equals(coverLanguage, game.coverLanguage) && Objects.equals(gameLanguage, game.gameLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coverLanguage, gameLanguage);
    }

    @Override
    public String toString() {
        return super.toString() + "; " + coverLanguage + "; " + gameLanguage + "; " + getPurchasePrice() + "; "
                + getSellingPrice() + "; " + getQuantity();
    }

    @Override
    public String toCsv() {
            return (TYPE + ";") +
                    getCategory() + ";" +
                    getName() + ";" +
                    getCoverLanguage() + ";" +
                    getGameLanguage() + ";" +
                    getPurchasePrice() + ";" +
                    getSellingPrice() + ";" +
                    isNewOrUsed() + ";" +
                    getQuantity() + "";
        }
}
