package com.baodian.model.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
//linux系统的mysql区分大小写，因此写上创建表的名称
@Table(name="user")
public class User {
	private int id;
	private String name;
	private String account;
	private String password;
	private Department dpm;
	private List<User_Role> user_roles;
	
	private Date date;//注册时间
	private String himage;//头像
	
	private long maxSize;//总容量
	private long uploadSize;//最大上传大小
	
	public User() {}
	public User(int id) {
		this.id = id;
	}
	//UserDaoImpl.getU_ByDid 2.GoodsRecord.init
	public User(int aId, String aName) {
		this.id = aId;
		this.name = aName;
	}
	//UserDaoImpl.getU_ByDids
	public User(int id, String name, int dpmId) {
		this.id = id;
		this.name = name;
		this.dpm = new Department(dpmId);
	}
	//User_Email.init
	public User(int aId, String aName, int dpmId, String dpmName) {
		this.id = aId;
		this.name = aName;
		this.dpm = new Department(dpmId, dpmName);
	}
	//UserDaoImpl.getU_inadOnPage
	public User(int id, String name, String account, int dpmId, String dpmName, long maxSize, long uploadSize) {
		this.id = id;
		this.name = name;
		this.account = account;
		this.dpm = new Department(dpmId, dpmName);
		this.maxSize = maxSize;
		this.uploadSize = uploadSize;
	}
	//新闻浏览作者信息
	public User(int id, String name, Date date, String himage,
			int dpmId, String dpmName) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.himage = himage;
		this.dpm = new Department(dpmId, dpmName);
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(length=20, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=20, nullable=false, unique=true)
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Column(columnDefinition="char(32)", nullable=false)//自定义类型
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="department_id", nullable=false)
	public Department getDpm() {
		return dpm;
	}
	public void setDpm(Department dpm) {
		this.dpm = dpm;
	}
	@OneToMany(mappedBy="user")//,fetch=FetchType.LAZY)
	public List<User_Role> getUser_roles() {
		return user_roles;
	}
	public void setUser_roles(List<User_Role> user_roles) {
		this.user_roles = user_roles;
	}
//注册时间
	@Column(columnDefinition="timestamp not null default current_timestamp", insertable=false, updatable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
//头像
	@Column(columnDefinition="varchar(100) not null default'images/head.gif'", insertable=false)
	public String getHimage() {
		return himage;
	}
	public void setHimage(String himage) {
		this.himage = himage;
	}
	@Column(nullable=false)
	public long getUploadSize() {
		return uploadSize;
	}
	public void setUploadSize(long uploadSize) {
		this.uploadSize = uploadSize;
	}
	@Column(nullable=false)
	public long getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}
}
