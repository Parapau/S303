package Floristeria;

import model.Product;
import model.Ticket;
import resources.Connection;
import services.IDDBBManager;
import services.txtManagerImpl;

import java.util.ArrayList;

public class Store {
    private final String name;
    private final ArrayList<Product> products;
    private final ArrayList<Ticket> sales;
    IDDBBManager DDBBManager = new txtManagerImpl();


    public Store(String name) {
        this.name = name;
        products = DDBBManager.getAllBBDD();
        sales = Connection.obtenerTicketsDesdeDB();
    }

    //Getters
    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Ticket> getSales() {
        return sales;
    }


}
