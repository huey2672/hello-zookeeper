package com.huey.hello.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 创建节点
 * 
 * @author huey
 */
public class CuratorCreateNodeTest {

	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(3000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		
		// 创建节点
		client.create()
			.creatingParentsIfNeeded()
			.withMode(CreateMode.PERSISTENT)
			.forPath("/curator", "hello".getBytes());
		
		client.close();
	}
	
}
