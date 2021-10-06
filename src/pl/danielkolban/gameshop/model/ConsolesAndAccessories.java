package pl.danielkolban.gameshop.model;

public class ConsolesAndAccessories extends Product {
    public static final String TYPE = "Konsole i akcesoria";

    public ConsolesAndAccessories(String category, String name, double purchasePrice, double sellingPrice, boolean newOrUsed, int quantity) {
        super(category, name, purchasePrice, sellingPrice, newOrUsed, quantity);
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") +
                getCategory() + ";" +
                getName() + ";" +
                getPurchasePrice() + ";" +
                getSellingPrice() + ";" +
                isNewOrUsed() + ";" +
                getQuantity() + "";
    }

    @Override
    public String toString() {
        return super.toString() + "; " + getPurchasePrice() + "; " + getSellingPrice() + "; " + getQuantity();
    }
}
