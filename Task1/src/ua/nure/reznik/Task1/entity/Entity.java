package ua.nure.reznik.Task1.entity;

/**
 * Root of all entities which have identifier field.
 * 
 * @author D.Kolesnikov
 * 
 */
public abstract class Entity {

	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
