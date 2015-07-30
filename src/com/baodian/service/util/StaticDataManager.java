package com.baodian.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baodian.model.user.Department;

public interface StaticDataManager {
//static data
	/**
	 * 部门，使用get(did)返回这个部门Department
	 */
	public Map<Integer, Department> depts = new HashMap<Integer, Department>();
	/**
	 * 部门顺序
	 */
	public List<Integer> dpmIndex = new ArrayList<Integer>();
	/**
	 * 子部门,使用get(did)返回其子部门,无子部门返回为null,get(0)返回根节点
	 */
	public Map<Integer, List<Integer>> dchildren = new HashMap<Integer, List<Integer>>();
//read
	/**
	 * 返回所有部门
	 * @return [{id,name,sort,open,pId}]
	 */
	public String findDept();
	/**
	 * 重新读取数据
	 */
	public void reload();
//update
//o
	public void output();
}
