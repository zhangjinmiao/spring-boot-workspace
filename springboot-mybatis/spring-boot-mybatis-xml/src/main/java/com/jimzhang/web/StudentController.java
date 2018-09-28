package com.jimzhang.web;

import com.jimzhang.entity.StudentEntity;
import com.jimzhang.service.StudentService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
@RestController
public class StudentController {

  @Resource
  private StudentService studentService;

  private Lock lock = new ReentrantLock();

  @RequestMapping("/serialId")
  public void testSerialId(HttpServletRequest request){
    System.out.println("+++++++++++++++");
    String serialId = request.getParameter("serialId");
    boolean flag = studentService.testUnique(Long.parseLong(serialId));
    System.out.println(flag);

    if (flag){
      System.out.println("不唯一....");
    }else{
      /*synchronized (this) {
        boolean ff = studentService.testUnique(Long.parseLong(serialId));
        if (ff) {
          System.out.println("不唯一.....");
        } else {
          // insert
          StudentEntity entity = new StudentEntity(Long.parseLong(serialId), "wangwu", 20);
          try {
            studentService.save(entity);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }*/
      lock.lock();
      try{
        boolean ff = studentService.testUnique(Long.parseLong(serialId));
        if (ff) {
          System.out.println("不唯一.....");
        } else {
          // insert
          StudentEntity entity = new StudentEntity(Long.parseLong(serialId), "wangwu", 20);
          try {
            studentService.save(entity);
            System.out.println("插入++++++++");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }finally {
        lock.unlock();
      }
    }
  }
}
