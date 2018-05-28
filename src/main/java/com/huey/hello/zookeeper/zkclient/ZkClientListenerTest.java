package com.huey.hello.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 事件监听器的使用
 * 
 * @author huey
 */
public class ZkClientListenerTest {

	public static void main(String[] args) throws Exception {
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);

		zkClient.createPersistent("/zkclient");

		// 注册监听器
		zkClient.subscribeDataChanges("/zkclient", new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println(String.format("The node '%s' is deleted.", dataPath));
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println(String.format("The node '%s' is changed, now its data is '%s'.", dataPath, data));
			}
		});

		zkClient.writeData("/zkclient", "hello world");
		Thread.sleep(1000);

		zkClient.delete("/zkclient");
		Thread.sleep(1000);

		zkClient.close();
	}

}
