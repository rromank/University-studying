package ua.nure.reznik.Task1.entity;

/**
 * User bean.
 * 
 * @author D. Kolesnikov
 * 
 */
public class User extends Entity {

	private String login;

	private String password;

	private String name;

	private int roleId;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", name=" + name + ", roleId=" + roleId + "]";
	}

}
