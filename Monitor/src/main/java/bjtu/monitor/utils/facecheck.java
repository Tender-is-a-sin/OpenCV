package bjtu.monitor.utils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.util.Arrays;
import static bjtu.monitor.utils.Global.*;
import static bjtu.monitor.utils.Global.facexmlAbsPath;


public class facecheck {
    private  boolean  flag=false;
    private static int facenumber=3;


    private void faceplus(){
        facenumber++;
    }

    private void faceminus(){ facenumber--; }

    public  boolean facecheck(Mat imgcompare){
        for(int i=0;i<facenumber;i++)
        {
            double res=compare_image(imgcompare,adminface+i+".png");
            if(res>0.7){
                flag=true;
                return flag;
            }
        }

        return flag;
    }

    public static Mat conv_Mat(String img) {
        //读取图片Mat
        Mat imgInfo = Imgcodecs.imread(img);
        //此处调用了实体方法，实现灰度转化
        CvtMatEntity cvtMatEntity = CvtMatEntity.cvtR2G(imgInfo);
        //创建Mat矩形
        MatOfRect faceMat = new MatOfRect();
        //识别人人脸
        CascadeClassifier faceDetector = new CascadeClassifier(facexmlAbsPath);
        faceDetector.detectMultiScale(cvtMatEntity.gray, faceMat);
        for (Rect rect : faceMat.toArray()) {
            //选出灰度人脸
            Mat face = new Mat(cvtMatEntity.gray, rect);
            return face;
        }
        return null;
    }

    public static double compare_image(Mat mat_1, String img_2) {
        //获得灰度人脸
//      Mat mat_1 = conv_Mat(img_1);
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
