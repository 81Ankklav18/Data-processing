package sample;

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
import sun.jvm.hotspot.jdi.ByteValueImpl;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.ResourceBundle;

import static com.sun.deploy.config.Config.getCacheDirectory;
import static java.lang.Math.round;

public class ReadDat extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Float, Float> series1;

    public ReadDat()
    {
        super(1000);
    }

    @FXML
    LineChart<Float, Float> chart1;
    @FXML
    TextField tfN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Float, Float> getSeries() throws IOException {
        series1 = new XYChart.Series<>();
//  read the file into a byte array
        RandomAccessFile file = new RandomAccessFile( getCacheDirectory() + "/pgp_1ms.dat", "r");
        //FileInputStream fis = new FileInputStream(file);

        float[] fl = new float[1000];

        try(FileChannel fc = new RandomAccessFile(getCacheDirectory() + "/pgp_1ms.dat", "rw").getChannel()) {
            FloatBuffer fb = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size())
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            fb.get(fl);
        }

        for (int i = 0; i < fl.length; i++) {
            series1.getData().add(new XYChart.Data<>((float) i, fl[i]));
        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }

        return series1;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ReadDat.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {

        printLineFl(chart1, getSeries());
    }
}