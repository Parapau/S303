package Floristeria;

import model.Product;
import model.Ticket;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Store store1 = new Store("Floristeria Amapola");

        ArrayList<Product> stock = store1.getProducts();
        ArrayList<Ticket> sales = store1.getSales();


        Menu.Options(stock,sales);

    }

}
