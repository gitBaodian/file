package com.baodian.dao.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baodian.dao.role.AuthorityDao;
import com.baodian.model.role.Authority;

@Repository("authorityDao")
public class AuthorityDaoImpl implements AuthorityDao {

	private HibernateTemplate ht;
	public HibernateTemplate getHt() {
		return ht;
	}
	@Resource(name="hibernateTemplate")
	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
//c	
	public void save(Authority resource) {
		ht.save(resource);
	}
//r
	@SuppressWarnings("unchecked")
	public List<Authority> getAuthorities() {
		return (List<Authority>)ht.find("from Authority");
	}
	@SuppressWarnings("unchecked")
	public List<Authority> getAll_AR_() {
		// where a.url!='#'
		return (List<Authority>) ht.find("from Authority a join fetch a.role_auths");
	}
	@SuppressWarnings("unchecked")
	public List<Authority> getAsOnsort() {
		return (List<Authority>)ht.find("from Authority a order by a.sort");
	}
	@SuppressWarnings("unchecked")
	public List<Authority> getAsOnsd() {
		return (List<Authority>)ht.find("from Authority a where a.display=1 order by a.sort");
	}
	public Authority getAuthorityById(int id) {
		return ht.get(Authority.class, id);
	}
	public int getChildrenNum(int id) {
		long nums = (Long) ht.find("select count(*) " +
				"from Authority a where a.parent.id=?", id).get(0);
		return (int) nums;
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getA_OnNoRole() {
		return (List<Integer>)ht.find("select a.id from Authority a where a.display=1 and " +
				"a.id not in (select distinct ra.authority.id from Role_Auth ra)");
	}
	@SuppressWarnings("unchecked")
	public boolean chkExit(int aid) {
		List<Integer> aids = ht.find("select a.id from Authority a where a.id=" + aid);
		return aids.size()==0? false: true;
	}
//u
	public void update(Authority authority) {
		ht.update(authority);
	}
	public void updateSort(int id, int pid, int display, int sort) {
		String sql = "";
		if(pid != -1) {
			if(pid == 0)
				sql = sql.concat(",a.parent.id=null");
			else
				sql = sql.concat(",a.parent.id=" + pid);
		}
		if(display != -1)
			sql = sql.concat(",a.display=" + display);
		if(sort != -1)
			sql = sql.concat(",a.sort=" + sort);
		if(!sql.isEmpty())
			ht.bulkUpdate("update Authority a set " + sql.substring(1, sql.length()) +
					" where a.id=" + id);
	}
	public void updateA_nu(Authority a) {
		if(a.getName().isEmpty()) {
			if(!a.getUrl().isEmpty()) {
				ht.bulkUpdate("update Authority a set a.url=? where a.id=?", a.getUrl(), a.getId());
			}
		} else {
			if(a.getUrl().isEmpty()) {
				ht.bulkUpdate("update Authority a set a.name=? where a.id=?", a.getName(), a.getId());
			} else {
				ht.bulkUpdate("update Authority a set a.name=?,a.url=? where a.id=?", a.getName(), a.getUrl(), a.getId());
			}
		}
	}
//d
	public void delete(Authority authority) {
		ht.delete(authority);
	}

}
