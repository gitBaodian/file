package com.baodian.service.user;

import com.baodian.model.user.Department;

public interface DepartmentManager {

//c
	/**
	 * 保存部门
	 * @param department (name, parent.id)
	 */
	public void save(Department department);
//r
	/**
	 * 读取所有的部门信息
	 * @return Json [{"id":"", "name":"", "pId":""}]
	 */
	public String findAll();
//u
	/**
	 * 更改部门名称
	 * @param department
	 */
	public void changeName(Department department);
	public void changeSort(String[] dpms);
//d
	public String remove(Department department);

}
