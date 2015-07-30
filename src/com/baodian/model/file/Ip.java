package com.baodian.model.file;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 允许下载文件的ip
 * @author LF_eng
 * 2014年3月8日12:52:11
 */
@Entity
@Table(name="ip")
public class Ip {
	private int id;
	private String ip;
	private int status;
	private int type;
	private Date date;
	private Date updateDate;
	
	public Ip() {}
	//User_Ip.init
	public Ip(int id) {
		this.id = id;
	}
	//User_IpDao.getIpByUId
	public Ip(int id, String ip, int type) {
		this.id = id;
		this.ip = ip;
		this.type = type;
	}
	//FileManager.addIp
	public Ip(String ip, int type, int status, Date date, Date updateDate) {
		this.ip = ip;
		this.type = type;
		this.status = status;
		this.date = date;
		this.updateDate = date;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(columnDefinition="char(15)", nullable=false)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	/*
	 * 0为使用 1为停用
	 */
	@Column(columnDefinition="tinyint(1) not null default'0'")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(nullable=false, updatable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column(nullable=false)
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/*
	 * 0为上网机 1为工作机
	 */
	@Column(columnDefinition="tinyint(1) not null default'0'")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
