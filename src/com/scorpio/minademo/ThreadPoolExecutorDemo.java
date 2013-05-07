package com.scorpio.minademo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorDemo extends ThreadPoolExecutor implements Runnable {
	private boolean isPaused;
	private ReentrantLock pauseLock = new ReentrantLock();
	private Condition unpaused = pauseLock.newCondition();

	public ThreadPoolExecutorDemo(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		// TODO Auto-generated method stub
		super.beforeExecute(t, r);
		pauseLock.lock();
		try {
			while (isPaused)
				unpaused.await();
		} catch (InterruptedException ie) {
			t.interrupt();
		} finally {
			pauseLock.unlock();
		}
	}

	public void pause() {
		pauseLock.lock();
		try {
			isPaused = true;
		} finally {
			pauseLock.unlock();
		}
	}

	public void resume() {
		pauseLock.lock();
		try {
			isPaused = false;
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}
	public static void main(String[] args) {
		ThreadPoolExecutorDemo tp = new ThreadPoolExecutorDemo(5, 10, 5000, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
		Thread th1 = new Thread(tp);
		Thread th2 = new Thread(tp);
		Thread th3 = new Thread(tp);
		Thread th4 = new Thread(tp);
		tp.execute(th1);
		tp.execute(th2);
		tp.execute(th3);
		tp.execute(th4);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("当前时间1：" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < new Random().nextInt(100)+1; i++) {
			System.out.println(i);
		}
		System.out.println("over!!!--------------------");
		System.out.println("当前时间2：" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}
}
