package com.luohao.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
		Jedis jedis = RedisPool.getInstance();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// 模拟假数据
		for (int i = 0; i < 2; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ONE", "1");
			map.put("TWO", "2");
			map.put("THR", "3");
			list.add(map);
		}
		// 存到缓存
		for (int i = 0; i < list.size(); i++) {
			jedis.rpush("MYLIST", list.get(i).get("ONE").toString());
			jedis.rpush("MYLIST", list.get(i).get("TWO").toString());
			jedis.rpush("MYLIST", list.get(i).get("THR").toString());
		}
		// 取缓存
		List<String> lrange = jedis.lrange("MYLIST", 0, -1);
		for (int i = 0; i < lrange.size(); i++) {
			System.out.println("MYLIST:"+lrange.get(i));
		}
	}
}
