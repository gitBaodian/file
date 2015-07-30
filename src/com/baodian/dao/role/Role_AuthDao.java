package com.baodian.dao.role;

import java.util.List;

import com.baodian.model.role.Role_Auth;

public interface Role_AuthDao {
//c
	public void save(Role_Auth role_Auth);
//r
	public List<Role_Auth> getRole_Auths();
	public List<Role_Auth> getAIdsById(int roleId);
	/**
	 * 通过角色id获取权限id
	 * @param rids
	 * @return
	 */
	public List<Integer> getAIdsByRIds(String rids);
	/**
	 * 获取角色与权限的关系
	 * @return [{rid,aid}]
	 */
	public String getRoAus();
//d
	/**
	 * 通过角色id删除角色与权限的关系
	 * @param id
	 */
	public void deleteR_A_ByRid(int id);
	/**
	 * 通过权限id删除权限与角色的关系
	 * @param id
	 */
	public void deleteR_A_ByAid(int id);
}
