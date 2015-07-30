package com.baodian.dao.role;

import java.util.List;

import com.baodian.model.role.Authority;

public interface AuthorityDao {
//c
	public void save(Authority authority);
//r
	public List<Authority> getAuthorities();
	/**
	 * 获取所有的角色与权限关系
	 * @return
	 */
	public List<Authority> getAll_AR_();
	/**
	 * 排序读取权限(菜单)
	 * @return
	 */
	public List<Authority> getAsOnsort();
	/**
	 * 排序读取显示的权限(菜单)
	 * @return
	 */
	public List<Authority> getAsOnsd();
	public Authority getAuthorityById(int id);
	/**
	 * 获取子权限个数
	 * @param id
	 * @return
	 */
	public int getChildrenNum(int id);
	/**
	 * 获取无角色的权限id
	 * @return
	 */
	public List<Integer> getA_OnNoRole();
	/**
	 * 检查是否存在
	 * @return true 存在 false 不存在
	 */
	public boolean chkExit(int aid);
//u
	public void update(Authority authority);
	/**
	 * 更新权限的父节点 顺序 显示
	 * @param id
	 * @param pid
	 * @param display 1显示 0不显示 -1不更新
	 * @param sort -1不更新 其它为顺序
	 */
	public void updateSort(int id, int pid, int display, int sort);
	/**
	 * 更改权限的名字和地址
	 * @param authority
	 */
	public void updateA_nu(Authority authority);
//d
	public void delete(Authority authority);
	
}

