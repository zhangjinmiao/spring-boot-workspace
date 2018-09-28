package com.jimzhang.test;

import java.util.Random;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
public class MyUnit extends Thread {

  @Override
  public void run() {
    //开10个线程，模拟10个用户
    for(int i = 0;i < 10; i++){
      Thread thread = new Thread(new MyThread());
      thread.setName(" user NO."+(i+1));
      thread.start();
//      try {
//        //线程休眠时间1-5秒，每间隔1-5秒开启一个线程，模拟一个用户进行访问
//        Thread.sleep(1000*(new Random().nextInt(5)+1));
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
    }
  }

  public static void main(String[] args) {
    new MyUnit().start();
  }
}
