package com.baodian.service.role.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baodian.dao.role.RoleDao;
import com.baodian.dao.role.Role_AuthDao;
import com.baodian.dao.user.User_RoleDao;
import com.baodian.model.role.Role;
import com.baodian.model.role.Role_Auth;
import com.baodian.service.role.RoleManager;
import com.baodian.util.JSONValue;

@Service("roleManager")
public class RoleManagerImpl implements RoleManager {
	private RoleDao roleDao;
	@Resource(name="roleDao")
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	private Role_AuthDao role_AuthDao;
	@Resource(name="roleAuthDao")
	public void setRole_AuthDao(Role_AuthDao role_AuthDao) {
		this.role_AuthDao = role_AuthDao;
	}
	private User_RoleDao user_RoleDao;
	@Resource(name="userRoleDao")
	public void setUser_RoleDao(User_RoleDao user_RoleDao) {
		this.user_RoleDao = user_RoleDao;
	}
//c
	public void save(Role role, int[] ids) {
		roleDao.save(role);
		if(ids != null)
			for(int aId : ids)
				role_AuthDao.save(new Role_Auth(role.getId(), aId));
	}
//r
	public String findRoles() {
		return roleDao.getRoles();
	}
	public Role findRA_ByR_id(int id) {
		Role role = roleDao.getRoleById(id);
		role.setName(JSONValue.escape(role.getName()));
		if(role != null)
			role.setRole_Authorities(role_AuthDao.getAIdsById(id));
		return role;
	}
//u
	public void changeRA(int id, int[] ids) {
		role_AuthDao.deleteR_A_ByRid(id);
		if(ids != null)
			for(int aId : ids)
				role_AuthDao.save(new Role_Auth(id, aId));
	}
	public void changeName(Role role) {
		roleDao.updateName(role);
	}
	public void changeSort(String[] roles) {
		for(String role : roles) {
			String[] ids = role.split("_");
			roleDao.updateSort(Integer.parseInt(ids[0]),
					Integer.parseInt(ids[1]), Integer.parseInt(ids[2]));
		}
	}
//d
	public void remove(Role role) {
		user_RoleDao.deleteU_R_ByRid(role.getId());
		role_AuthDao.deleteR_A_ByRid(role.getId());
		roleDao.delete(role);
	}
}
