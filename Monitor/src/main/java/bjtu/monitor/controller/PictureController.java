package bjtu.monitor.controller;

import bjtu.monitor.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static bjtu.monitor.utils.Global.adminface;
import static bjtu.monitor.utils.Global.savepath;

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
    @RequestMapping("/select/{userid}")
    @ResponseBody
    //调用图片
    public void getBase64(HttpServletRequest request, HttpServletResponse response,@PathVariable int userid) {
        //调用doGet方法会抛出异常,捕获一下
    }
    @GetMapping("/getPicture")
    @ResponseBody
    public String[] getPicture(String date) {
        //  static\\intrusion填绝对路径
        String paths = "C:\\Users\\FUBOFENG\\IdeaProjects\\Monitor\\src\\main\\resources\\static\\intrusion";
        String result[]=new String[24];
        // 获得指定文件对象
        File file = new File(paths);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();

        int j=0;
        for(int i=0;i<array.length;i++)
        {
            if(array[i].getName().contains(date.toString())){
//                result[j]="/intrusion/"+array[j].getName();
                result[j] = "data:image/png;base64," +pictureService.toBase64(array[i].getPath());
                j++;
            }
        }
        return result;
    }
    @GetMapping("/recordPicture")
    @ResponseBody
    public String modifyPicture(String name) {
        //  static\\intrusion填绝对路径
        String paths = savepath;
        String result="new String()";
        File file = new File(paths);
//        System.out.println("warning"+name);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        for(int i=0;i<array.length;i++)
        {
            if(array[i].getName().compareTo(name)==0){
                result= "data:image/png;base64," +pictureService.toBase64(array[i].getPath());
//                System.out.println(array[i].getName());
            }
        }
        return result;
    }

    @GetMapping("/whitePicture")
    @ResponseBody
    public String whitePicture(String name) {
        //  static\\intrusion填绝对路径
        String paths = adminface;
        String result="new String()";
        File file = new File(paths);
//        System.out.println("warning"+name);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        for(int i=0;i<array.length;i++)
        {
            if(array[i].getName().compareTo(name)==0){
                result= "data:image/png;base64," +pictureService.toBase64(array[i].getPath());
                System.out.println(array[i].getName());
            }
        }
        return result;
    }
}
