package com.baodian.service.role;

import com.baodian.model.role.Role;

public interface RoleManager {
//c
	/**
	 * 保存角色及其权限
	 * @param role
	 * @param ids 权限id组
	 */
	public void save(Role role, int[] ids);
//r
	/**
	 * 读取角色
	 * @return [{id,name,sort,open,pId}]
	 */
	public String findRoles();
	/**
	 * 根据id查找角色及它的权限
	 * @param id
	 * @return
	 */
	public Role findRA_ByR_id(int id);
//u
	/**
	 * 更改角色的权限
	 * @param id 角色
	 * @param ids 权限
	 */
	public void changeRA(int id, int[] ids);
	/**
	 * 更改角色名称
	 * @param role
	 */
	public void changeName(Role role);
	public void changeSort(String[] roles);
//d
	/**
	 * 删除角色 1.删除角色与用户的关系 2.删除角色与权限的关系 3.删除角色本身
	 * @param role
	 */
	public void remove(Role role);

}
