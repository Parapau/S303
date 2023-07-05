import Controller.Menu;
import model.Product;
import model.Store;
import model.Ticket;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Store store1 = new Store("Floristeria Amapola");

        //Trabajamos con el Stock y sales de store1
        ArrayList<Product> stock = store1.getProducts();
        ArrayList<Ticket> sales = store1.getSales();

        Menu.Options(stock,sales);

    }

}
