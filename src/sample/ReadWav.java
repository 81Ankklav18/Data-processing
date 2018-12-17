package sample;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;

import static com.sun.deploy.config.Config.getCacheDirectory;

public class ReadWav extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Float, Byte> series1;

    public ReadWav()
    {
        super(11025);
    }

    @FXML
    LineChart<Float, Byte> chart1;
    @FXML
    TextField tfN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Float, Byte> getSeries() throws IOException {
        series1 = new XYChart.Series<>();
//  read the file into a byte array
        FileInputStream file = new FileInputStream( "src/Dron.wav");
        //FileInputStream fis = new FileInputStream(file);

        byte [] byteArray = new byte[50000];
        try(DataInputStream fileInStream = new DataInputStream(file))
        {
            //fileInStream.readFully(byteArray);
            file.read(byteArray);
        }
        catch(IOException ioe){
            System.out.print(ioe.getMessage());
        }

        //TODO: убрать константу
        float[] fl = new float[11025];

        double[] fld = new double[11025];

        try(FileChannel fc = new RandomAccessFile("src/betkhoven-lunnaja-sonata.wav", "rw").getChannel()) {
            DoubleBuffer fb = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size())
                    .order(ByteOrder.nativeOrder()).asDoubleBuffer();
            fb.get(fld);
        }

        for (int i = 0; i < byteArray.length; i++) {
            series1.getData().add(new XYChart.Data<>((float) i, byteArray[i]));
        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }

        return series1;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ReadWav.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {

        printLineFlB(chart1, getSeries());
    }

}
