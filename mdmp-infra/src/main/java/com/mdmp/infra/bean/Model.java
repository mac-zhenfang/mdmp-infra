package com.mdmp.infra.bean;

/**
 * @author zhefang
 *
 */
public class Model {
	// id, name, uid, description, logic, createdTime
	private String id;

	private String name;

	private String uid;

	private String description;

	private String logic;

	private String createdTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public final Model clone() {
		try {
			return (Model) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
