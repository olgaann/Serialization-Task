import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;


public class Basket {
    private String[] products;
    private double[] prices;
    private int[] bin;
    static final int QTY_OF_LINES = 3;
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
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + "  " + dF.format(prices[i]) + " руб./шт.");
        }
    }

    public void saveTxt(File textFile) { //метод сохранения корзины в текстовый файл

        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (String product : products) {
                writer.print(product);
                writer.append("*");
            }
            writer.append("\n");

            for (double p : prices) {
                writer.print(p);
                writer.append("*");
            }
            writer.append("\n");

            for (int b : bin) {
                writer.print(b);
                writer.append("*");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    static Basket loadFromTxtFile(File textFile) {

        String[] arrOfLines = new String[QTY_OF_LINES];

        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {

            for (int i = 0; i < arrOfLines.length; i++) {
                arrOfLines[i] = reader.readLine();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        List<String[]> listOfLines = Arrays.stream(arrOfLines)
                .map(s -> s.substring(0, (s.length() - 1)))
                .map(s -> s.split("\\*")).toList();

        String[] productsNew = listOfLines.get(0);
        double[] pricesNew = Arrays.stream(listOfLines.get(1)).mapToDouble(Double::parseDouble).toArray();
        int[] binNew = Arrays.stream(listOfLines.get(2)).mapToInt(Integer::parseInt).toArray();

        return new Basket(productsNew, pricesNew, binNew);
    }

}
