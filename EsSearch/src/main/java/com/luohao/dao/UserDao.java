package com.luohao.dao;

import java.util.List;
import java.util.Map;

import com.luohao.entity.User;

/**
 * 用户管理
 * @author Administrator
 *
 */
public interface UserDao {

	/**
	 * 获取用户的信息
	 * @param dataMap
	 * @return
	 */
	public List<Object> queryUserMsg(Map<String, Object> where);
	
	/**
	 * 获取用户的信息总数
	 * @param dataMap
	 * @return
	 */
	public Integer queryUserMsgCount(Map<String, Object> where);
	
	/**
	 * 根据id获取用户的信息
	 * @param dataMap
	 * @return
	 */
	public List<User> queryUserById(Map<String, Object> where);
	
	/**
	 * 登录
	 * @param dataMap
	 * @return
	 */
	public String login(Map<String, Object> where);
	
	/**
	 * 新增用户
	 * @param dataMap
	 * @return
	 */
	public Integer addUser(Map<String, Object> where);
	
	/**
	 * 修改用户
	 * @param dataMap
	 * @return
	 */
	public Integer updateUser(Map<String, Object> where);
	
	/**
	 * 删除用户
	 * @param dataMap
	 * @return
	 */
	public Integer delUser(Map<String, Object> where);
}
