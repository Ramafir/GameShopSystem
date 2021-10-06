package pl.danielkolban.gameshop.app;

public class GameShopApp {
    private final static String APP_NAME = "GameShop System v.1.0";
    public static void main(String[] args) {
        System.out.println(APP_NAME);
        GameShopController gameShopController = new GameShopController();
        gameShopController.controlLoop();

    }
}
