
import java.io.UnsupportedEncodingException;
import org.dom4j.*;


public class openapiMainApplication {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //此为opencv的opencv_java453.dll
        //位置在opencv安装目录下的build\\java\\x64\\位置
        String dllAbsPath = "C:\\Users\\86137\\Desktop\\BJTU\\小学期二\\opencv\\build\\java\\x64\\opencv_java460.dll";
        //位置在opencv安装目录下的sources\\data\\haarcascades\\位置
        String facexmlAbsPath = "C:\\Users\\86137\\Desktop\\BJTU\\haarcascade_frontalface_alt.xml";
        //必须加载
        String eyexmlAbsPath="0";
        InitInstance.init(dllAbsPath, facexmlAbsPath,eyexmlAbsPath);
               InitInstance.videoDetectorModel();
//              System.out.println(InitInstance.compare_image("C:\\Users\\86137\\Desktop\\test.jpg", "C:\\Users\\86137\\Desktop\\test.jpg"));
    }
}
