package com.huey.hello.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 更新数据
 * 
 * @author huey
 */
public class ZkClientWriteDataTest {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
		
		zkClient.writeData("/zkclient", "hello");
		
		zkClient.close();
	}
	
}
