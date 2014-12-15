package ua.nure.reznik.Task1.entity;

public class Order_Item {

	private int orderId;

	private int itemId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "Order_Item [orderId=" + orderId + ", itemId=" + itemId + "]";
	}

}
