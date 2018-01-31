package com.luohao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luohao.dao.UserDao;
import com.luohao.entity.User;
import com.luohao.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	public List<Object> queryUserMsg(Map<String, Object> where) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer queryUserMsgCount(Map<String, Object> where) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	@Autowired
//	private UserDao userDao;
//
//	/**
//	 * 鑾峰彇鐢ㄦ埛鐨勪俊鎭�
//	 * 
//	 * @param dataMap
//	 * @return
//	 */
//	public List<Object> queryUserMsg(Map<String, Object> where) {
//		return userDao.queryUserMsg(where);
//	}
//
//	/**
//	 * 鐧诲綍
//	 * 
//	 * @param dataMap
//	 * @return
//	 */
//	public EntityResult<?> login(Map<String, Object> where) {
//		EntityResult<?> result = new EntityResult<>();
//		String login = userDao.login(where);
//		if ("0".equals(login)) {
//			result.setMsg("璇锋鏌ヨ处鍙锋垨瀵嗙爜~");
//			result.setResult("0");
//		} else {
//			result.setMsg("鎿嶄綔鎴愬姛");
//			result.setResult("1");
//		}
//		return result;
//	}
//	
//	/**
//	 * 淇敼鐢ㄦ埛
//	 * @param dataMap
//	 * @return
//	 */
//	public EntityResult<?> updateUser(Map<String, Object> where) {
//		EntityResult<?> result = new EntityResult<>();
//		Integer updateUser = userDao.updateUser(where);
//		if (updateUser >= 0) {
//			result.setMsg("鎿嶄綔鎴愬姛");
//			result.setResult("1");
//		} else {
//			result.setMsg("淇濆瓨澶辫触");
//			result.setResult("0");
//		}
//		return result;
//	}
//	
//	/**
//	 * 淇敼鐢ㄦ埛瀵嗙爜
//	 * @param dataMap
//	 * @return
//	 */
//	public EntityResult<?> updateUserPwd(Map<String, Object> where) {
//		EntityResult<?> result = new EntityResult<>();
//		where.put("password", where.get("oldPassword").toString());
//		String login = userDao.login(where);
//		if ("0".equals(login)) {
//			result.setMsg("鏃у瘑鐮佷笉姝ｇ‘锛岃妫�鏌�!");
//			result.setResult("2");
//		} else {
//			where.put("password", where.get("newPassword").toString());
//			Integer updateUser = userDao.updateUser(where);
//			if (updateUser >= 0) {
//				result.setMsg("鎿嶄綔鎴愬姛");
//				result.setResult("1");
//			} else {
//				result.setMsg("鎿嶄綔澶辫触");
//				result.setResult("0");
//			}
//		}
//		return result;
//	}
//
//	
//	/**
//	 * 鍒ゆ柇鐢ㄦ埛鏄惁瀛樺湪
//	 * @param dataMap
//	 * @return
//	 */
//	public EntityResult<?> existUser(Map<String, Object> where) {
//		EntityResult<?> result = new EntityResult<>();
//		List<User> queryUserById = userDao.queryUserById(where);
//		if (queryUserById.size() == 0 && queryUserById.isEmpty()) {
//			result.setMsg("鐢ㄦ埛涓嶅瓨鍦紝鍙互淇濆瓨");
//			result.setResult("1");
//		} else {
//			result.setMsg("鐢ㄦ埛閲嶅,璇蜂慨鏀�");
//			result.setResult("0");
//		}
//		return result;
//	}
//	
//	/**
//	 * 鑾峰彇鐢ㄦ埛鐨勪俊鎭�绘暟
//	 * @param dataMap
//	 * @return
//	 */
//	public Integer queryUserMsgCount(Map<String, Object> where) {
//		return userDao.queryUserMsgCount(where);
//	}
//
//	@Override
//	public EntityResult<?> addUser(Map<String, Object> where) {
//		return null;
//	}
//
//	@Override
//	public EntityResult<?> delUser(Map<String, Object> where) {
//		return null;
//	}
//
}
