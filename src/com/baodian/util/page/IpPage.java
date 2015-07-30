package com.baodian.util.page;

public class IpPage extends Page {
	private String ip;
	private int status = -1;
	private int type = -1;
	
	/**
	 * 计算页数
	 * @param countNums
	 */
	public void countPage(int countNums) {
		super.countPage(10, 100, countNums);
	}
//set get
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
