package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 以异步的方式获取节点数据内容
 * 
 * @author huey
 */
public class ZkAsyncGetDataTest {

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

		zk.getData("/zk-huey-async", true, new AsyncCallback.DataCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
				System.out.println("ResultCode: " + rc);
				System.out.println("ZNode: " + path);
				System.out.println("Context: " + ctx);
				System.out.println("NodeData: " + new String(data));
				System.out.println("Stat: " + stat);
			}
		}, "The Context");

		zk.close();
		System.in.read();
	}

}
