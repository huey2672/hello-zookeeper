package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 以同步的方式创建节点的示例
 * @author huey
 */
public class ZkAsyncCreateNodeTest {
	public static void main(String[] args) throws Exception {
		final CountDownLatch connectedSignal = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});
		connectedSignal.await();
		
		zk.create(
			"/zk-huey-async", 
			"hello".getBytes(), 
			Ids.OPEN_ACL_UNSAFE, 
			CreateMode.PERSISTENT, 
			new AsyncCallback.StringCallback() {
				@Override
				public void processResult(int rc, String path, Object ctx, String name) {
					// 服务器响应码
					System.out.println("ResultCode: " + rc);
					// 接口调用时传入API的数据节点的路径参数值
					System.out.println("Znode: " + path);
					// 接口调用时传入API的ctx参数值
					System.out.println("Context: " + (String) ctx);
					// 实际在服务端创建的节点名
					System.out.println("Real Path: " + name);
				}
			}, 
			"The Context"
		);		

		zk.close();		
		System.in.read();
	}
}
