package com.baodian.service.role.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.baodian.dao.role.AuthorityDao;
import com.baodian.dao.role.RoleDao;
import com.baodian.dao.role.Role_AuthDao;
import com.baodian.model.role.Authority;
import com.baodian.model.role.Role;
import com.baodian.model.role.Role_Auth;
import com.baodian.service.role.AuthorityManager;
import com.baodian.util.JSONValue;

@Component("authorityManager")
public class AuthorityManagerImpl implements AuthorityManager {
	private AuthorityDao authorityDao;
	@Resource(name="authorityDao")
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	private Role_AuthDao role_AuthDao;
	@Resource(name="roleAuthDao")
	public void setRole_AuthDao(Role_AuthDao role_AuthDao) {
		this.role_AuthDao = role_AuthDao;
	}
	private RoleDao roleDao;
	@Resource(name="roleDao")
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
//c
	public void save(Authority authority) {
		this.authorityDao.save(authority);
	}
//r
	public List<Authority> findAuthorities() {
		return this.authorityDao.getAuthorities();
	}
	public String findAsOnsort() {
		List<Authority> auths = authorityDao.getAsOnsort();
		if(auths.size() == 0) {
			return "[]";
		}
		StringBuilder json = new StringBuilder();
		json.append('[');
		for(Authority a : auths) {
			json.append("{\"id\": \"" + a.getId() + 
					"\",\"name\":\"" + JSONValue.escape(a.getName()) +
					"\",\"url\":\"" + JSONValue.escape(a.getUrl()) +
					"\",\"sort\":\"" + a.getSort() +
					"\",\"open\":true");
			if(a.getParent() != null)
				json.append(",\"pId\": \"" + a.getParent().getId() +"\"");
			json.append(",\"checked\":" + (a.getDisplay()==1?"true": "false") + "},");
		}
		return json.substring(0, json.length()-1) + ']';
	}
	public String findAsOnsort(Role role) {
		List<Authority> authorities = authorityDao.getAsOnsort();
		StringBuilder json = new StringBuilder();
		json.append('[');
		for(Authority a : authorities) {
			json.append("{\"id\": \"" + a.getId() +
					"\",\"name\":\"" + JSONValue.escape(a.getName()) +
					"\",\"open\":true");
			if(a.getParent() != null)
				json.append(",\"pId\": \"" + a.getParent().getId() +"\"");
			if(role != null)
				for(Role_Auth ra : role.getRole_Authorities()) {
					if(ra.getAuthority().getId() == a.getId()) {
						json.append(",\"checked\":true");
						break;
					}
				}
			json.append("},");
		}
		if(json.length() != 1)
			return json.substring(0, json.length()-1) + ']';
		return json.toString() + ']';
	}
	public Authority findA_RById(int id) {
		Authority auth = this.authorityDao.getAuthorityById(id);
		return auth;
	}
	public String findRoAus() {
		return "{auths:" + findAsOnsort(null) +
				",roles:" + roleDao.getRoles() +
				",ro_aus:" + role_AuthDao.getRoAus() + "}";
	}
//u
	public void changeAuthority(Authority authority) {
		this.authorityDao.update(authority);
	}
	public void changeAuths(String[] auths) {
		for(String auth : auths) {
			String[] ids = auth.split("_");
			authorityDao.updateSort(Integer.parseInt(ids[0]),
					Integer.parseInt(ids[1]), Integer.parseInt(ids[2]),
					Integer.parseInt(ids[3]));
		}
	}
	public void changeA_nu(Authority authority) {
		authorityDao.updateA_nu(authority);
	}
	public String changeRole(String json) {
		if(json==null || json.length()==0) {
			return "{\"status\":1,\"mess\":\"输入有误！\"}";
		}
		String[] ids = json.split("A");
		int aid = 0;
		try {
			aid = Integer.parseInt(ids[0]);
		} catch(NumberFormatException e) {}
		if(! authorityDao.chkExit(aid)) {
			return "{\"status\":1,\"mess\":\"权限不存在！\"}";
		}
		role_AuthDao.deleteR_A_ByAid(aid);
		if(ids.length > 1) {
			int rid = 0;
			Set<Integer> rids = new HashSet<Integer>();
			for(String role : ids[1].split("a")) {
				try {
					rid = Integer.parseInt(role);
				} catch(NumberFormatException e) {}
				if(rids.add(rid) && roleDao.chkExit(rid)) {
					role_AuthDao.save(new Role_Auth(rid, aid));
				}
			}
		}
		return "{\"status\":0}";
	}
//d
	public String remove(Authority authority) {
		int num = authorityDao.getChildrenNum(authority.getId());
		if(num > 0) {
			return "{\"status\":1, \"mess\": \"存在" + num + "个子权限！\"}";
		}
		this.role_AuthDao.deleteR_A_ByAid(authority.getId());
		this.authorityDao.delete(authority);
		return "{\"status\":0}";
	}
//o
	public org.springframework.security.core.userdetails.User currentUser() {
		try {
			return (org.springframework.security.core.userdetails.User)
					SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch(ClassCastException e) {
			return null;
		}
	}
	public Set<Integer> currentMenu() {
		try {
			return ((org.springframework.security.core.userdetails.User)
					SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUserMenu();
		} catch(ClassCastException e) {
			return new HashSet<Integer>();
		}
	}
}
