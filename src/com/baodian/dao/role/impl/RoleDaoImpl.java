package com.baodian.dao.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baodian.dao.role.RoleDao;
import com.baodian.model.role.Role;
import com.baodian.util.JSONValue;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
	private HibernateTemplate ht;
	public HibernateTemplate getHt() {
		return ht;
	}
	@Resource(name="hibernateTemplate")
	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
//c
	public void save(Role role) {
		ht.save(role);
	}
//r
	@SuppressWarnings("unchecked")
	public String getRoles() {
		List<Role> roles = ht.find("from Role r order by r.sort");
		if(roles.size() == 0) {
			return "[]";
		}
		StringBuilder json = new StringBuilder();
		json.append('[');
		for(Role r : roles) {
			json.append("{\"id\":" + r.getId() + 
					",\"name\":\"" + JSONValue.escape(r.getName()) +
					"\",\"sort\":" + r.getSort() +
					",\"open\":true");
			if(r.getParent() != null) {
				json.append(",\"pId\": " + r.getParent().getId());
			}
			json.append("},");
		}
		return json.substring(0, json.length()-1) + ']';
	}
	public Role getRoleById(int id) {
		return ht.get(Role.class, id);
	}
	@SuppressWarnings("unchecked")
	public boolean chkExit(int rid) {
		List<Integer> rids = ht.find("select r.id from Role r where r.id=" + rid);
		return rids.size()==0? false: true;
	}
//u
	public void update(Role role) {
		ht.update(role);
	}
	public void updateName(Role role) {
		ht.bulkUpdate("update Role r set r.name=? where r.id=?", role.getName(), role.getId());
	}
	public void updateSort(int id, int pid, int sort) {
		StringBuilder sql = new StringBuilder();
		if(pid != -1) {
			if(pid == 0)
				sql.append(",r.parent.id=null");
			else
				sql.append(",r.parent.id=" + pid);
		}
		if(sort != -1)
			sql.append(",r.sort=" + sort);
		if(sql.length() != 0) {
			ht.bulkUpdate("update Role r set " + sql.substring(1, sql.length()) +
					" where r.id=" + id);
		}
	}
//d
	public void delete(Role role) {
		ht.delete(role);
	}

}
