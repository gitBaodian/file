package com.baodian.util.page;

public class UserPage extends Page {
	
	private int dpmId;
	private String id;
	private String name;
	private String account;
	private String ip;
	/**
	 * 计算页数
	 */
	public void countPage(int countNums) {
		super.countPage(10, 100, countNums);
	}
//set get
	public int getDpmId() {
		return dpmId;
	}
	public void setDpmId(int dpmId) {
		this.dpmId = dpmId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
