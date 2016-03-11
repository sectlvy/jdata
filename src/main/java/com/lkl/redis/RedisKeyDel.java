package com.lkl.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisKeyDel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RedisDao redisDao = new RedisDao(1, 1, 40, 0, false, 0, "192.168.1.13", 6379, 2);
		
		Jedis jedis = redisDao.getJereids();
		Set<String> keset = jedis.keys("*");
		
		keset.forEach(a -> {
			System.out.println(a);
			System.out.println(jedis.get(a));
		});
	}

}
