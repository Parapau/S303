package Floristeria;

import model.Product;
import model.Ticket;
import services.IDDBBManager;
import services.txtManagerImpl;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Store store1 = new Store("Floristeria Amapola");

        //Trabajamos con el Stock y sales de store1
        IDDBBManager DDBBManager = new txtManagerImpl();

        ArrayList<Product> stock = DDBBManager.getAllBBDD();
        ArrayList<Ticket> sales = store1.getSales();

        Menu.Options(stock,sales);

    }

}
