package bjtu.monitor.controller;

import bjtu.monitor.utils.InitInstance;
import bjtu.monitor.utils.SseEmitterServer;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;




@RestController
@RequestMapping("/sse")
public class SseEmitterController {

    private static Logger logger = LoggerFactory.getLogger(InitInstance.class);
    //脸部识别实例
    private static CascadeClassifier faceDetector;

    InitInstance initInstance;

    Thread t;
    
    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        for (int i = 0; i < 10; i++) {
            SseEmitterServer.batchSendMessage(message + i);
        }
//        SseEmitterServer.batchSendMessage(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }

    @GetMapping("/close/{userId}")
    public void close(@PathVariable String userId){
        SseEmitterServer.removeUser(userId);
        initInstance.closeVideo();
        t.isInterrupted();

    }
        @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        SseEmitter  s = SseEmitterServer.connect(userId);
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                initInstance = new InitInstance();
                if(initInstance.openVideo()){
                    while (true){
                        Mat img = initInstance.getMatfromVideo();
                        String tem = initInstance.matToBase64(img);
                        SseEmitterServer.sendMessage(userId,tem);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t.start();
        return s;
    }
}