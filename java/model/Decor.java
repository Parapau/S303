package model;

import services.IDDBBManager;

public class Decor extends Product {

    private final String material;

    public Decor(double precio, IDDBBManager managerDDBB, String material) {
        super(precio, managerDDBB);
        this.material = material;
    }

    public Decor(int idProducto, double precie, String material) {
        super(idProducto, precie);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String ToString() {
        return String.format("  ID:%d Product:Decoration  Price:%.2f    Attribute:%s",super.getIdProduct(), super.getPrice(), getMaterial() );
    }

    @Override
    public String writeTXT() {
        return super.getIdProduct() + " " + "Decoration" + " " + super.getPrice() + " " + getMaterial();
    }
    
    @Override
    public String variableExtra() {
		return getMaterial();
    	
    }

	@Override
	public String getType() {
		return "Decoration";
	}
}
