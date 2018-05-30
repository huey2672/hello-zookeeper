package com.huey.hello.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用 Curator 创建会话
 * 
 * @author huey
 */
public class CuratorConnectionTest {

	public static void main(String[] args) throws Exception {
		// 
		CuratorFramework client = CuratorFrameworkFactory.newClient(
				"127.0.0.1:2181", 5000, 3000, new ExponentialBackoffRetry(1000, 3));
		client.start();
		System.out.println("ZooKeeper session has been established.");
		client.close();
	}

}
