package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LinearFunction extends Line implements Initializable {
    //TODO Переопределить оператор плюс в базовом классе для сложения двух лайнов. Переработать базовый класс (сделать поля х,у)
    private double k;
    private double b;

    private XYChart.Series<Double, Double> series1;

    public LinearFunction(double k, int N)

    {
        this(k, 0, N);
    }

    public LinearFunction(double k, double b, int N)
    {
        super(N);
        this.k = k;
        this.b = b;
    }
    public LinearFunction()
    {
        super(0);
        this.k = 0;
        this.b = 0;
    }

    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    TextField tfk;
    @FXML
    TextField tfb;
    @FXML
    TextField tfN;
    @FXML
    TextField tfShift;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    public XYChart.Series<Double, Double> getSeries()
    {
        series1 = new XYChart.Series<Double, Double>();
        for (int i = 0; i < N; i++)
        {
            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(k * i + b)));
        }

        return series1;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LinearFunction.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    void setValue(double k, double b, int N)
    {
        this.k = k;
        this.b = b;
        super.N = N;
    }

    public void newBuildIt(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()), Double.parseDouble(tfb.getText()), Integer.parseInt(tfN.getText()));

        printLine(chart1, getSeries());
    }

    public void newShift(ActionEvent actionEvent) {
        chart1.getData().clear();

        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );

        printLine(chart1, getSeries(), shift(getSeries(), Integer.parseInt(tfShift.getText())));
    }

    void sum(){
        LinearFunction lf = new LinearFunction(4, 3, 1000);
        JavaRandom jr = new JavaRandom(3, 4, 1000);
        Line.operationSum(lf.getSeries(), jr.getSeries());
    }
}
