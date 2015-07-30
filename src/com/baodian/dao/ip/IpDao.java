package com.baodian.dao.ip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.baodian.dao.util.UtilDao;
import com.baodian.model.file.Ip;
import com.baodian.util.StaticMethod;
import com.baodian.util.page.IpPage;

@Repository("ipDao")
public class IpDao extends UtilDao {

	/**
	 * 查找ip是否允许下载文件
	 * @param userId 
	 */
	public boolean chkIp(int userId, String currentIp) {
		if((Long) super.ht.find("select count(*) from Ip ip, User_Ip user_ip" +
				" where ip.id=user_ip.ip and user_ip.user=" + userId +
					" and ip.ip=? and ip.status=0", currentIp).get(0) == 0) {
			return false;
		}
		return true;
	}
	/**
	 * 查看允许下载IP
	 */
	public List<Ip> getDownLoadIps(IpPage page) {
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" from Ip ip where 1=1");
		//StringBuilder orderSql = new StringBuilder();
		List<String> params = new ArrayList<String>();
		if(StaticMethod.StrSize(page.getIp()) > 0) {
			whereSql.append(" and ip.ip like(?)");
			params.add("%" + page.getIp() + "%");
		}
		if(page.getStatus() >= 0) {
			whereSql.append(" and ip.status=" + page.getStatus());
		}
		if(page.getType() >= 0) {
			whereSql.append(" and ip.type=" + page.getType());
		}
		return getObjsByPage("select count(*)" + whereSql,
				whereSql + " order by inet_aton(ip.ip)", page, params);
	}
	/**
	 * 更新ip状态
	 */
	public void updateIpStatus(String status, String id) {
		ht.bulkUpdate("update Ip ip" +
				" set ip.status=" + status + ", updateDate=?" +
				" where ip.id=" + id + " and ip.status!=" + status, new Date());
	}
	/**
	 * 更新ip用途 
	 */
	public void updateIpType(String type, String id) {
		ht.bulkUpdate("update Ip ip" +
				" set ip.type=" + type + ", updateDate=?" +
				" where ip.id=" + id + " and ip.type!=" + type, new Date());
	}
	/**
	 * 通过ids查询ip
	 */
	@SuppressWarnings("unchecked")
	public List<Ip> getIpByIds(String ids) {
		return ht.find("from Ip ip where id in(" + ids + ")");
	}

}
