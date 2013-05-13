/**
 * 
 */
package com.mdmp.infra.bean;


/**
 * @author zhefang
 *
 */
public class Report {
	// id, name, aid, rpttype, mid, did, uid, logic, description, createdTime
	private String id;

	private String name;
	
	private String reportType;
	
	private String aid;
	
	private String mid;
	
	private String starttime;
	
	private String data; 
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	private String endtime;
	
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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
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

	private String did;
	
	private String uid;

	private String description;

	private String logic;

	private String createdTime;
	
	public final Report clone() {
		try {
			return (Report) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
