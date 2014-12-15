package ua.nure.reznik.Task1.db;

/**
 * Holder for fields names of DB tables.
 * 
 * @author D.Kolesnikov
 * 
 */
public final class Fields {

	// Suppress default constructor
	private Fields() {
		// no op
	}

	// entities
	public static final String ENTITY_ID = "id";

	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_NAME = "name";
	public static final String USER_ROLE_ID = "role_id";

	public static final String ITEM_NAME = "name";
	public static final String ITEM_PRICE = "price";

}