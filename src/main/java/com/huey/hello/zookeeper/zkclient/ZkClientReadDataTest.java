package com.huey.hello.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 获取节点的数据内容
 * @author huey
 */
public class ZkClientReadDataTest {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);

		// 获取节点的数据内容
		Object data = zkClient.readData("/zkclient", true);
		if (data != null) {
			System.out.println("Node Data: " + data);
		} else {
			System.out.println("The node is nonexistent.");
		}
		
		zkClient.close();
	}

}
