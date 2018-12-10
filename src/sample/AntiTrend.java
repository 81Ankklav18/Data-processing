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
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AntiTrend extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Double, Double> series1;

    public AntiTrend() {
        super(1000);
    }

    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    TextField tfN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Double, Double> getSeries() throws IOException {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        double[] temp = new double[N];
        Random rand = new Random();

        series1 = new XYChart.Series<>();
        double val;
        for (int i = 0; i < N; i++) {
            val = rand.nextInt(100) + 1;
            temp[i] = val;
            if (val > max) {
                max = val;
            }
            if (val < min) {
                min = val;
            }
        }

        for (int i = 0; i < N; i++) {
            series1.getData().add(new XYChart.Data<>((double) i, temp[i] - 0.5d + 3 * i + 2));
        }

        return series1;
    }

    private XYChart.Series<Double, Double> AntiTrend(XYChart.Series<Double, Double> series1) throws IOException {
        double mid = 0;
        XYChart.Series<Double, Double> series2;
        series2 = new XYChart.Series<>();

        for (int i = 0; i < N; i++) {
            if (i > 6) {
                for (int j = i - 6; j < i + 6 && j < N - 13; j++) {
                    mid += series1.getData().get(j).getYValue();
                }
                if (i < 975) {
                    mid /= 12;
                    series2.getData().add(new XYChart.Data<>((double) i, mid));
                    mid = 0;
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            series2.getData().add(i, new XYChart.Data<>((double) i, series1.getData().get(i).getYValue()));
        }

        for (int i = 975; i < 1000; i++) {
            series2.getData().add(new XYChart.Data<>((double) i, series1.getData().get(i).getYValue()));
        }

        int a = 0;
        return series2;
    }


    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AntiTrend.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {

        printLine(chart1, getSeries());
    }

    public void newAntiTrendIt(ActionEvent actionEvent) throws IOException {
        printLine(chart1, AntiTrend(getSeries()));
    }

}
