package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

/**
 * 以同步的方式更新节点数据
 * 
 * @author huey
 */
public class ZkSetDataTest {

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

		// 更新节点数据
		Stat stat = zk.setData("/zk-huey", "hello zookeeper".getBytes(), -1);
		System.out.println(stat);

		zk.close();
	}

}
