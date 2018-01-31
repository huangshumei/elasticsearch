package com.luohao.service;

import java.util.List;
import java.util.Map;

import com.luohao.entity.User;

public interface UserService {

	/**
	 * 获取用户的信息
	 * 
	 * @param dataMap
	 * @return
	 */
	public List<Object> queryUserMsg(Map<String, Object> where);

	/**
	 * 登录
	 * 
	 * @param dataMap
	 * @return
	 */
	//public EntityResult<?> login(Map<String, Object> where);

	/**
	 * 新增用户
	 * 
	 * @param dataMap
	 * @return
	 */
	//public EntityResult<?> addUser(Map<String, Object> where);

	/**
	 * 修改用户
	 * 
	 * @param dataMap
	 * @return
	 */
	//public EntityResult<?> updateUser(Map<String, Object> where);
	
	/**
	 * 修改用户密码
	 * @param dataMap
	 * @return
	 */
	//public EntityResult<?> updateUserPwd(Map<String, Object> where);

	/**
	 * 删除用户
	 * 
	 * @param dataMap
	 * @return
	 */
	//public EntityResult<?> delUser(Map<String, Object> where);
	
	/**
	 * 判断用户是否存在
	 * @param dataMap
	 * @return
	 */
	//public EntityResult<?> existUser(Map<String, Object> where);
	
	/**
	 * 获取用户的信息总数
	 * @param dataMap
	 * @return
	 */
	public Integer queryUserMsgCount(Map<String, Object> where);
}
