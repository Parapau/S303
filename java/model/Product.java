package model;

import services.IDDBBManager;

public abstract class Product {
    //private static int counter;
    private final int idProduct;
    public double price;


    public Product(double price, IDDBBManager managerDDBB) {
        this.idProduct = managerDDBB.nextIdBBDD();
        this.price = price;
    }

    public Product(int idProducto, double price){
        this.idProduct = idProducto;
        this.price = price;
    }


    public double getPrice() {
        return price;
    }
    public int getIdProduct() {
        return idProduct;
    }

    public abstract String ToString();
    public abstract String writeTXT();
    public abstract String variableExtra();
    public abstract String getType();
}
