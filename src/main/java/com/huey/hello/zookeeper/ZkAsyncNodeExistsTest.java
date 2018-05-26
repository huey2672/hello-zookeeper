package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooKeeper;

/**
 * 以异步的方式检测节点是否存在
 * 
 * @author huey
 */
public class ZkAsyncNodeExistsTest {

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

		// 异步检测节点是否存在
		zk.exists("/zk-huey-async", false, new AsyncCallback.StatCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, Stat stat) {
				System.out.println("ResultCode: " + KeeperException.Code.get(rc));
				System.out.println("ZNode: " + path);
				System.out.println("Context: " + (String) ctx);
				System.out.println("Stat: " + stat);
			}
		}, "The Context");

		zk.close();
		System.in.read();
	}

}
