package com.example.javaweb2.demo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Email {

    @Autowired
    JavaMailSender javaMailSender;

    public String send(String to, String from, String title, String context){
        // 建立邮件信息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 设置接收方
        mailMessage.setTo(to);
        // 设置发送方
        mailMessage.setFrom(from);
        // 设置标题
        mailMessage.setSubject(title);
        // 设置正文
        mailMessage.setText(context);
        javaMailSender.send(mailMessage);
        return "success";
    }
}
