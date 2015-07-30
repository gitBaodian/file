package com.baodian.dao.user.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

import com.baodian.dao.user.UserDao;
import com.baodian.dao.util.UtilDao;
import com.baodian.model.user.User;
import com.baodian.util.StaticMethod;
import com.baodian.util.page.UserPage;

@Repository("userDao")
public class UserDaoImpl extends UtilDao implements UserDao {

//c
	public void save(User user) {
		ht.save(user);
	}
//r
	public List<User> getU_inadOnPage(final UserPage page) {
		final StringBuilder sql = new StringBuilder();
		sql.append(" where 1=1");
		List<String> params = new ArrayList<String>();
		if(StaticMethod.Str2Int(page.getId()) > 0) {
			sql.append(" and u.id=" + page.getId());
		}
		if(page.getDpmId() != 0) {
			sql.append(" and u.dpm.id=" + page.getDpmId());
		}
		if(StaticMethod.StrSize(page.getName()) > 0) {
			sql.append(" and u.name like (?)");
			params.add("%" + page.getName() + "%");
		}
		if(StaticMethod.StrSize(page.getAccount()) > 0) {
			sql.append(" and u.account like (?)");
			params.add("%" + page.getAccount() + "%");
		}
		if(StaticMethod.Str2Int(page.getIp()) > 0) {
			sql.insert(0, ", User_Ip user_ip");
			sql.append(" and u.id=user_ip.user" +
					" and user_ip.ip=" + page.getIp());
		}
		return getObjsByPage("select count(*) from User u " + sql.toString(),
			"select new User(u.id, u.name, u.account, d.id, d.name, u.maxSize, u.uploadSize)" +
			"from User u, Department d " + sql.toString() +
			" and u.dpm.id=d.id order by d.id", page, params);
	}
	@SuppressWarnings("unchecked")
	public User getU_inadById(int id) {
		List<User> users = ht.find("select new User(u.id, u.name, u.account, d.id, d.name) " +
				"from User u, Department d where u.id=?", id);
		if(users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	@SuppressWarnings("unchecked")
	public String getU_passByAccount(String account) {
		List<String> strs = ht.find("select u.password from User u where u.account=?", account);
		if(strs.size() > 0)
			return strs.get(0);
		else
			return null;
	}
	public int getU_NumbyD_id(int id) {
		return ((Long) ht.find("select count(*) " +
				"from User u where u.dpm.id=?", id).get(0)).intValue();
	}
	public User getUserByU_a(String account) {
		return (User) ht.find("from User u where u.account=?", account).get(0);
	}
	@SuppressWarnings("unchecked")
	public int getU_NumByAc(String account, int uId) {
		List<Integer> uids = ht.find("select u.id " +
				"from User u where u.account=?", account);
		if(uids.size() > 0) {
			if(uId == uids.get(0)) {
				return 0;
			} else {
				return uids.size();
			}
		} else {
			return 0;
		}
	}
	@SuppressWarnings("unchecked")
	public List<User> getU_ByDids(String dids) {
		return ht.find("select new User(u.id, u.name, u.dpm.id)" +
				"from User u where u.dpm.id in(" + dids + ")");
	}
	@SuppressWarnings("unchecked")
	public List<User> getU_ByDid(int did) {
		return ht.find("select new User(u.id, u.name)" +
				"from User u where u.dpm.id=" + did);
	}
	@SuppressWarnings("unchecked")
	public boolean chkExit(int uid) {
		List<Integer> uids = ht.find("select u.id from User u where u.id=" + uid);
		return uids.size()==0? false: true;
	}
//u
	public void update(User user, int depmId) {
		String sql = "update User u set ";
		if(!user.getName().isEmpty()) {
			sql = sql.concat("u.name='" + user.getName() + "',");
		}
		if(!user.getAccount().isEmpty()) {
			sql = sql.concat("u.account='" + user.getAccount() + "'," +
					"u.password='" + user.getPassword() + "',");
		}
		if(depmId != -1) {
			sql = sql.concat("u.dpm.id=" + depmId + ",");
		}
		sql = sql.concat("u.maxSize=" + user.getMaxSize() +
				",u.uploadSize=" + user.getUploadSize() + ",");
		if(!sql.equals("update User u set ")) {
			sql = sql.substring(0, sql.length()-1)
					.concat(" where u.id=" + user.getId());
			ht.bulkUpdate(sql);
		}
	}
	public void updatePW(User user) {
		ht.bulkUpdate("update User u set u.password=? where u.account=?", user.getPassword(), user.getAccount());
	}
	public void updateDpm(int uid, int did) {
		ht.bulkUpdate("update User u set u.dpm.id=" + did + " where u.id=" + uid);
	}
	public void updateMaxSize(int uid, long fielSize) {
		ht.bulkUpdate("update User u set u.maxSize=" + fielSize + " where u.id=" + uid);
	}
	public void updateUploadSize(int uid, long fielSize) {
		ht.bulkUpdate("update User u set u.uploadSize=" + fielSize + " where u.id=" + uid);
	}
//d
	public void delete(User user) {
		ht.delete(user);
	}
	public User getUserByName(String username) {
		return (User) ht.find("from User where name=?",username).get(0);
	}
	@SuppressWarnings("unchecked")
	public List<String> getAllUserName() {
		List<String> usernames = ht.find("select u.name from User u");
		return usernames;
	}

}
