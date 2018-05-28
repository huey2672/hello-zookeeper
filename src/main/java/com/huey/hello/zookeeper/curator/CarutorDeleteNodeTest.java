package com.huey.hello.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 删除节点
 * 
 * @author huey
 */
public class CarutorDeleteNodeTest {

	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(3000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		
		// 删除节点
		client.delete()
			.guaranteed()
			.deletingChildrenIfNeeded()
			.withVersion(-1)
			.forPath("/curator");
		
		client.close();
	}
	
}
