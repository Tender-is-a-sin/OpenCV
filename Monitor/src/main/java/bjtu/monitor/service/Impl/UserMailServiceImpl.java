package bjtu.monitor.service.Impl;
import bjtu.monitor.pojo.table.User;
import bjtu.monitor.pojo.table.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import bjtu.monitor.service.UserMailService;

import static bjtu.monitor.utils.Global.FAIL;
import static bjtu.monitor.utils.Global.SUCCESS;

@Service
public class UserMailServiceImpl implements UserMailService{
    @Autowired
    private JavaMailSender mailSender;//注入发送邮件的bean

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom("1245325661@qq.com");
        //收件人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

}
