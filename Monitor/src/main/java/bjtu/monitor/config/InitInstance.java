package bjtu.monitor.config;
import java.io.*;
import javafx.scene.image.Image;
import org.opencv.videoio.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.*;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.*;
import java.util.Arrays;
public class InitInstance {

    private static Logger logger = LoggerFactory.getLogger(InitInstance.class);
    //脸部识别实例
    private static CascadeClassifier faceDetector;


    //此类加载人脸识别模块
    public static void init(String dllAbsPath, String facexmlAbsPath, String eyexmlAbsPath) {
        logger.info("开始读取脸部识别实例");
        //加载dll文件
        System.load(dllAbsPath);
        faceDetector = new CascadeClassifier(facexmlAbsPath);
        if (faceDetector.empty()) {
            logger.error("人脸识别模块读取失败");
        } else logger.info("人脸识别模块读取成功");
    }


    //此类实现打开视频，识别人脸
    public static void videoDetectorModel() {
        savePicture savePicture=new savePicture();                
        //打开摄像头
        VideoCapture videoCapture = new VideoCapture(0);
        //判断摄像头是否打开
        if (!videoCapture.open(0)) {
            logger.error("相机打开失败");
            return;
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
                Imgproc.rectangle(img, new Point(re.x, re.y), new Point(re.x + re.width, re.y + re.height), new Scalar(0, 255, 0), 2);
                imgcompare = new Mat(gray, new Rect(re.x, re.y, re.width, re.height));

                //显示在屏幕
                facecheck face = new facecheck();
                boolean flag = face.facecheck(imgcompare);
                if (flag)
                    Imgproc.putText(img, "admin", new Point(re.x, re.y), 2, 1, new Scalar(0, 255, 0));
                else {
                    Imgproc.rectangle(img, new Point(re.x, re.y), new Point(re.x + re.width, re.y + re.height), new Scalar(0, 0, 255), 2);
                    Imgproc.putText(img, "warn", new Point(re.x, re.y), 2, 1, new Scalar(0, 0, 255));
                    savePicture.savePicture(img);
                }
            }
            HighGui.imshow("人脸识别", img);
            //按'q'退出
            if (HighGui.waitKey(1) == 81) break;
        }
        //释放资源
        videoCapture.release();
        HighGui.destroyAllWindows();
    }

}