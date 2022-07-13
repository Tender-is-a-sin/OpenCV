package bjtu.monitor.utils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static bjtu.monitor.utils.Global.savepath;

public class savePicture {
    public void savePicture(Mat img){

        String time=getTime();
        Imgcodecs.imwrite(savepath+time,img);

    }

    public static String getTime(){
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
         String month1 = "";
        String date1 ="";
        if(month<10){
            month1="0"+month;
        }else {
            month1=""+month;
        }
        if(date<10){
            date1="0"+month;
        }else {
            date1=""+month;
        }
        String savedate=year+"-"+month1+"-"+date1+"-"+hour+"-"+minute+".png";
        return savedate;
    }

}