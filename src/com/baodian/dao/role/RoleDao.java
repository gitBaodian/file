package com.baodian.dao.role;

import com.baodian.model.role.Role;

public interface RoleDao {
//c
	public void save(Role role);
//r
	/**
	 * 读取全部角色信息
	 * @return [{id,name,sort,open,pId}]
	 */
	public String getRoles();
	public Role getRoleById(int id);
	/**
	 * 检查是否存在
	 * @return true 存在 false 不存在
	 */
	public boolean chkExit(int rid);
//u
	public void update(Role role);
	public void updateName(Role role);
	public void updateSort(int id, int pId, int sort);
//d
	public void delete(Role role);

}

