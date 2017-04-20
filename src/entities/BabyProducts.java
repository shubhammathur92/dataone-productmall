package entities;
import java.io.*;

public class BabyProducts {

	private int id;
	private	double price;
	private	String name;
	private boolean isCombo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public BabyProducts(int id, double d, String name) {
		super();
		this.id = id;
		this.price = d;
		this.name = name;
	}
	public BabyProducts() {
		super();
	}
	
	public String toString() {
		return id + " : " + name + " : " + price;
	}
	public boolean isCombo() {
		return isCombo;
	}
	public void setCombo(boolean isCombo) {
		this.isCombo = isCombo;
	}
	public BabyProducts(int id, double price, String name, boolean isCombo) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
		this.isCombo = isCombo;
	}
	
	
}