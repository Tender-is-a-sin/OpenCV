package bjtu.monitor.controller;

import bjtu.monitor.config.CaptureBasic;
import bjtu.monitor.config.InitInstance;
import bjtu.monitor.config.facecheck;
import bjtu.monitor.config.savePicture;
import bjtu.monitor.service.PictureService;
import bjtu.monitor.utils.SseEmitterServer;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;

import static bjtu.monitor.utils.Global.*;


@RestController
@RequestMapping("/sse")
public class SseEmitterController {

    private static Logger logger = LoggerFactory.getLogger(InitInstance.class);
    private static CascadeClassifier faceDetector;
    @Autowired
    PictureService pictureService;

    /**
     * 用于创建连接
     */
//    @GetMapping("/connect/{userId}")
//    public SseEmitter connect(@PathVariable String userId) {
//        SseEmitter  s = SseEmitterServer.connect(userId);
////        return SseEmitterServer.connect(userId);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0;i<100;i++){
//                    int j = i%6;
//                    String in = "C:\\Users\\FUBOFENG\\Desktop\\"+j+".jpg";
//                    SseEmitterServer.batchSendMessage(pictureService.toBase64(in));
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
////        SseEmitterServer.batchSendMessage(pictureService.toBase64("C:\\Users\\FUBOFENG\\Desktop\\0.jpg"));
//
//        return s;
//    }
    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        for (int i = 0; i < 10; i++) {
            SseEmitterServer.batchSendMessage(message + i);
        }
//        SseEmitterServer.batchSendMessage(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }
//    static {
//        URL url = ClassLoader.getSystemResource("lib\\opencv\\opencv_java460.dll");
//        System.load(url.getPath());
//    }

    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        SseEmitter s = SseEmitterServer.connect(userId);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.load(dllAbsPath);
        faceDetector = new CascadeClassifier(facexmlAbsPath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                savePicture savePicture = new savePicture();
                //打开摄像头
                VideoCapture videoCapture = new VideoCapture(0);
                //判断摄像头是否打开
                if (!videoCapture.open(0)) {
                    logger.error("相机打开失败");
                }
                while (true) {
                    //创建图片Mat
                    Mat img = new Mat();
                    //读取摄像头下的图像
                    if (!videoCapture.read(img)) return;
                    //为保证教程详细度，此处不调用实体方法，大家可自行选择
                    //图片灰度转化
                    Mat rgb = new Mat();
                    Imgproc.cvtColor(img, rgb, Imgproc.COLOR_BGR2RGB);
                    Mat gray = new Mat();
                    Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);
                    //创建人脸识别出的矩形变量
                    Mat imgcompare = new Mat();
                    MatOfRect faveRect = new MatOfRect();
                    //检测人脸
                    faceDetector.detectMultiScale(gray, faveRect);
                    //图形面勾选人脸
                    for (Rect re : faveRect.toArray()) {
                        Imgproc.rectangle(img, new org.opencv.core.Point(re.x, re.y), new org.opencv.core.Point(re.x + re.width, re.y + re.height), new Scalar(0, 255, 0), 2);
                        imgcompare = new Mat(gray, new Rect(re.x, re.y, re.width, re.height));

                        //显示在屏幕
                        facecheck face = new facecheck();
                        boolean flag = face.facecheck(imgcompare);
                        if (flag)
                            Imgproc.putText(img, "admin", new org.opencv.core.Point(re.x, re.y), 2, 1, new Scalar(0, 255, 0));
                        else {
                            Imgproc.rectangle(img, new org.opencv.core.Point(re.x, re.y), new org.opencv.core.Point(re.x + re.width, re.y + re.height), new Scalar(0, 0, 255), 2);
                            Imgproc.putText(img, "warn", new Point(re.x, re.y), 2, 1, new Scalar(0, 0, 255));
                            savePicture.savePicture(img);
                        }
                    }
                    String jpg_base64 = null;
                    Imgcodecs.imwrite(savepath + "tmp.jpg", img);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        ImageIO.write(new CaptureBasic().mat2BI(img), "jpg", baos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] bytes = baos.toByteArray();
//                    BASE64Encoder encoder = new BASE64Encoder();
//                    jpg_base64 = encoder.encodeBuffer(Objects.requireNonNull(bytes));
                    Base64.Encoder encoder = Base64.getEncoder();
                    jpg_base64 = encoder.encodeToString(Objects.requireNonNull(bytes));
                    SseEmitterServer.sendMessage(userId, jpg_base64);
                    //按'q'退出
                    if (HighGui.waitKey(1) == 81) break;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                videoCapture.release();
                HighGui.destroyAllWindows();
            }
        }).start();


        return s;
    }

}