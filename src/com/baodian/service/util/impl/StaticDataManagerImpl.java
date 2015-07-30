package com.baodian.service.util.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baodian.dao.user.DepartmentDao;
import com.baodian.model.user.Department;
import com.baodian.service.util.StaticDataManager;
import com.baodian.util.JSONValue;

@Service("staticData")
//利用sping保存经常读，但又不经常变化的
public class StaticDataManagerImpl implements StaticDataManager {

	private DepartmentDao departmentDao;
	@Resource(name="departmentDao")
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
//init
	@PostConstruct
	public void init() {
		int a;
		//部门
		List<Department> dps = departmentDao.getAll();
		for(Department dpm : dps) {
			depts.put(dpm.getId(), dpm);
			dpmIndex.add(dpm.getId());
		}
		//子部门
		Iterator<Department> it = depts.values().iterator();
	    while(it.hasNext()) {
	    	Department dept = it.next();
	    	if(dept.getParent() != null)
		    	a = dept.getParent().getId();
	    	else a = 0;//根节点
	    	if(dchildren.containsKey(a)) {
	    		dchildren.get(a).add(dept.getId());
	    	} else {
	    		List<Integer> ids = new ArrayList<Integer>();
	    		ids.add(dept.getId());
	    		dchildren.put(a, ids);
	    	}
		}
		System.out.println("***初始化StaticData(静态数据)成功***");
	}
//read
	public String findDept() {
		if(depts.size() == 0) {
			return "[]";
		}
		StringBuilder json = new StringBuilder();
		json.append('[');
		Department dept;
		for(int did : dpmIndex) {
			dept = depts.get(did);
			json.append("{\"id\": " + dept.getId() +
					",\"name\":\"" + JSONValue.escape(dept.getName()) +
					"\",\"sort\":" + dept.getSort() +
					",\"open\":true");
			if(dept.getParent() != null)
				json.append(",\"pId\": " + dept.getParent().getId());
			json.append("},");
		}
	    return json.substring(0, json.length()-1) + ']';
		
	}
	public void reload() {
		depts.clear();
		dpmIndex.clear();
		dchildren.clear();
		this.init();
	}
//update
//output
	public void output() {
		System.out.println();
		System.out.println("部门 => depts");
		System.out.println(findDept());
		System.out.println("子部门 => dchildren");
		for(Entry<Integer, List<Integer>> settt : dchildren.entrySet()) {
	    	List<Integer> iiii = settt.getValue();
	    	System.out.print(settt.getKey() + " => ");
	    	for(int i=0;i<iiii.size();i++)
	    		System.out.print(iiii.get(i) + " ");
	    	System.out.println();
		}
	}
}
