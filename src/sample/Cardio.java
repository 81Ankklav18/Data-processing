package sample;

import javafx.fxml.Initializable;
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

public class Cardio extends Line implements Initializable {
    private int M;
    private XYChart.Series<Double, Double> series1;

    public Cardio()
    {
        super(1000);
        M = 200;
    }

    @FXML
    LineChart<Double, Double> chart1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Double, Double> getSeries() throws IOException {
        /*TODO: create var's instead of const's*/
        double f = 7, a = 25, dt = 0.005d;
        series1 = new XYChart.Series<>();
        for (int k = 0; k < N / M; k++) {
            for (int i = 0; i < M; i++) {
                series1.getData().add(new XYChart.Data<>((double) i+(k*M), Math.sin(2 * Math.PI * f * dt * i) * Math.exp(-a * dt * i)));
            }
        }
        return series1;
    }

    XYChart.Series<Double, Double> Convalutuion(XYChart.Series<Double, Double> series1){
        XYChart.Series<Double, Double> series2 = new XYChart.Series<Double, Double>();
        /*TODO: create var's instead of const's*/
        double f = 7, a = 25, dt = 0.005d;
        double resault = 0;

        for (int i = 0; i < M+N-1; i++){
            for (int j = 0; j < M-1; j++){
                if (i-j > 0 || j < 1000){
                    resault += Math.sin(2*Math.PI*f*dt*i-j)*Math.exp(-a*dt*j);
                }
            }
            series2.getData().add(new XYChart.Data<>((double) i, resault));
            resault = 0;
        }

        return series2;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Cardio.fxml"));
        stageLF.setTitle("Cardio Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {
        printLine(chart1, getSeries(), Convalutuion((getSeries())));
    }
}
