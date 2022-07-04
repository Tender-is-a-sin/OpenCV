package bjtu.monitor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.opencv.core.Mat;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.opencv.core.Core.merge;
import static org.opencv.core.Core.split;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.equalizeHist;
@SpringBootTest
class MonitorApplicationTests {

    @Test
    void contextLoads() {
        System.setProperty("java.awt.headless", "false");
//        System.out.println(System.getProperty("java.library.path"));
        URL url = ClassLoader.getSystemResource("lib.opencv/opencv_java460.dll");
        System.load(url.getPath());
        //填你的图片地址
        Mat image = imread("C:\\Users\\FUBOFENG\\Desktop\\image.jpg", 1);
        if (image.empty()){
            try {
                throw new Exception("image is empty!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        imshow("Original Image", image);
        List<Mat> imageRGB = new ArrayList<>();
        split(image, imageRGB);
        for (int i = 0; i < 3; i++) {
            equalizeHist(imageRGB.get(i), imageRGB.get(i));
        }
        merge(imageRGB, image);
        imshow("Processed Image", image);
        waitKey();
    }

}
