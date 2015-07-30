package com.baodian.dao.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baodian.dao.user.DepartmentDao;
import com.baodian.model.user.Department;

@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {
	private HibernateTemplate ht;
	@Resource(name="hibernateTemplate")
	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}
//c
	public void save(Department d) {
		ht.save(d);
	}
//r
	@SuppressWarnings("unchecked")
	public List<Department> getAll() {
		return ht.find("from Department d order by d.sort");
	}
	public int getChildrenNum(int id) {
		return ((Long) ht.find("select count(*) " +
				"from Department d where d.parent.id=?", id).get(0)).intValue();
	}
	@SuppressWarnings("unchecked")
	public boolean chkExit(int did) {
		List<Integer> aids = ht.find("select d.id from Department d where d.id=" + did);
		return aids.size()==0? false: true;
	}
//u
	public void updateName(Department d) {
		ht.bulkUpdate("update Department d set d.name=? where d.id=?", d.getName(), d.getId());
	}
	public void updateSort(int id, int pid, int sort) {
		StringBuilder sql = new StringBuilder();
		if(pid != -1) {
			if(pid == 0)
				sql.append(",d.parent.id=null");
			else
				sql.append(",d.parent.id=" + pid);
		}
		if(sort != -1)
			sql.append(",d.sort=" + sort);
		if(sql.length() != 0) {
			ht.bulkUpdate("update Department d set " + sql.substring(1, sql.length()) +
					" where d.id=" + id);
		}
	}
//d
	public void delete(Department d) {
		ht.delete(d);
	}

}
