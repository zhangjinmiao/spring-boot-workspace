package com.jimzhang.service;

import com.jimzhang.SpringbootEmailApplicationTests;
import com.jimzhang.model.Email;
import com.jimzhang.utils.MailUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;


/**
 * @description: 测试
 * @author: jimzhang
 * @home: <>https://segmentfault.com/u/itzhangjm</>
 * @date: 2017-10-31 16:52
 * @version: V1.0.0
 */
@ImportResource({"classpath:spring-context-dubbo.xml", "classpath:spring-context-task.xml"})
public class ServiceTests extends SpringbootEmailApplicationTests{

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    private String to = "itzjm@qq.com";
    private String subject = "邮件发送测试";
    private String content = "你好，恭喜你注册成功！";

    @Test
    public void testSimpleMail(){
        mailService.sendSimpleMail(to, subject, " Simple mail : " + content);
    }


    @Test
    public void testHtmlMail() throws Exception {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>hello jimzhang ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail(to, "test html mail", content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath = "d:\\file\\对账文件.txt";
        mailService.sendAttachmentsMail(to, "主题：带附件的邮件" , "有附件，请查收！", filePath);
    }


    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "D:\\file\\1.jpg";

        mailService.sendInlineResourceMail(to, "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail(to, "主题：这是模板邮件", emailContent);
    }

    @Autowired
    private IMailService service;

    @Test
    public void testSend() throws Exception {
        Email email= new Email();
        email.setEmail(new String[]{"itzjm@qq.com","zhangjinmiao@xxx.com"});
        email.setSubject("APP Store 充值卡");
        email.setContent("资和信欢迎您");
        email.setTemplate("welcome");
//        service.send(email);
        HashMap<String, String> map = new HashMap<>();
        map.put("imageName", "java.jpg");
        map.put("attachmentFilename", "马踏飞燕.zip");
        email.setKvMap(map);
//        service.sendHtml(email);

        service.sendThymeleaf(email);
//        service.sendFreemarker(email);
    }


    @Test
    public void testSS(){
        try {
            Email mail = new Email();
            mail.setEmail(new String[]{"zhangjinmiao@xxx.com"});
            mail.setSubject("你个小逗比");
            mail.setContent("科帮网欢迎您");
            mail.setTemplate("welcome");
            for (int i = 0; i < 10; i++) {
                service.sendQueue(mail);
//                service.sendRedisQueue(mail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
