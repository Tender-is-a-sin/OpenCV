package bjtu.monitor.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opencv.core.Mat;

import java.io.IOException;

public interface PictureService {
    void getPicture(HttpServletRequest request, HttpServletResponse response,String p) throws IOException;
    String toBase64(String filePath);

}
