package com.snapshot.es.vo;

public class SnapshotEsVO {
	
	private String date;
	private String controller;
	private String type;
	private String fromdt;
	private String todt;
	private String success;
	
	public SnapshotEsVO(String date, String controller, String type, String fromdt, String todt, String success) {
		super();
		this.date = date;
		this.controller = controller;
		this.type = type;
		this.fromdt = fromdt;
		this.todt = todt;
		this.success = success;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFromdt() {
		return fromdt;
	}
	public void setFromdt(String fromdt) {
		this.fromdt = fromdt;
	}
	public String getTodt() {
		return todt;
	}
	public void setTodt(String todt) {
		this.todt = todt;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	

}