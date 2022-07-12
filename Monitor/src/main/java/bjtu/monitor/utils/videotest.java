package bjtu.monitor.utils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.*;//导入方法依赖的package包/类

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayInputStream;

import static org.opencv.imgcodecs.Imgcodecs.imencode;

/**
 * Display image in a frame
 *
 * @param title
 * @param img
 */
public class videotest {


    public static void imshow(Mat img) {

        String title ="test";
        // Convert image Mat to a jpeg
        MatOfByte imageBytes = new MatOfByte();
        imencode(".jpg", img, imageBytes);

        try {
            // Put the jpeg bytes into a JFrame window and show.
            JFrame frame = new JFrame(title);
            frame.getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())))));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setLocation(30 , 30 );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}