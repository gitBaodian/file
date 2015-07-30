package com.baodian.service.user.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baodian.dao.user.DepartmentDao;
import com.baodian.dao.user.UserDao;
import com.baodian.model.user.Department;
import com.baodian.service.user.DepartmentManager;
import com.baodian.service.util.StaticDataManager;

@Service("departmentManager")
public class DepartmentManagerImpl implements DepartmentManager {

	private DepartmentDao ddao;
	@Resource(name="departmentDao")
	public void setDdao(DepartmentDao ddao) {
		this.ddao = ddao;
	}
	private UserDao userDao;
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	private StaticDataManager sdata;
	@Resource(name="staticData")
	public void setSdata(StaticDataManager sdata) {
		this.sdata = sdata;
	}
//c
	public void save(Department d) {
		ddao.save(d);
		sdata.reload();
	}
//r
	public String findAll() {
		return sdata.findDept();
	}
//u
	public void changeName(Department d) {
		ddao.updateName(d);
		StaticDataManager.depts.get(d.getId()).setName(d.getName());
	}
	public void changeSort(String[] dpms) {
		for(String goods : dpms) {
			String[] ids = goods.split("_");
			ddao.updateSort(Integer.parseInt(ids[0]),
					Integer.parseInt(ids[1]), Integer.parseInt(ids[2]));
		}
		sdata.reload();
	}
//d
	public String remove(Department department) {
		int num = ddao.getChildrenNum(department.getId());
		if(num > 0) {
			return "{\"status\":1,\"mess\": \"存在" + num + "个子部门！\"}";
		}
		num = userDao.getU_NumbyD_id(department.getId());
		if(num > 0) {
			return "{\"status\":1,\"mess\": \"此部门存在" + num + "个用户！\"}";
		}
		ddao.delete(department);
		sdata.reload();
		return "{\"status\":0}";
	}
}
