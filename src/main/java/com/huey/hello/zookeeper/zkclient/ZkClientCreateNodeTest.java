package com.huey.hello.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * 创建节点
 * 
 * @author huey
 */
public class ZkClientCreateNodeTest {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);

		// 创建节点
		zkClient.create("/zkclient", "hello zkclient", CreateMode.PERSISTENT);

		// 递归创建节点
		zkClient.createPersistent("/zkclient/foo/bar", true);

		zkClient.close();
	}

}
