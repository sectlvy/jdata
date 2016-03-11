package com.lkl.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;

public class RedisDao {
	private Logger log = LoggerFactory.getLogger(RedisDao.class);
	public int redisN;
	private static JedisPool pool;

	public RedisDao(int redisMaxActive, int maxTotal, int redisMaxIdle, int redisMaxWait, boolean testOnBorrow, int timeout, String ip, int port, int redisN) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(redisMaxActive);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxWaitMillis(redisMaxWait);

		log.info("start to init redis ip[" + ip + "] port [" + port + "]");
		pool = new JedisPool(jedisPoolConfig, ip, port, 10000);
		Jedis jredis = getJereids();
		try {
			jredis.select(redisN);
			this.redisN = redisN;
		} catch (Exception e) {
			pool.returnBrokenResource(jredis);
			log.error("", e);
		} finally {
			pool.returnResource(jredis);
		}
	}

	public Jedis getJereids() {
		Jedis jredis = pool.getResource();
		jredis.select(redisN);
		return jredis;
	}

	public String blpop(String queuename) {
		Jedis jredis = getJereids();
		try {
			List<String> blist = jredis.blpop(Integer.MAX_VALUE, queuename);
			if (blist != null && blist.size() == 2) {
				String jobStr = blist.get(1);
				return jobStr;
			} else {
				log.error("redis error " + JSON.toJSONString(blist));
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jredis);
			log.error("", e);
		} finally {
			pool.returnResource(jredis);
		}
		return null;
	}

	public String brpop(String queuename) {
		Jedis jredis = getJereids();
		try {
			List<String> blist = jredis.brpop(Integer.MAX_VALUE, queuename);
			if (blist != null && blist.size() == 2) {
				String jobStr = blist.get(1);
				return jobStr;
			} else {
				log.error("redis error " + JSON.toJSONString(blist));
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jredis);
			log.error("", e);
		} finally {
			pool.returnResource(jredis);
		}
		return null;
	}

	public boolean lpush(String jonname, String value) {
		Jedis jredis = getJereids();
		boolean isUnique = false;
		try {
			jredis.select(redisN);
			jredis.lpush(jonname, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jredis);
			log.error("", e);
		} finally {
			pool.returnResource(jredis);
		}
		return isUnique;
	}

	public boolean rpush(String jonname, String value) {
		Jedis jredis = getJereids();
		boolean isUnique = false;
		try {
			jredis.select(redisN);
			jredis.rpush(jonname, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jredis);
			log.error("", e);
		} finally {
			pool.returnResource(jredis);
		}
		return isUnique;
	}

	public String set(String keyname, String value) {
		Jedis jredis = getJereids();
		try {
			String code = jredis.set(keyname, value);
			return code;
		} catch (Exception e) {
			pool.returnBrokenResource(jredis);
			log.error("", e);
		} finally {
			pool.returnResource(jredis);
		}
		return null;
	}

	public String get(String keyname) {
		Jedis jredis = getJereids();
		try {
			String code = jredis.get(keyname);

			return code;
		} catch (Exception e) {
			pool.returnBrokenResource(jredis);
			log.error("", e);
		} finally {
			pool.returnResource(jredis);
		}
		return null;
	}
}
