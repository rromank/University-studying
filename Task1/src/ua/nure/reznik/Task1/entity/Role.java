package ua.nure.reznik.Task1.entity;

/**
 * User role bean.
 * 
 * @author D. Kolesnikov
 * 
 */
public class Role extends Entity {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

}
