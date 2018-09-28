package com.jimzhang.test;

import cn.hutool.http.HttpUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
public class MyThread implements Runnable {


  @Override
  public void run() {
    request();

    System.out.println(
        DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"  "+Thread.currentThread().getName()+"  visit");
//    try {
//      //线程休眠时间1-5秒
//      Thread.sleep(1000*(new Random().nextInt(5)+1));
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }

  private void request(){
    HashMap<String, Object> paramMap = new HashMap<>();
    paramMap.put("serialId", "130");
    String s = HttpUtil.get("http://127.0.0.1:8080/serialId", paramMap);
    System.out.println("result:" + s);
  }

}
