package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 以异步的方式删除节点
 * 
 * @author huey
 */
public class ZkAsyncDeleteNodeTest {

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

		// 版本指定 -1，表示客户端基于数据的最新版本操作
		zk.delete("/zk-huey-async", -1, new AsyncCallback.VoidCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx) {
				System.out.println("ResultCode: " + rc);
				System.out.println("Znode: " + path);
				System.out.println("Context: " + (String) ctx);
			}
		}, "The Context");

		zk.close();
		System.in.read();
	}

}
