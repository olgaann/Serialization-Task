import java.io.*;
import java.text.DecimalFormat;


public class Basket implements Serializable {
    private String[] products;
    private double[] prices;
    private int[] bin;
    static DecimalFormat dF = new DecimalFormat("0.00");


    public Basket() {
    }

    public Basket(String[] products, double[] prices) {
        this.products = products;
        this.prices = prices;
        this.bin = new int[products.length];
    }

    public Basket(String[] products, double[] prices, int[] bin) {
        this.products = products;
        this.prices = prices;
        this.bin = bin;
    }

    public void setPrices(double[] prices) {
        this.prices = prices;
    }

    public void addToCart(int productNum, int amount) { //метод добавления amount штук продукта номер productNum в корзину;
        bin[productNum] += amount;
        bin[productNum] = Math.max(bin[productNum], 0);
    }

    public void printCart() { //метод вывода на экран покупательской корзины

        System.out.println("Ваша корзина: ");
        double sum = 0;
        for (int i = 0; i < bin.length; i++) {
            if (bin[i] != 0) {
                System.out.println(products[i] + " " + bin[i] + " шт. " + dF.format(prices[i]) + " руб/шт. " + (dF.format(bin[i] * prices[i])) + " руб. в сумме");
            }
            sum += bin[i] * prices[i];
        }
        System.out.println("Общая сумма покупок: " + dF.format(sum) + " руб.");
    }

    public void printListOfProducts() { //метод вывода на экран доступных товаров для покупки
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < this.products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + "  " + dF.format(prices[i]) + " руб./шт.");
        }
    }

    public void saveBin(File file) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(this);
        out.close();
    }

    static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Basket basket = (Basket) in.readObject();
        in.close();
        return basket;
    }

}
