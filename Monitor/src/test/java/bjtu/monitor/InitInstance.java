
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
            Mat imgcompare=new Mat();
            MatOfRect faveRect = new MatOfRect();
            //检测人脸
            faceDetector.detectMultiScale(gray, faveRect);
            //图形面勾选人脸
            for (Rect re : faveRect.toArray()) {
                    Imgproc.rectangle(img, new Point(re.x, re.y), new Point(re.x + re.width, re.y + re.height), new Scalar(0, 0, 255), 2);
                imgcompare=new Mat(gray,new Rect(re.x,re.y,re.width,re.height));

            //显示在屏幕
            double res=compare_image(imgcompare,"C:\\Users\\86137\\Desktop\\test.png");
                String res1=new String(""+res);
                Imgproc.putText(img,res1,new Point(re.x,re.y),2,1,new Scalar(0, 0, 255));
            }
            HighGui.imshow("人脸识别", img);
//            System.out.println(compare_image(imgcompare,"C:\\Users\\86137\\Desktop\\test.png"));


            //按'q'退出
            if (HighGui.waitKey(1) == 81) break;
        }
        //释放资源
        videoCapture.release();
        HighGui.destroyAllWindows();
    }


    //以下内容为对比人脸模块。与打开视频，识别人脸完全分离
    /**
     * 获取灰度人脸
     */
    public static Mat conv_Mat(String img) {
        //读取图片Mat
        Mat imgInfo = Imgcodecs.imread(img);
        //此处调用了实体方法，实现灰度转化
        CvtMatEntity cvtMatEntity = CvtMatEntity.cvtR2G(imgInfo);
        //创建Mat矩形
        MatOfRect faceMat = new MatOfRect();
        //识别人人脸
        faceDetector.detectMultiScale(cvtMatEntity.gray, faceMat);
        for (Rect rect : faceMat.toArray()) {
            //选出灰度人脸
            Mat face = new Mat(cvtMatEntity.gray, rect);
            return face;
        }
        return null;
    }

    /**
     * 图片对比人脸
     */
    public static double compare_image(Mat mat_1, String img_2) {
        //获得灰度人脸
//        Mat mat_1 = conv_Mat(img_1);
            Mat mat_2 = conv_Mat(img_2);
        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();
        //参数定义
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histSize = new MatOfInt(10000000);
        //实现图片计算
        Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);
        // 相关系数，获得相似度
        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        //返回相似度
        return res;
    }
}
