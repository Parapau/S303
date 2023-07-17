package model;

import services.IDDBBManager;

public class Tree extends Product {

    private final double height;

    public Tree(double precio , IDDBBManager managerDDBB, double height) {
        super(precio, managerDDBB);
        this.height = height;
    }
    public Tree(int idProducto, double precie, double height) {
        super(idProducto, precie);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String ToString() {
        return String.format("  ID:%d Product:Tree        Price:%.2f    Height:%.2f",super.getIdProduct(), super.getPrice(), getHeight());
    }
    
    @Override
    public String writeTXT() {
        return super.getIdProduct() + " " + "Tree" + " " + super.getPrice() + " " + getHeight();
    }
    
    @Override
    public String variableExtra() {
    	return "" + getHeight();
    }
	@Override
	public String getType() {
		return "Tree";
	}
}
