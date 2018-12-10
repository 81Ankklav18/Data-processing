package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Cardio extends Line implements Initializable {
    private int M;
    private XYChart.Series<Double, Double> series1;

    public Cardio() {
        super(1000);
        M = 200;
    }

    @FXML
    LineChart<Double, Double> chart1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Double, Double> getSeries() {
        /*TODO: create var's instead of const's*/
        double f = 7, a = 25, dt = 0.005d;
        series1 = new XYChart.Series<>();

        for (int i = 0; i < M; i++) {
            series1.getData().add(new XYChart.Data<>((double) i, Math.sin(2 * Math.PI * f * dt * i) * Math.exp(-a * dt * i)));
        }

        return series1;
    }

    XYChart.Series<Double, Double> Peek(XYChart.Series<Double, Double> series1) {
        XYChart.Series<Double, Double> series2 = new XYChart.Series<Double, Double>();
        /*TODO: create var's instead of const's*/
        double f = 7, a = 25, dt = 0.005d;


        for (int i = 0; i < M * 4; i++) {
            if (i % 200 == 0) {
                series2.getData().add(new XYChart.Data<>((double) i, 1d));
            } else {
                series2.getData().add(new XYChart.Data<>((double) i, 0d));
            }

        }

        return series2;
    }

    XYChart.Series<Double, Double> Convalution(XYChart.Series<Double, Double> peek, XYChart.Series<Double, Double> h) {
        XYChart.Series<Double, Double> series3 = new XYChart.Series<Double, Double>();
        /*TODO: create var's instead of const's*/
        double resault = 0;
        int N = peek.getData().size();
        int M = h.getData().size();

        for (int i = 0; i < N + M - 1; i++) {
            for (int j = 0; j < M; j++) {
                if (i - j > 0 && i - j < N) {
                    resault += peek.getData().get(i - j).getYValue() * h.getData().get(j).getYValue();
                }
            }
            series3.getData().add(new XYChart.Data<>((double) i, resault));
            resault = 0;
        }
        return series3;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Cardio.fxml"));
        stageLF.setTitle("Cardio Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) {
        printLine(chart1, getSeries());
    }

    public void newConvaluation(ActionEvent actionEvent) {
        printLine(chart1, Convalution(Peek(getSeries()), getSeries()));
    }

    public void newPeekIt(ActionEvent actionEvent) {
        printLine(chart1, Peek(getSeries()));

    }
}
