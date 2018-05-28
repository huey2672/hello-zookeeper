package com.huey.hello.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * 检测节点是否存在
 * 
 * @author huey
 */
public class CarutorCheckExistsTest {

	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(3000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		
		// 检测节点是否存在
		Stat stat = client.checkExists().forPath("/curator");
		if (stat != null) {
			System.out.println("Node Stat: " + stat);
		} else {
			System.out.println("The node is nonexistent");
		}
		
		client.close();
	}
	
}
