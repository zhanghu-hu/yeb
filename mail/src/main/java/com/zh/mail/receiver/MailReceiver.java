package com.zh.mail.receiver;

import com.zh.server.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 消息接收者
 * 决定用户收到的邮件信息
 *
 * @author ZH
 * @date 2021-01-29
 */
//@Component
public class MailReceiver {

    private static final Logger log = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = "mail.welcome")
    public void handler(Employee employee) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        //发件人
        helper.setFrom(mailProperties.getUsername());
        //收件人
        helper.setTo(employee.getEmail());
        //主题
        helper.setSubject("入职欢迎邮件");
        //发送日期
        helper.setSentDate(new Date());
        //邮件内容
        Context context=new Context();
        context.setVariable("name",employee.getName());
        context.setVariable("posName",employee.getPosition().getName());
        context.setVariable("joblevelName",employee.getJoblevel().getName());
        context.setVariable("departmentName",employee.getDepartment().getName());
        String mail=templateEngine.process("mail",context);
        helper.setText(mail,true);
        //发送邮件
        javaMailSender.send(msg);
    }
}
