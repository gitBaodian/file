package com.baodian.dao.user;

import java.util.List;

import com.baodian.model.user.User_Role;

public interface User_RoleDao {
//c
	public void save(User_Role user_Role);
//r
	/**
	 * 用户角色
	 * @return user(id) role(id)
	 */
	public List<User_Role> getU_R_ByUId(int id);
	/**
	 * 用户角色
	 * @return user(id) role(id, name)
	 */
	public List<User_Role> get_U_RByUId(int id);
//d
	/**
	 * 通过用户id删除角色
	 */
	public void delete(int userId);
	/**
	 * 通过角色id删除用户
	 */
	public void deleteU_R_ByRid(int roleId);
	/**
	 * 通过用户和角色id删除
	 */
	public void delete(int userId, int roleId);
}
