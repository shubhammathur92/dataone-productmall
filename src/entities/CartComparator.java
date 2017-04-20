package entities;

import java.util.Comparator;

public class CartComparator implements Comparator<Cart>{

//	@Override
//	public int compare(Cart o1, Cart o2) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
	@Override
	public int compare(Cart o1, Cart o2) {
		double cart1 = o1.getTotalCost();
		double cart2 = o2.getTotalCost();
		
		if (cart1 > cart2) {
	           return 1;
	       } else if (cart1 < cart2){
	           return -1;
	       } else {
	           return 0;
	       }
	}

}
