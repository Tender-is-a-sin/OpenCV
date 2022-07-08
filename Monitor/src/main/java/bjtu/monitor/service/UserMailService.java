package bjtu.monitor.service;


import org.springframework.stereotype.Service;

@Service
public interface UserMailService{
    void sendSimpleMail(String to, String subject, String content);
}
