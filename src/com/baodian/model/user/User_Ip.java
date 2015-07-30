package com.baodian.model.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baodian.model.file.Ip;

@Entity
@IdClass(User_IpPK.class)
@Table(name="user_ip")
public class User_Ip {
	private User user;
	private Ip ip;
	public User_Ip() {}
	public User_Ip(int userId, int ipId) {
		this.user = new User(userId);
		this.setIp(new Ip(ipId));
	}
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	public Ip getIp() {
		return ip;
	}
	public void setIp(Ip ip) {
		this.ip = ip;
	}

}
