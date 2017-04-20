package entities;


public class Cart {
//implements Comparator<Cart>{
	
	private int shopId;
	private double totalCost;
	
	public Cart(int shopId, double totalCost) {
		super();
		this.shopId = shopId;
		this.totalCost = totalCost;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public String toString(){
		return "Go to Shop Id : " + this.shopId + " for a total price of items = " + this.totalCost;
	}

//	@Override
//	public int compare(Cart o1, Cart o2) {
//		double cart1 = o1.getTotalCost();
//		double cart2 = o2.getTotalCost();
//		
//		if (cart1 > cart2) {
//	           return 1;
//	       } else if (cart1 < cart2){
//	           return -1;
//	       } else {
//	           return 0;
//	       }
//	}

}
