package com.huey.hello.zookeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 以异步的方式获取子节点列表
 * 
 * @author huey
 */
public class ZkAsyncGetChildrenTest {

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

		// 异步获取子节点列表
		zk.getChildren("/", true, new AsyncCallback.ChildrenCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, List<String> children) {
				System.out.println("ResultCode: " + KeeperException.Code.get(rc));
				System.out.println("ZNode: " + path);
				System.out.println("Context: " + ctx);
				System.out.println("Children: " + children);
			}
		}, "The Context");

		zk.close();
		System.in.read();
	}

}
