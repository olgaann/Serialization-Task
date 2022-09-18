import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Basket {
    private String[] products;
    private int[] prices; //TODO исправить на double
    private int[] bin;
    static final int QTY_OF_LINES = 3;



    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.bin = new int[products.length];
    }

    public Basket(String[] products, int[] prices, int[] bin) {
        this.products = products;
        this.prices = prices;
        this.bin = bin;
    }

    public int[] getBin() {
        return bin;
    }

    public void addToCart(int productNum, int amount) { //метод добавления amount штук продукта номер productNum в корзину;
        bin[productNum] += amount;
    }

    public void printCart() { //метод вывода на экран покупательской корзины
        System.out.println("Ваша корзина: ");
        int sum = 0;
        for (int i = 0; i < bin.length; i++) {
            if(bin[i] != 0) {
                System.out.println(products[i] + " " + bin[i] + " шт. " + prices[i] + " руб/шт. " + (bin[i] * prices[i]) +" руб. в сумме");
            }
            sum += bin[i] * prices[i];
        }
        System.out.println("Общая сумма покупок: " + sum);
    }

    public void printListOfProducts() { //метод вывода на экран доступных товаров для покупки
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i].toString());
        }
    }

    public void saveTxt(File textFile) { //метод сохранения корзины в текстовый файл
        try (PrintWriter writer = new PrintWriter(textFile)){
            for(String product : products) {
               writer.print(product);
               writer.append("*");
            }
            writer.append("\n");

            for (int p : prices) {
                writer.print(p);
                writer.append("*");
            }
            writer.append("\n");

            for(int b : bin) {
                writer.print(b);
                writer.append("*");
            }
        } catch (IOException ex) {
             System.out.println(ex.getMessage());
        }
    }


    static Basket loadFromTxtFile(File textFile) {

        String[] arrOfLines = new String[QTY_OF_LINES];

        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))){
            String line;
            for (int i = 0; i < arrOfLines.length; i++) {
                arrOfLines[i] = reader.readLine();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        List<String[]> listOfLines = Arrays.stream(arrOfLines)
                .map(s -> s.substring(0, (s.length()-1)))
                .map(s -> s.split("\\*"))
                .collect(Collectors.toList());

        String[] productsNew = listOfLines.get(0);

        List<int[]> listOfInts = new ArrayList<>();

        for (int i = 1; i < listOfLines.size(); i++) {
            listOfInts.add(Arrays.stream(listOfLines.get(i)).mapToInt(Integer::parseInt).toArray());
        }

        int[] pricesNew = listOfInts.get(0);
        int[] binNew = listOfInts.get(1);

        return new Basket(productsNew, pricesNew, binNew);
    }

}
