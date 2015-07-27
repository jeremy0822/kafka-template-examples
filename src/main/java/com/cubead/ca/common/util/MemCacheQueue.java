package com.cubead.ca.common.util;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.log4j.Logger;

public class MemCacheQueue {
	Logger log = Logger.getLogger(this.getClass());

	public static final String CA_START_KEY = "ca_";

	public static final int TIMEOUT = 60 * 60 * 24;

	public static final int SLEEPTIME = 10;

	public MemcachedClient memcachedClient;

	public MemCacheQueue() throws TimeoutException,
			InterruptedException, MemcachedException {
		this.memcachedClient = (MemcachedClient) SpringContextUtil
				.getBean("memcachedClient");
	}

	public boolean setCache(String key, Object value)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.set(CA_START_KEY+key, TIMEOUT, value);
	}

	public boolean addCache(String key, Object value)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.add(CA_START_KEY+key, TIMEOUT, value);
	}

	public Object getCache(String key) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.get(CA_START_KEY+key);
	}

	public boolean deleteCache(String key) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.delete(CA_START_KEY+key);
	}

}