package com.baodian.service.ip;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.baodian.dao.ip.IpDao;
import com.baodian.dao.user.User_IpDao;
import com.baodian.model.file.Ip;
import com.baodian.util.StaticMethod;
import com.baodian.util.page.IpPage;

@Service("ipManager")
public class IpManager {
	@Resource(name="ipDao")
	private IpDao ipDao;
	@Resource(name="userIpDao")
	private User_IpDao user_IpDao;
//c
	/**
	 * 添加IP
	 */
	public String addIp(Ip ip) {
		if(ip == null) {
			return StaticMethod.inputError;
		}
		//单个ip
		if(StaticMethod.isValidIP(ip.getIp())) {
			ip.setDate(new Date());
			ip.setUpdateDate(new Date());
			ipDao.add(ip);
		} else {
			String[] ips = ip.getIp().split("\\.\\.");
			if(ips.length != 2 && !StaticMethod.isValidIP(ips[0])) {
				return StaticMethod.inputError;
			}
			int ipEnd = StaticMethod.Str2Int(ips[1]);
			if(ipEnd<1 || ipEnd>255) {
				return StaticMethod.inputError;
			}
			int ipIndex = ips[0].lastIndexOf(".");
			String ipBase = ip.getIp().substring(0, ipIndex);
			int ipBegin = StaticMethod.Str2Int(ips[0].substring(ipIndex + 1));
			if(ipBegin >= ipEnd) {
				return StaticMethod.inputError;
			}
			for(int i=ipBegin; i<=ipEnd; i++) {
				ipDao.add(new Ip(ipBase + "." + i, ip.getType(), ip.getStatus(), new Date(), new Date()));
			}
		}
		return StaticMethod.addSucc;
	}
//r
	/**
	 * 当前ip是否允许下载文件
	 */
	public boolean containIp(int userId, String currentIp) {
		return ipDao.chkIp(userId, currentIp);
	}
	/**
	 * 查看允许下载IP
	 */
	@SuppressWarnings("unchecked")
	public String findDownLoadIps(IpPage page) {
		List<Ip> ips = ipDao.getDownLoadIps(page);
		if(ips.size() == 0) {
			return "{\"total\":0,\"rows\":[]}";
		}
		JSONObject json = new JSONObject();
		json.put("total", page.getCountNums());
		JSONArray array = new JSONArray();
		for(Ip ip : ips) {
			JSONObject ipJson = new JSONObject();
			ipJson.put("id", ip.getId());
			ipJson.put("ip", ip.getIp());
			ipJson.put("status", ip.getStatus());
			ipJson.put("type", ip.getType());
			ipJson.put("date", StaticMethod.DateToString(ip.getDate()));
			ipJson.put("updateDate", StaticMethod.DateToString(ip.getUpdateDate()));
			array.add(ipJson);
		}
		json.put("rows", array);
		return json.toString();
	}
	/**
	 * 通过ids查询ip
	 * @param json id中间用减号-隔开
	 */
	@SuppressWarnings("unchecked")
	public String findIpByIds(String json) {
		if(StaticMethod.StrSize(json) < 1) {
			return "[]";
		}
		Set<Integer> ipSet = new HashSet<Integer>();
		for(String s : json.split("-")) {
			try {
				ipSet.add(Integer.parseInt(s));
			} catch(NumberFormatException e) {}
		}
		List<Ip> ips = ipDao.getIpByIds(StaticMethod.Set2Str(ipSet, ","));
		if(ips.size() == 0) {
			return "[]";
		}
		JSONArray array = new JSONArray();
		for(Ip ip : ips) {
			JSONObject ipJson = new JSONObject();
			ipJson.put("id", ip.getId());
			ipJson.put("ip", ip.getIp());
			ipJson.put("status", ip.getStatus());
			ipJson.put("type", ip.getType());
			array.add(ipJson);
		}
		return array.toString();
	}
//u
	/**
	 * 更新ip
	 */
	public String changeIp(Ip ip) {
		if(ip == null || ! StaticMethod.isValidIP(ip.getIp())) {
			return StaticMethod.inputError;
		}
		ip.setUpdateDate(new Date());
		ipDao.updateObject(ip);
		return StaticMethod.changeSucc;
	}
	/**
	 * 标记ip状态
	 * @param json 0/1 A id1-id2 (0A1-2-3)
	 */
	public String changeIpStatus(String json) {
		if(StaticMethod.StrSize(json) < 3) {
			return StaticMethod.inputError;
		}
		String[] str = json.split("A");
		if(str.length<2) {
			return StaticMethod.inputError;
		}
		if(StaticMethod.Str2Int(str[0]) != 0) {
			str[0] = "1";
		}
		Set<String> ips = new HashSet<String>();
		for(String s : str[1].split("-")) {
			try {
				Integer.parseInt(s);
				if(ips.add(s)) {
					ipDao.updateIpStatus(str[0], s);
				}
			} catch(NumberFormatException e) {}
		}
		return StaticMethod.changeSucc;
	}
	/**
	 * 标记ip用途
	 * @param json 0/1 A id1-id2 (0A1-2-3)
	 */
	public String changeIpType(String json) {
		if(StaticMethod.StrSize(json) < 3) {
			return StaticMethod.inputError;
		}
		String[] str = json.split("A");
		if(str.length<2) {
			return StaticMethod.inputError;
		}
		if(StaticMethod.Str2Int(str[0]) != 0) {
			str[0] = "1";
		}
		Set<String> ips = new HashSet<String>();
		for(String s : str[1].split("-")) {
				try {
					Integer.parseInt(s);
					if(ips.add(s)) {
						ipDao.updateIpType(str[0], s);
					}
				} catch(NumberFormatException e) {}
			}
			return StaticMethod.changeSucc;
		}
//d
	/**
	 * 批量删除
	 */
	public String remove(String json) {
		if(StaticMethod.StrSize(json) < 1) {
			return StaticMethod.inputError;
		}
		Set<String> Ips = new HashSet<String>();
		for(String s : json.split("-")) {
			try {
				Integer.parseInt(s);
				if(Ips.add(s)) {
					user_IpDao.deleteIpByIpId(s);
					ipDao.delete(s, "Ip");
				}
			} catch(NumberFormatException e) {}
		}
		return StaticMethod.removeSucc;
	}
		
		
}
