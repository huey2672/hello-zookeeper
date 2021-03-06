package com.huey.hello.zookeeper.curator;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 获取子节点列表
 * 
 * @author huey
 */
public class CuratorGetChildrenTest {

	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(3000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		
		// 获取子节点列表
		List<String> chlidren = client.getChildren().forPath("/");
		System.out.println("Children of '/': " + chlidren);
		
		client.close();
	}
	
}
