package com.baodian.dao.user;

import java.util.List;

import com.baodian.model.user.User;
import com.baodian.util.page.UserPage;


public interface UserDao {
//c
	public void save(User u);
//r
	/**
	 * 分页查找用户的id name account department
	 * @param page
	 * @return
	 */
	public List<User> getU_inadOnPage(UserPage page);
	/**
	 * 根据账号获取密码
	 * @param account 账号
	 * @return 密码
	 */
	public String getU_passByAccount(String account);
	/**
	 * 查找用户的id name account department
	 * @param id
	 * @return
	 */
	public User getU_inadById(int id);
	/**
	 * 根据部门id查找此部门人数
	 * @param id 部门id
	 * @return
	 */
	public int getU_NumbyD_id(int id);
	/**
	 * 根据账号获取用户信息, 前提:此账户一定存在
	 * @param account 账号
	 * @return user
	 */
	public User getUserByU_a(String account);
	/**
	 * 根据账号查找用户个数，在注册和更改时用于判断此账号是否已经有人使用
	 * @param account
	 * @param uId 使用于更改账号时的查询
	 * @return
	 */
	public int getU_NumByAc(String account, int uId);
	/**
	 * 根据部门id组查找此部门组中所有人员
	 * @param pids "1,2,3"
	 * @return User(id name Department(did))
	 */
	public List<User> getU_ByDids(String pids);
	/**
	 * 根据部门id查找此部门中所有人员
	 * @param did 1
	 * @return User(id name)
	 */
	public List<User> getU_ByDid(int did);
	/**
	 * 检查是否存在
	 * @return true 存在 false 不存在
	 */
	public boolean chkExit(int uid);
//u
	/**
	 * 更改用户信息及部门
	 * @param user
	 * @param depmId
	 */
	public void update(User user, int depmId);
	/**
	 * 根据账号更改密码
	 * @param user
	 */
	public void updatePW(User user);
	/**
	 * 为用户更改部门
	 */
	public void updateDpm(int uid, int did);
	/**
	 * 为用户更改总容量
	 */
	public void updateMaxSize(int uid, long fielSize);
	/**
	 * 为用户更改上传大小
	 */
	public void updateUploadSize(int uid, long fielSize);
//d
	public void delete(User user);
	
	public User getUserByName(String username);
	
	public List<String> getAllUserName();

}
