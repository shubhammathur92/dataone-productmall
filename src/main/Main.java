package main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import entities.BabyProducts;
import entities.Cart;
import entities.CartComparator;

public class Main {

	public static void main(String[] args) {
		
		
		List<String> shoppingProducts = new ArrayList<String>();
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your products (separated by spaces) : "); 
		String product = input.nextLine();
		
		
		String[] shoppingItems = product.split("\\s");
				
		for(String item : shoppingItems){
			shoppingProducts.add(item);
		}
		
		//example 1
		//teddy_bear baby_powder
		
		//example 2
		// pampers_diapers soap
		
		//example 3
		// scissor bath_towel 
		
		 
		List<BabyProducts> listProducts = null;
		try {
			listProducts = createBabyProductList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(listProducts!=null){
			//Create Inventory
			Map<Integer , List<String>> inventory = createInventory(listProducts);
			
			//Search and recommend shop for purchase
			searchProduct(listProducts, shoppingProducts, inventory);
		}
		
	}
	
	public static List<BabyProducts> createBabyProductList() throws IOException {
		List<BabyProducts> listProducts = new ArrayList<BabyProducts>();
		String csvFile = "data.csv";
        BufferedReader bufferedReader = null;
        String data = "";
        
        try {

        	bufferedReader = new BufferedReader(new FileReader(csvFile));
            while ((data = bufferedReader.readLine()) != null) {

                String[] productItem = data.split(",");
                if(productItem.length>3){
                	String comboProduct = productItem[2];
                	for(int i=3; i<productItem.length; i++){
                		comboProduct = comboProduct + "," +  productItem[i];
                	}
                	BabyProducts product = new BabyProducts(Integer.valueOf(productItem[0]), Double.valueOf(productItem[1]), comboProduct, true);
                	listProducts.add(product);
	             }else{
	                	BabyProducts product = new BabyProducts(Integer.valueOf(productItem[0]), Double.valueOf(productItem[1]), productItem[2], false);
	                	listProducts.add(product);
	             }
            }

        } catch (FileNotFoundException exp) {
            exp.printStackTrace();
        } catch (IOException exp) {
            exp.printStackTrace();
        } finally {
        	
        	bufferedReader.close();
        }
		return listProducts;

	}

	
	public static Map<Integer , List<String>> createInventory(List<BabyProducts> listProducts){
		
		
		Map<Integer , List<String>> inventory = new HashMap<Integer , List<String>>();
		
		for(BabyProducts babyProduct : listProducts) {
			if(inventory.containsKey(babyProduct.getId())){
				inventory.get(Integer.valueOf(babyProduct.getId())).add(babyProduct.getName());
			}
			else{
				List<String> shopProductList = new ArrayList<String>();
					shopProductList.add(babyProduct.getName());
				
				inventory.put(Integer.valueOf(babyProduct.getId()),shopProductList);
			}
			
		}
		
//		for (Map.Entry<Integer, List<String>> entry : inventory.entrySet()) {
//			System.out.println(entry.getKey());
//			for(String shopTest : entry.getValue()){
//				System.out.println(shopTest);
//			}
//		    
//		}
		return inventory;
	}
	
	public static void searchProduct(List<BabyProducts> listProducts, List<String> desiredProductList, Map<Integer , List<String>> inventory){
		boolean findAll = true;
		Set<Integer> qualifiedShops = new HashSet<Integer>();
		Map<Integer, Double> minCartMap = new HashMap<Integer, Double>();
		
		for(String desiredProduct : desiredProductList) {
			//System.out.println("desiredProduct : " + desiredProduct);
			
			for (Map.Entry<Integer, List<String>> entry : inventory.entrySet()) {
				//System.out.println("Value Test : " + entry.getValue());
				
				if(isProductAvailable(entry.getValue() , desiredProduct)){
					if(!qualifiedShops.contains(entry.getKey()) && findAll){
						//System.out.println("shop added : " + entry.getKey());
						qualifiedShops.add(entry.getKey());
					}
					
				}else{
					if(qualifiedShops.contains(entry.getKey())){
						//System.out.println("shop removed : " + entry.getKey());
						qualifiedShops.remove(entry.getKey());
						findAll = false;
					}
				}
			}
		}
		
		if(qualifiedShops.size()==0){
			System.out.println("No qualified shop to purchase all the desired products. ");
		}else{
			//System.out.println("qualifiedShops size : " + qualifiedShops.size());
	 
			
			for(String desiredProduct : desiredProductList) {
				// System.out.println("cost desiredProduct : " + desiredProduct);
				
				for(Integer qualifiedShopId : qualifiedShops) {
					double productPrice = getPriceByIdAndName(listProducts, qualifiedShopId, desiredProduct);
					// System.out.println("productPrice : " + productPrice);
					if(productPrice > 0){
						if(minCartMap.containsKey(qualifiedShopId)){
							Double currentCartPrice = minCartMap.get(qualifiedShopId);
							
							currentCartPrice =  currentCartPrice + Double.valueOf(productPrice);
							minCartMap.put(qualifiedShopId, currentCartPrice);
						}else{
							minCartMap.put(qualifiedShopId, Double.valueOf(productPrice));
						}
						
					}
				}
				
			}
			
			List<Cart> cartList = new ArrayList<Cart>();
			for (Map.Entry<Integer, Double> entry : minCartMap.entrySet()) {
			    //System.out.println(entry.getKey() + " : " + entry.getValue());
			    cartList.add(new Cart(entry.getKey(), entry.getValue()));
			}
			
			Collections.sort(cartList, new CartComparator());
	
			System.out.println(cartList.get(0).toString());
		}
		
	}
	
	public static double getPriceByIdAndName(List<BabyProducts> babyProductList, int shopId, String name){
		double productPrice = 0;
		
		for(BabyProducts product : babyProductList){
			if(product.getId()==shopId && product.getName().contains(name)) {
				productPrice = product.getPrice();
			}
		}
		return productPrice;
		
	}
	
	public static boolean isCombo(List<BabyProducts> babyProductList, int shopId, String name){
		
		for(BabyProducts product : babyProductList){
			if(product.getId()==shopId && product.getName().contains(name)) {
				return product.isCombo();
			}
		}
		return false;
		
	}
	
	public static boolean isProductAvailable(List<String> productList, String product){
		for(String item : productList){
			if(item.contains(product)){
				return true;
			}
		}
		return false;
		
	}
 

}
