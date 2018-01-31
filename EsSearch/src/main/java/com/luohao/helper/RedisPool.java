package com.luohao.helper;

import redis.clients.jedis.Jedis;

public class RedisPool {

	private static final String HOST = "127.0.0.1";
	private static final int PORT = 6379;
	
	private RedisPool(){}
	
	private static Jedis jedis = null;
	
	public static synchronized Jedis getInstance(){
		if (jedis == null) {
			jedis = new Jedis(HOST, PORT);
		}
		return jedis;
	}
}
