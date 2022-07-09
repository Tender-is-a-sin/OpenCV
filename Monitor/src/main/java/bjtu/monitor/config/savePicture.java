package bjtu.monitor.config;
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
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String savedate=year+"-"+month+"-"+date+"-"+hour+"-"+minute+".png";
        return savedate;
    }

}