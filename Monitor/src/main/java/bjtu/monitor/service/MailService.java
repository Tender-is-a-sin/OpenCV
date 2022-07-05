package bjtu.monitor.service;

public interface MailService {
    //发送邮件
    boolean sendMail(String email, String text);
}
