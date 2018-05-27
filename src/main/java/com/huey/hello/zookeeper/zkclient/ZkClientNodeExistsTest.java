package com.huey.hello.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 判断节点是否存在
 * 
 * @author huey
 */
public class ZkClientNodeExistsTest {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);

		// 判断节点是否存在
		boolean nodeExists = zkClient.exists("/zkclient");
		if (nodeExists) {
			System.out.println("The node is existent");
		} else {
			System.out.println("The node is nonexistent");
		}

		zkClient.close();
	}

}
