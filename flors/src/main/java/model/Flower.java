package model;

import services.IDDBBManager;

public class Flower extends Product {

    private final String color;

    public Flower(double precio, IDDBBManager managerDDBB, String color) {
        super(precio, managerDDBB);
        this.color = color;
    }
    public Flower(int idProducto, double precie, String color) {
        super(idProducto, precie);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String ToString() {
        return String.format("  ID:%d Product:Flower      Price:%.2f    Color:%s",super.getIdProduct(), super.getPrice(), getColor());
    }
    @Override
    public String writeTXT() {
        return super.getIdProduct() + " " + "Flower" + " " + super.getPrice() + " " + getColor();
    }
    @Override
    public String variableExtra() {
		return getColor();
    	
    }
	@Override
	public String getType() {
		return "Flower";
	}
}
