package bjtu.monitor.utils;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.*;
import org.opencv.highgui.*;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import static bjtu.monitor.utils.Global.*;
import static org.opencv.imgproc.Imgproc.INTER_LINEAR;
import static org.opencv.videoio.Videoio.CAP_FFMPEG;

public class InitInstance {

    private static Logger logger = LoggerFactory.getLogger(InitInstance.class);
    //脸部识别实例
    private static CascadeClassifier faceDetector;
    savePicture savePicture;
    //打开摄像头
    VideoCapture videoCapture;

    //此类加载人脸识别模块
    public static void init(String dllAbsPath, String facexmlAbsPath, String ffmpegxmlAbsPath) {
        logger.info("开始读取脸部识别实例");
        //加载dll文件
        System.load(dllAbsPath);
        faceDetector = new CascadeClassifier(facexmlAbsPath);
        if (faceDetector.empty()) {
            logger.error("人脸识别模块读取失败");
        } else logger.info("人脸识别模块读取成功");
    }
    public  InitInstance() {
        logger.info("开始读取脸部识别实例");
        //加载dll文件
        System.load(dllAbsPath);
        System.load(ffmpegxmlAbsPath);
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
                boolean flag = face.faceCheck(imgcompare);
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
    public boolean openVideo(){
        String url="rtmp://124.70.0.166:1935/rtmplive";
        savePicture=new savePicture();
        //打开摄像头
        videoCapture = new VideoCapture(0);
        //拉流
        //videoCapture = new VideoCapture(url,CAP_FFMPEG);

        //判断摄像头是否打开
        if (!videoCapture.isOpened()) {
            logger.error("相机打开或拉流失败");
            return false;
        }
        return true;
    }

    public void closeVideo(){
        videoCapture.release();
    }
    public Mat getMatfromVideo(){
        Mat img = new Mat();
        //读取摄像头下的图像
        if (!videoCapture.read(img)) return null;
        return img;
    }
    public String matToBase64(Mat img){
        if(img==null){
            return null;
        }
        //图像缩放比例
        double scale =4.0;
        double fx=1/scale;

        //初始化白名单人脸库，第一个参数为图片数量，第二个参数为图片存放地址
        facecheck  face=new facecheck();
        face.initFaceCheck(1,adminface);

        Mat rgb = new Mat();
        Imgproc.cvtColor(img, rgb, Imgproc.COLOR_BGR2RGB);
        Mat gray = new Mat();
        Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);

        //图片缩放，减少计算量
        Mat smallImg=new Mat();
        Imgproc.resize(gray,smallImg,smallImg.size(),fx,fx,INTER_LINEAR);
        //释放无用mat
        gray.release();

        //图片直方图均衡化
        Imgproc.equalizeHist(smallImg,smallImg);



        MatOfRect faveRect = new MatOfRect();
        //检测人脸
        faceDetector.detectMultiScale(gray, faveRect);
        //图形面勾选人脸
        for (Rect re : faveRect.toArray()) {

            double lx=re.x*scale;
            double ly=re.y*scale;
            double lwidth=(re.x + re.width)*scale;
            double lheigeht=(re.y + re.height)*scale;

            //框出人脸
            Imgproc.rectangle(img, new Point(lx, ly), new Point(lwidth, lheigeht), new Scalar(0, 255, 0), 2);
            //创建人脸识别出的矩形变量
            Mat imgcompare = new Mat(smallImg, new Rect(re.x, re.y, re.width, re.height));

            //在白名单库中对比
            boolean flag = face.faceCheck(imgcompare);
            if (flag)
                Imgproc.putText(img, "admin", new Point(lx, ly), 2, 1, new Scalar(0, 255, 0));
            else {
                Imgproc.rectangle(img, new Point(lx, ly), new Point(lwidth, lheigeht), new Scalar(0, 0, 255), 2);
                Imgproc.putText(img, "warn", new Point(lx, ly), 2, 1, new Scalar(0, 0, 255));
                savePicture.savePicture(img);
            }
        }
        String jpg_base64 = null;
        Imgcodecs.imwrite(savepath + "tmp.jpg", img);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(matToBase64.mat2BI(img), "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
//                    BASE64Encoder encoder = new BASE64Encoder();
//                    jpg_base64 = encoder.encodeBuffer(Objects.requireNonNull(bytes));
        Base64.Encoder encoder = Base64.getEncoder();
        jpg_base64 = encoder.encodeToString(Objects.requireNonNull(bytes));
        return jpg_base64;
    }

}