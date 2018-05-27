package com.huey.hello.zookeeper.zkclient;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;

/**
 * 获取子节点列表
 * 
 * @author huey
 */
public class ZkClientGetChildrenTest {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);

		List<String> children = zkClient.getChildren("/");
		System.out.println(children);

		zkClient.close();
	}

}
