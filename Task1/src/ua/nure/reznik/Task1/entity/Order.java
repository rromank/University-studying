package ua.nure.reznik.Task1.entity;

public class Order extends Entity {

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + "]";
	}

}
