package model;

import java.util.ArrayList;

public class Ticket {

    private static int counter = 1;

    private final int idTicket;

    ArrayList<Product>products;

    public Ticket() {
        idTicket = counter++;
        this.products = new ArrayList<>();
    }

    public int getIdTicket() {
        return idTicket;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Ticket: " + idTicket + " :\n" + " Productos : " + products;
    }
}
