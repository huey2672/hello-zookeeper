package com.huey.hello.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 创建会话的示例
 * @author huey
 */
public class ZkConnectionTest {

	public static void main(String[] args) throws Exception {
		final CountDownLatch connectedSignal = new CountDownLatch(1);
		
		/**
         * ZooKeeper 客户端和服务器会话的建立是一个异步的过程
         * 构造函数在处理完客户端的初始化工作后立即返回，在大多数情况下，并没有真正地建立好会话
         * 当会话真正创建完毕后，Zookeeper 服务器会向客户端发送一个事件通知
         */
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});

		System.out.println(zk.getState());	// CONNECTING
		
		// 阻塞线程，等待会话创建
		connectedSignal.await();
		
		System.out.println(zk.getState());	// CONNECTED
		
		// 关闭会话
		zk.close();
		
		System.out.println(zk.getState());	// CLOSED
	}
}
