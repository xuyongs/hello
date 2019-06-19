package com.eparking.test;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.locks.Lock;

public class PollingThread extends Thread implements Runnable {
    public static Queue<CarInto> queue = new LinkedTransferQueue<CarInto>();
    public static Queue<CarInto> queue1 = new LinkedTransferQueue<CarInto>();
    public static Queue<CarInto> queue2 = new LinkedTransferQueue<CarInto>();

    @Override
    public void run() {
        while (true) {
            while (!queue.isEmpty()) {
                try {
					queue.poll().into(); 
					queue1.poll().out(); 
					queue2.poll().reg();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            //�Ѷ����е���Ϣȫ����ӡ��֮�����߳�����
            synchronized (Lock.class)
            {
                try {
                    Lock.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}