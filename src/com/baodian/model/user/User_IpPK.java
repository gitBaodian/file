package com.baodian.model.user;

import java.io.Serializable;

import com.baodian.model.file.Ip;

@SuppressWarnings("serial")
public class User_IpPK implements Serializable {
	private User user;
	private Ip ip;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Ip getIp() {
		return ip;
	}
	public void setIp(Ip ip) {
		this.ip = ip;
	}
	@Override
	public boolean equals(Object o) {//判断逻辑属性和物理属性是否相同
		if(o instanceof User_IpPK) {
			User_IpPK pk = (User_IpPK)o;
			if(this.ip.getId() == pk.ip.getId() && this.user.getId() == pk.ip.getId()) {
			  return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {//方便查找(主键)对应的对象
		return this.user.hashCode() + this.ip.hashCode();
	}
}
