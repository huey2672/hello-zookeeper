package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 以同步的方式检测节点是否存在
 * 
 * @author huey
 */
public class ZkNodeExistsTest {

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
		
		// 检测节点是否存在。如果节点不存在，那么返回的 Stat 实例是 null。
		Stat stat = zk.exists("/zk-huey", false);
		if (stat != null) {
			System.out.println(stat);
		} else {
			System.out.println("The node is nonexistent");
		}
		
		
		zk.close();
	}

}
