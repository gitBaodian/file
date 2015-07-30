package com.baodian.dao.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baodian.dao.role.Role_AuthDao;
import com.baodian.model.role.Role_Auth;

@Repository("roleAuthDao")
public class Role_AuthDaoImpl implements Role_AuthDao {
	private HibernateTemplate ht;
	public HibernateTemplate getHt() {
		return ht;
	}
	@Resource(name="hibernateTemplate")
	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
//c
	public void save(Role_Auth role_Auth) {
		ht.save(role_Auth);
	}
//r
	@SuppressWarnings("unchecked")
	public List<Role_Auth> getRole_Auths() {
		return ht.find("from Role_Auth");
	}
	@SuppressWarnings("unchecked")
	public List<Role_Auth> getAIdsById(int roleId) {
		return ht.find("from Role_Auth ra where ra.role.id=?", roleId);
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getAIdsByRIds(String rids) {
		return ht.find("select a.id from Authority a where a.display=1 and " +
				"a.id in(select distinct ra.authority.id from Role_Auth ra " +
				"where ra.role.id in(" + rids + "))");
	}
	@SuppressWarnings("unchecked")
	public String getRoAus() {
		List<Role_Auth> roAus = ht.find("from Role_Auth");
		if(roAus.size() == 0) {
			return "[]";
		}
		StringBuilder json = new StringBuilder();
		json.append('[');
		for(Role_Auth ra : roAus) {
			json.append("{\"rid\":" + ra.getRole().getId() + 
					",\"aid\":" + ra.getAuthority().getId() + "},");
		}
		return json.substring(0, json.length()-1) + ']';
	}
//d
	public void deleteR_A_ByRid(int id) {
		ht.bulkUpdate("delete from Role_Auth r where r.role.id=?", id);
	}
	public void deleteR_A_ByAid(int id) {
		ht.bulkUpdate("delete from Role_Auth r where r.authority.id=?", id);
	}
}
