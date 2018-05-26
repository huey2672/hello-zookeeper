package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 以同步的方式获取节点数据内容
 * 
 * @author huey
 */
public class ZkGetDataTest {

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

		/*
		 * 获取节点数据
		 */
		Stat nodeStat = new Stat();
		byte[] nodeData = zk.getData("/zk-huey", false, nodeStat);
		System.out.println("Node Data: " + new String(nodeData));
		System.out.println("Node Stat: " + nodeStat);

		zk.close();
	}

}
