package com.huey.hello.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 更新节点数据
 * 
 * @author huey
 */
public class CuratorSetDataTest {
	
	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(3000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		
		// 更新节点数据
		client.setData().withVersion(-1).forPath("/curator", "hello curator".getBytes());
		
		client.close();
	}
	
}
