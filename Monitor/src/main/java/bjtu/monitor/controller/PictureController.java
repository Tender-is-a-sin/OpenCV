package bjtu.monitor.controller;

import bjtu.monitor.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @RequestMapping("/requestPicture")
    @ResponseBody
    //调用图片
    public void getBase64(HttpServletRequest request, HttpServletResponse response) {
        //调用doGet方法会抛出异常,捕获一下
        try {
            for(int i=0;i<3;i++){
                String path  =  "C:\\Users\\FUBOFENG\\Desktop\\";
                path.concat(""+i+".jpg");
                pictureService.getPicture(request,response,path);
            }
//            pictureService.getPicture(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


     @GetMapping("/getPicture")
    @ResponseBody
    public String[] getPicture(String date) {
        //  static\\intrusion填绝对路径
        String paths = "F:\\video-detection-system-master (2)\\video-detection-system-master\\Monitor\\src\\main\\resources\\static\\intrusion";
        String result[]=new String[24];
        // 获得指定文件对象
        File file = new File(paths);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();

        int j=0;
        for(int i=0;i<array.length;i++)
        {
            if(array[i].getName().contains(date.toString())){
                result[j]="/intrusion/"+array[j].getName();
                System.out.println(result[j]);
                j++;
            }
        }
        return result;
    }
//    public void getBase64(HttpServletRequest request, HttpServletResponse response) {
//        //调用doGet方法会抛出异常,捕获一下
//        try {
//            String path  =  "C:\\Users\\FUBOFENG\\Desktop\\0.jpg";
//            pictureService.getPicture(request,response,path);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String path1 =  "C:\\Users\\FUBOFENG\\Desktop\\1.jpg";
//            pictureService.getPicture(request,response,path);
////            pictureService.getPicture(request,response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
