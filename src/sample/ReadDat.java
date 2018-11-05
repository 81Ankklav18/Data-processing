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

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

    XYChart.Series<Float, Float> getSeries() throws IOException {
        series1 = new XYChart.Series<Float, Float>();
//  read the file into a byte array
        File file = new File(new File(getCacheDirectory()), "pgp_1ms.dat");
        FileInputStream fis = new FileInputStream(file);
        byte [] arr = new byte[(int)file.length()];
        fis.read(arr);

//  create a byte buffer and wrap the array
        ByteBuffer bb = ByteBuffer.wrap(arr);

        for (int i = 0; i < (int)file.length()/8; i++) {
            series1.getData().add(new XYChart.Data<>(Float.valueOf(i), bb.getFloat()));
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