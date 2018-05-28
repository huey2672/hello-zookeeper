package com.huey.hello.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * 获取节点数据
 * 
 * @author huey
 */
public class CarutorGetDataTest {
	
	public static void main(String[] args) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(3000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		
		/*
		 * 获取节点数据
		 */
		Stat stat = new Stat();
		byte[] data = client.getData().storingStatIn(stat).forPath("/curator");
		System.out.println("Node Stat: " + stat);
		System.out.println("Node Data: " + new String(data));
		
		client.close();
	}
	
}
