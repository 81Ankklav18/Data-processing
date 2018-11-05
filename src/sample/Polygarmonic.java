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
import java.util.ResourceBundle;

public class Polygarmonic extends Line implements Initializable {
    private int A;
    private int f;
    private double t;

    private XYChart.Series<Double, Double> series1;

    public Polygarmonic(int N, int A, int f, double t)
    {
        super(N);
        this.A = A;
        this.f = f;
        this.t = t;
    }

    public Polygarmonic() {
        super(0);
        this.A = 0;
        this.f = 0;
        this.t = 0.d;
    }

    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    TextField tfA;
    @FXML
    TextField tff;
    @FXML
    TextField tft;
    @FXML
    TextField tfN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    public XYChart.Series<Double, Double> getSeries()
    {
        series1 = new XYChart.Series<>();
        for (int i = 0; i < N; i++)
        {
            series1.getData().add(new XYChart.Data<>((double) i, A * Math.sin(2 * Math.PI * f * i * t)));
        }

        return series1;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Polygarmonic.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    void setValue(int A, int f, double t, int N)
    {
        this.A = A;
        this.f = f;
        this.t = t;
        super.N = N;
    }

    public void newBuildIt(ActionEvent actionEvent) {
        setValue(Integer.parseInt(tfA.getText()), Integer.parseInt(tff.getText()), Double.parseDouble(tft.getText()), Integer.parseInt(tfN.getText()));

        printLine(chart1, getSeries());
    }

    public XYChart.Series<Double, Double> addSeries(XYChart.Series<Double, Double> serial1)
    {
        setValue(Integer.parseInt(tfA.getText()), Integer.parseInt(tff.getText()), Double.parseDouble(tft.getText()), Integer.parseInt(tfN.getText()));
        XYChart.Series<Double, Double> series2;
        series2 = new XYChart.Series<>();
        for (int i = 0; i < N; i++)
        {
            series2.getData().add(new XYChart.Data<>((double) i,
                    series1.getData().get(i).getYValue() + A * Math.sin(2 * Math.PI * f * i * t)));
        }

        return series2;
    }

    public void newAdd(ActionEvent actionEvent) {

        printLine(chart1, addSeries(getSeries()));
        //setValue(Integer.parseInt(tfA.getText()), Integer.parseInt(tff.getText()), Double.parseDouble(tft.getText()), Integer.parseInt(tfN.getText()));


    }
}
