package ua.nure.reznik.Task1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.reznik.Task1.entity.Item;
import ua.nure.reznik.Task1.entity.Order;
import ua.nure.reznik.Task1.entity.User;

/**
 * This class contains methods for working with DB.
 * 
 * @author D. Kolesnikov
 * 
 */
public class DBManager {

	private static final String URL = "jdbc:derby://localhost:1527/testDB;user=test;password=test";

	// private static final String CREATE_USER =
	// "INSERT INTO users (login, password, name, role_id) VALUES (?, ?, ?, ?)";

	private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	// private static final String FIND_ALL_ROLES = "SELECT * FROM roles";

	private static final String FIND_ALL_ITEMS = "SELECT * FROM items";

	private static final String CREATE_ORDER = "INSERT INTO orders (user_id) VALUES (?)";

	private static final String INSERT_ORDER_ITEM = "INSERT INTO orders_items (order_id, item_id) VALUES (?, ?)";

	private static final String FIND_ITEMS_BY_ORDER_ID = "SELECT i.id, i.name, i.price FROM items AS i, orders_items AS oi WHERE oi.order_id = ? AND oi.item_id = i.id";

	// Singleton BEGIN =====================================

	// suppress default constructor
	private DBManager() {
		// no op
	}

	private static DBManager instance;

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	// Singleton END =======================================

	// Business logic methods ==============================

	/**
	 * Make an order.
	 * 
	 * @param user
	 *            user who make an order.
	 * @param ids
	 *            array of items' id.
	 */
	public Order makeOrder(User user, int[] ids) {
		Order order = null;
		Connection con = null;
		try {
			con = getConnection();
			startTransaction(con);
			order = addOrder(con, user.getId());
			for (int orderItemId : ids) {
				addOrderItem(con, order.getId(), orderItemId);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con);
			endTransaction(con);
		}

		return order;
	}

	private Order addOrder(Connection connection, int userId)
			throws SQLException {
		int generatedId = -1;
		try (PreparedStatement stmt = connection.prepareStatement(CREATE_ORDER,
				Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, userId);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				generatedId = rs.getInt(1);
			}
			rs.close();
		}
		Order order = new Order();
		order.setId(generatedId);
		order.setUserId(userId);
		return order;
	}

	private void addOrderItem(Connection connection, int orderId,
			int orderItemId) throws SQLException {
		try (PreparedStatement stmt = connection
				.prepareStatement(INSERT_ORDER_ITEM)) {
			int i = 1;
			stmt.setInt(i++, orderId);
			stmt.setInt(i++, orderItemId);
			stmt.execute();
		}
	}

	/**
	 * Returns items of an order as list.
	 * 
	 * @param order
	 *            order object.
	 * @return items' list of the given order.
	 * @throws SQLException
	 */
	public List<Item> getItems(Order order) {
		List<Item> items = new ArrayList<Item>();

		Connection con = null;
		try {
			con = getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try (PreparedStatement stmt = con
				.prepareStatement(FIND_ITEMS_BY_ORDER_ID)) {
			startTransaction(con);
			con.setAutoCommit(false);
			stmt.setInt(1, order.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				items.add(extractItem(rs));
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con);
			endTransaction(con);
		}
		return items;
	}

	/**
	 * Find user by id.
	 * 
	 * @param id
	 *            if of user.
	 * @return user with given id.
	 */
	public User findUserById(int id) {
		User user = null;
		Connection con = null;
		try {
			// Obtain connection
			con = getConnection();
			user = findUserById(con, id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(con);
		}
		return user;
	}

	/**
	 * Returns all items as list.
	 * 
	 * @return list of items.
	 */
	public List<Item> findAllItems() {
		List<Item> items = null;
		Connection con = null;
		try {
			con = getConnection();
			items = findAllItems(con);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(con);
		}
		return items;
	}

	// Low-level jdbc methods ==============================
	// these methods can be used in transactions

	/**
	 * Returns user by id.
	 * 
	 * @param con
	 *            connection for transactions.
	 * @param id
	 *            user id.
	 * @return user with given id.
	 */
	private User findUserById(Connection con, int id) throws SQLException {
		User user = null;
		PreparedStatement pstmt = con.prepareStatement(FIND_USER_BY_ID);

		// Adjust statement
		pstmt.setInt(1, id);

		// Execute query
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			// Save auto id into the user object
			user = extractUser(rs);
		}
		return user;
	}

	private List<Item> findAllItems(Connection con) throws SQLException {
		List<Item> items = new ArrayList<Item>();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(FIND_ALL_ITEMS);
		while (rs.next()) {
			items.add(extractItem(rs));
		}
		return items;
	}

	// Utilities ===========================================

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void startTransaction(Connection con) {
		try {
			con.setAutoCommit(false);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void endTransaction(Connection con) {
		try {
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Obtains a connection.
	 * 
	 * @return connection to DB.
	 */
	private Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		return con;
	}

	/**
	 * Extracts user from result set object.
	 * 
	 * @param rs
	 *            result set object.
	 * @return user object.
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.ENTITY_ID));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setName(rs.getString(Fields.USER_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}

	private Item extractItem(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setId(rs.getInt(Fields.ENTITY_ID));
		item.setName(rs.getString(Fields.ITEM_NAME));
		item.setPrice(rs.getInt(Fields.ITEM_PRICE));
		return item;
	}

}