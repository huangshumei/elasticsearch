package com.luohao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.luohao.entity.User;
import com.luohao.helper.DataConvertHelper;
import com.luohao.helper.ListResultRedis;
import com.luohao.helper.RedisPool;
import com.luohao.helper.ResponseUtil;
import com.luohao.service.UserService;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping(value = "/UserController")
public class ZlbController {
//
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
//
//	@Autowired
//	public UserService userService;
//
//	/**
//	 * 获取用户信息
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/queryUserMsg", method = RequestMethod.POST)
//	public String queryUserMsg(HttpServletRequest request,HttpServletResponse response) {
//		JSONObject result=new JSONObject();
//		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
//		Integer count = userService.queryUserMsgCount(where);
//		result.put("total", count);
//		// 如果条件为空就差缓存，不为空就不查
//		if (StringHelper.IsEmptyOrNull(where.get("firstName"))) {
//			// 得到缓存实例
//			Jedis jedis = RedisPool.getInstance();
//			String userMsgRed = jedis.get("userMsg");
//			// 判断是否存在该缓存 不存在就创建，存在就直接
//			if (StringHelper.IsEmptyOrNull(userMsgRed)) {
//				List<Object> list = userService.queryUserMsg(where);
//				String userMsgListJson = JSON.toJSONString(list);
//				// 加密
//				userMsgListJson = Escape.escape(userMsgListJson);
//				jedis.set("userMsg", userMsgListJson);
//				userMsgListJson = Escape.unescape(userMsgListJson);
//				result.put("rows", userMsgListJson);
//			} else {
//				userMsgRed = Escape.unescape(userMsgRed);
//				result.put("rows", userMsgRed);
//			}
//		} else {
//			List<Object> list = userService.queryUserMsg(where);
//			String userMsgListJson = JSON.toJSONString(list);
//			
//			result.put("rows", userMsgListJson);
//		}
//		ResponseUtil.write(response, result);
//		return null;
//	}
//
//	/**
//	 * 登录
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public EntityResult<?> login(HttpServletRequest request) {
//		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
//		EntityResult<?> result = userService.login(where);
//		return result;
//	}
//
//	/**
//	 * 新增用户
//	 * 
//	 * @param dataMap
//	 * @return
//	 */
//	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
//	public EntityResult<?> addUser(HttpServletRequest request) {
//		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
//		EntityResult<?> result = userService.addUser(where);
//		Jedis jedis = RedisPool.getInstance();
//		jedis.del("userMsg");
//		return result;
//	}
//
//	/**
//	 * 修改用户
//	 * 
//	 * @param dataMap
//	 * @return
//	 */
//	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
//	public EntityResult<?> updateUser(HttpServletRequest request) {
//		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
//		EntityResult<?> result = userService.updateUser(where);
//		Jedis jedis = RedisPool.getInstance();
//		jedis.del("userMsg");
//		return result;
//	}
//
//	/**
//	 * 修改用户密码
//	 * 
//	 * @param dataMap
//	 * @return
//	 */
//	@RequestMapping(value = "/updateUserPwd", method = RequestMethod.POST)
//	public EntityResult<?> updateUserPwd(HttpServletRequest request) {
//		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
//		EntityResult<?> result = userService.updateUserPwd(where);
//		Jedis jedis = RedisPool.getInstance();
//		jedis.del("userMsg");
//		return result;
//	}
//
//	/**
//	 * 删除用户
//	 * 
//	 * @param dataMap
//	 * @return
//	 */
//	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
//	public EntityResult<?> delUser(HttpServletRequest request) {
//		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
//		EntityResult<?> result = userService.delUser(where);
//		Jedis jedis = RedisPool.getInstance();
//		jedis.del("userMsg");
//		return result;
//	}
//
//	/**
//	 * 判断用户是否存在
//	 * 
//	 * @param dataMap
//	 * @return
//	 */
//	@RequestMapping(value = "/existUser", method = RequestMethod.POST)
//	public EntityResult<?> existUser(HttpServletRequest request) {
//		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
//		EntityResult<?> result = userService.existUser(where);
//		return result;
//	}
}
