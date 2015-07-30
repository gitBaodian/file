package com.baodian.service.role;

import java.util.List;

import com.baodian.model.role.Authority;
import com.baodian.model.role.Role;

public interface AuthorityManager {
//c
	public void save(Authority authority);
//r
	public List<Authority> findAuthorities();
	/**
	 * 排序读取权限(菜单)
	 * @return [{id,name,url,sort,open,pId,checked}]
	 */
	public String findAsOnsort();
	/**
	 * 结合角色的权限，组合全部权限
	 * @param role 为null时表示只取全部权限
	 * @return [{id,name,open,pId}]
	 */
	public String findAsOnsort(Role role);
	public Authority findA_RById(int id);
	/**
	 * 获取全部权限，角色，以及关系
	 * @return {auths:findAsOnsort(null), roles:[], au_ros:[]}
	 */
	public String findRoAus();
//u
	public void changeAuthority(Authority authority);
	/**
	 * 更改权限的pid display sort
	 * @param auths "id_pid_display(checked)_sort"
	 */
	public void changeAuths(String[] auths);
	/**
	 * 更改权限的name和url
	 * @param authority
	 */
	public void changeA_nu(Authority authority);
	/**
	 * 为权限更改角色
	 * @param json (aid A rid a rid)
	 */
	public String changeRole(String json);
//d
	/**
	 * 删除权限前先检查是否有子权限，并且删除权限角色关系
	 * @param authority
	 * @return
	 */
	public String remove(Authority authority);
}
