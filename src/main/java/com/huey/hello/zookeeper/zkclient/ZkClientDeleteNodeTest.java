package com.huey.hello.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 删除节点
 * 
 * @author huey
 */
public class ZkClientDeleteNodeTest {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);

		// 删除节点
		zkClient.delete("/zkclient/foo/bar");

		// 递归删除节点
		zkClient.deleteRecursive("/zkclient");

		zkClient.close();
	}

}
