package ua.nure.reznik.Task1;

import java.util.List;

import ua.nure.reznik.Task1.db.DBManager;
import ua.nure.reznik.Task1.entity.Item;
import ua.nure.reznik.Task1.entity.Order;
import ua.nure.reznik.Task1.entity.User;

/**
 * Main class.
 * 
 * @author D. Kolesnikov
 * 
 */
public class Main {

	public static void main(String[] args) throws Exception {
		// obtain a user
		User user = DBManager.getInstance().findUserById(2);
		System.out.println("Current user: " + user);
		System.out.println("~~~~~~~~~~~~~~~~~");
		
		// obtain menu items
		System.out.println("Menu items:");
		List<Item> items = DBManager.getInstance().findAllItems();
		for (Item item : items) {
			System.out.println(item);
		}		
		System.out.println("~~~~~~~~~~~~~~~~~");
		
		// make an order
		System.out.println("Try to make an order");
		Order order = DBManager.getInstance().makeOrder(user, new int[] {3, 5, 7});
		System.out.println("Order has been created: " + order);
		
		// print an order
		System.out.println("Items of order:");
		items = DBManager.getInstance().getItems(order);
		for (Item item : items) {
			System.out.println(item);
		}		
		
	}

}
