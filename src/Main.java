import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String[] products = {"Молоко", "Хлеб", "Яблоки", "Сыр"}; // массив товаров (ассортимента)
        double[] prices = {100.50, 75.00, 110.00, 800.00}; // массив цен
        Basket basket = new Basket();
        Scanner scan = new Scanner(System.in);

        //создаем файл для записи/получения сведений о корзине
        File file = new File("file.txt");
        try {
            if (file.createNewFile() || file.length() == 0L) {
                basket = new Basket(products, prices);
            } else {
                basket = Basket.loadFromTxtFile(file);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        basket.printListOfProducts();
        while (true) {
            System.out.println("Введите номер товара и количество через пробел. Для завершения введите `end`");
            String input = scan.nextLine();

            if (input.equals("end")) {
                basket.printCart();
                break;
            }

            String[] purchase = input.split(" "); //расщепляем ввод пользователя на номер продукта и количество продукта
            if (purchase.length != 2) { //если пользователь сделал ввод не из двух частей
                System.out.println("Некорректный ввод. Введите два числа через пробел.");
                continue; //переходим к следующей итерации
            }

            int productNumber;
            int amount;

            try {
                productNumber = (Integer.parseInt(purchase[0])) - 1; //определяем номер продукта (в соотв.с ключом массива products)
                amount = Integer.parseInt(purchase[1]);
            } catch (NumberFormatException exception) {
                System.out.println("Ошибка преобразования значения!");
                continue;
            }

            if (productNumber < 0 || productNumber >= products.length) {
                System.out.println("Такого номера товара не существует.");
                continue;
            }
            basket.addToCart(productNumber, amount);
            basket.saveTxt(file);

        }
        scan.close();


    }
}
