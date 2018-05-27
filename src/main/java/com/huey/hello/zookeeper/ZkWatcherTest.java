package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooKeeper;

/**
 * 事件监听器的使用
 * 
 * @author huey
 */
public class ZkWatcherTest {

	public static void main(String[] args) throws Exception {
		final CountDownLatch connectedSignal = new CountDownLatch(1);
		final ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					if (event.getType() == EventType.None && event.getPath() == null) {
						connectedSignal.countDown();
					}
				}
			}
		});
		connectedSignal.await();

		/*
		 * 重新设置默认的监听器，构造方法的监听器仅用于响应会话连接。 
		 * 需要注意的是，由于监听器的事件通知是一次性的，创建会话连接时已经触发通知，此时监听器是失效的。
		 */
		zk.register(new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					try {
						String nodePath = event.getPath();
						if (event.getType() == EventType.NodeCreated) {
							System.out.println("Create a node: " + nodePath);
							// 监听器的事件通知是一次性的，需要反复注册
							String data = new String(zk.getData(nodePath, true, null));
							System.out.println("Now the node data is " + data);
						} else if (event.getType() == EventType.NodeDeleted) {
							System.out.println("The node " + nodePath + " is deleted.");
						} else if (event.getType() == EventType.NodeDataChanged) {
							System.out.println("The data of node " + nodePath + " is changed.");
							String data = new String(zk.getData(nodePath, true, null));
							System.out.println("Now the node data is " + data);
						} else if (event.getType() == EventType.NodeChildrenChanged) {
							System.out.println("The children of node " + nodePath + " is changed.");
							System.out.println("Now, the children is " + zk.getChildren(nodePath, true));
						} else {
							System.out.println("Reveice the watched event: " + event);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		/*
		 * 检测节点是否存在，同时注册监听器。 
		 * 需要注意的是，监听器仅监听指定的节点，其他节点的操作不会触发事件通知。
		 */
		String nodePath = "/zk-huey";
		Stat stat = zk.exists(nodePath, true);

		if (stat == null) {
			// 触发 NodeCreated 类型的事件通知
			zk.create(nodePath, "hello".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}

		// 触发 NodeDataChanged 类型的事件通知
		zk.setData(nodePath, "hello zk".getBytes(), -1);

		// 使用 getChildren 方法时才能注册子节点变化的事件监听器
		System.out.println("Originally, the children of " + nodePath + " is " + zk.getChildren(nodePath, true));
		/*
		 * 均触发 NodeChildrenChanged 类型的事件通知
		 */
		zk.create(nodePath + "/newNode", "hello".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.delete(nodePath + "/newNode", -1);

		System.in.read();
		// 触发 NodeDeleted 类型的事件通知
		zk.delete(nodePath, -1);

		zk.close();
	}

}
