package com.huey.hello.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 使用 ZkClient 创建会话
 * 
 * @author huey
 */
public class ZkClientConnectionTest {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
		System.out.println("ZooKeeper session has been established.");
		zkClient.close();
	}

}
