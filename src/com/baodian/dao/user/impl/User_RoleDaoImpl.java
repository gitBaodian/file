package com.baodian.dao.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baodian.dao.user.User_RoleDao;
import com.baodian.model.user.User_Role;

@Repository("userRoleDao")
public class User_RoleDaoImpl implements User_RoleDao {
	private HibernateTemplate ht;
	public HibernateTemplate getHt() {
		return ht;
	}
	@Resource(name="hibernateTemplate")
	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
//c
	public void save(User_Role user_Role) {
		ht.save(user_Role);
	}
//r
	@SuppressWarnings("unchecked")
	public List<User_Role> getU_R_ByUId(int id) {
		return ht.find("from User_Role ur where ur.user.id=?", id);
	}
	@SuppressWarnings("unchecked")
	public List<User_Role> get_U_RByUId(int id) {
		return ht.find("from User_Role ur join fetch ur.role where ur.user.id=?", id);
	}
//d
	public void delete(int userId) {
		ht.bulkUpdate("delete from User_Role ur where ur.user.id=?", userId);
	}
	public void deleteU_R_ByRid(int roleId) {
		ht.bulkUpdate("delete from User_Role ur where ur.role.id=?", roleId);
	}
	public void delete(int userId, int roleId) {
		ht.bulkUpdate("delete from User_Role ur where ur.user.id=" + userId +
			" and ur.role.id=" + roleId);
	}
}
