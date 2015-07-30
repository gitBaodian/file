package com.baodian.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baodian.dao.util.UtilDao;
import com.baodian.model.file.Ip;

@Repository("userIpDao")
public class User_IpDao extends UtilDao {
//c
//r
	/**
	 * 根据用户id获取使用中(status=0)的ip
	 */
	@SuppressWarnings("unchecked")
	public List<Ip> getIpByUId(int uId) {
		return super.ht.find("select new Ip(ip.id, ip.ip, ip.type)" +
				" from Ip ip, User_Ip user_ip" +
				" where ip.id=user_ip.ip and ip.status=0 and user_ip.user=" + uId);
	}
//u
//d
	/**
	 * 根据ip删除用户IP
	 */
	public void deleteIpByIpId(String ipId) {
		ht.bulkUpdate("delete from User_Ip user_ip where user_ip.ip.id=" + ipId);
	}
	/**
	 * 根据user删除用户IP
	 */
	public void deleteIpByUId(int uId) {
		ht.bulkUpdate("delete from User_Ip user_ip where user_ip.user.id=" + uId);
	}
	
}
