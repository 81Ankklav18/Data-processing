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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;

import static com.sun.deploy.config.Config.getCacheDirectory;

public class Filtr extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Double, Double> series1;

    public Filtr()
    {
        super(1000);
    }

    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    TextField tfN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    public XYChart.Series<Double, Double> Furie(XYChart.Series<Double, Double> series1, int m) {
        N = 2 * m;
        double[] re = new double[N];
        double[] im = new double[N];

        XYChart.Series<Double, Double> series2;
        series2 = new XYChart.Series<>();
        float val;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re[i] += series1.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im[i] += series1.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
            re[i] /= N;
            im[i] /= N;
        }

        for (int i = 0; i < m; i++)
        {
            series2.getData().add(new XYChart.Data<>((double) i, N * (double)((Math.sqrt(Math.pow(re[i], 2) + Math.pow(im[i], 2))))));
        }
        return series2;
    }

    public XYChart.Series<Double, Double> ReversFurie(XYChart.Series<Double, Double> series1) {
        double[] re = new double[N];
        double[] im = new double[N];

        XYChart.Series<Double, Double> series2;
        series2 = new XYChart.Series<>();
        double val;

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re[i] += series1.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im[i] += series1.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
            re[i] /= N;
            im[i] /= N;
        }

        for (int i = 0; i < N; i++)
        {
            series2.getData().add(new XYChart.Data<>((double) i, re[i]+im[i]));
        }
        XYChart.Series<Double, Double> series3;
        series3 = new XYChart.Series<>();
        double[] re1 = new double[N];
        double[] im1 = new double[N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re1[i] += series2.getData().get(j).getYValue() * Math.cos((2 * Math.PI * i * j) / N);
                im1[i] += series2.getData().get(j).getYValue() * Math.sin((2 * Math.PI * i * j) / N);
            }
        }

        for (int i = 0; i < N; i++)
        {
            series3.getData().add(new XYChart.Data<>((double) i, (double)re1[i] + im1[i]));
        }
        return series3;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Filtr.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public XYChart.Series<Double, Double> getSeries(int fcut, double dt, int m){
        series1 = new XYChart.Series<>();
        double d[] = {0.35_57_70_19d, 0.24_36_98_30d, 0.07_21_14_97d, 0.00_63_01_65d};
        double lpw[] = new double[2*m+1];

        //прямоугольная
        double arg = 2 * fcut * dt;
        lpw[0] = arg;
        arg *= Math.PI;

        for (int i = 1; i <= m; i++){
            lpw[i] = (Math.sin(arg * i)) / (Math.PI * i);
        }

        //трапеция
        lpw[m] /= 2;

        //окно Поттера p310
        double sumg = lpw[0];
        double sum;
        for (int i = 1; i <= m; i++){
            sum = d[0];
            arg = (Math.PI * i) / m;
            for (int k = 1; k <= 3; k++){
                sum += 2 * d[k] * Math.cos(arg * k);
            }
            lpw[i] *= sum;
            sumg += 2 * lpw[i];
        }

        for (int i = 0; i <= m; i++){
            lpw[i] /= sumg;
        }

        for (int i = -m; i <= m ; i++) {
            series1.getData().add(new XYChart.Data<>((double) i, lpw[Math.abs(i)]));
        }

        return series1;
    }

    XYChart.Series<Double, Double> Convalutuion(XYChart.Series<Double, Double> series1, int m){
        XYChart.Series<Double, Double> series2 = new XYChart.Series<Double, Double>();
        /*TODO: create var's instead of const's*/
        double f = 15, a = 25, dt = 0.002d;
        double resault = 0;
        N = m;
        int M = 32;

        for (int i = 0; i < M+N-1; i++){
            for (int j = 0; j < M-1; j++){
                if (i-j > 0 || j < m){
                    resault += Math.sin(2*Math.PI*f*dt*i-j)*Math.exp(-a*dt*j);
                }
            }
            series2.getData().add(new XYChart.Data<>((double) i, resault));
            resault = 0;
        }

        return series2;
    }
//TODO: create var's instead of const's and correct Convaluation
    public void newBuildIt(ActionEvent actionEvent) throws IOException {

        printLine(chart1, getSeries(15, 0.002d, 128));
    }

    public void newFIt(ActionEvent actionEvent) throws IOException {
        printLine(chart1, Furie(getSeries(15, 0.002d, 128), 128));
    }

    public void newRFIt(ActionEvent actionEvent) throws IOException {
        printLine(chart1, Convalutuion(getSeries(15, 0.002d, 128), 128));
    }
}
