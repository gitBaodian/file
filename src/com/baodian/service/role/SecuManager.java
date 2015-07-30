package com.baodian.service.role;

public interface SecuManager {
	/**
	 * 初始化权限与角色的关系，保存在Map<url, Collection<roleId>> resourceMapHash中
	 */
	public void initAll_RA_();
	/**
	 * 刷新权限与角色的关系
	 */
	public void refreshAll_RA_();
	/**
	 * 初始化菜单
	 */
	public void initMenu();
	/**
	 * 刷新数据
	 */
	public void refreshMenu();
}
