package bjtu.monitor.config;

import java.io.UnsupportedEncodingException;

import static bjtu.monitor.utils.Global.*;
//import org.dom4j.*;


public class openapiMainApplication {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //此为opencv的opencv_java453.dll
        //位置在opencv安装目录下的build\\java\\x64\\位置
//        String dllAbsPath = "D:\\Coding\\java\\git\\video-detection-system\\Monitor\\src\\main\\resources\\lib.opencv\\opencv_java460.dll";
//        //位置在opencv安装目录下的sources\\data\\haarcascades\\位置
//        String facexmlAbsPath = "D:\\java\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
        //必须加载
//        String eyexmlAbsPath="0";
        InitInstance.init(dllAbsPath, facexmlAbsPath,ffmpegPath);
        InitInstance.videoDetectorModel();

    }
}
