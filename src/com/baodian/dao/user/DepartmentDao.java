package com.baodian.dao.user;

import java.util.List;

import com.baodian.model.user.Department;;

public interface DepartmentDao {
//c
	/**
	 * 保存部门
	 * @param d department(name, parent.id)
	 */
	public void save(Department d);
//r
	/**
	 * 读取所有的部门信息
	 * @return List<Department>
	 */
	public List<Department> getAll();
	/**
	 * 查找子部门数
	 * @param id 要查找的部门id
	 * @return 数量
	 */
	public int getChildrenNum(int id);
	/**
	 * 检查是否存在
	 * @return true 存在 false 不存在
	 */
	public boolean chkExit(int did);
//u
	/**
	 * 更改部门名称
	 * @param department (id, name)
	 */
	public void updateName(Department department);
	public void updateSort(int id, int pId, int sort);
//d
	public void delete(Department d);

}
