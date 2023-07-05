package model;

import resources.Connection;
import services.txtManagerImpl;

import java.util.ArrayList;

public class Store {

    private final String name;
    private final ArrayList<Product> products;
    private final ArrayList<Ticket> sales;

    public Store(String name) {
        this.name = name;
        products = new txtManagerImpl().getAllBBDD();
        sales = new ArrayList<>();
    }

    //Getters
    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Ticket> getSales() {
        return sales;
    }


}
