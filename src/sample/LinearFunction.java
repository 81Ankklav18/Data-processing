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
    @FXML
    TextField tfCountOfPoints;
    @FXML
    TextField tfSpikeDeep;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    public XYChart.Series<Double, Double> getSeries()
    {
        series1 = new XYChart.Series<>();
        for (int i = 0; i < N; i++)
        {
            series1.getData().add(new XYChart.Data<>((double) i, k * i + b));
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

    public XYChart.Series<Double, Double> Furie(XYChart.Series<Double, Double> series1) {
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

        for (int i = 0; i < N ; i++)
        {
            series2.getData().add(new XYChart.Data<>((double) i, Math.sqrt(Math.pow(re[i], 2) + Math.pow(im[i], 2))));
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
        }

        for (int i = 0; i < N; i++)
        {
            series2.getData().add(new XYChart.Data<>((double) i, re[i] + im[i]));
        }
        return series2;
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

    public void newSpike(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );

        printLine(chart1, getSeries(), spike(getSeries(), Integer.parseInt(tfCountOfPoints.getText()), Integer.parseInt(tfSpikeDeep.getText())));
    }

    public void newFShift(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );
        printLine(chart1, Furie(shift(getSeries(), Integer.parseInt(tfShift.getText()))));
    }

    public void newFSpike(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );
        printLine(chart1, Furie(spike(getSeries(), Integer.parseInt(tfCountOfPoints.getText()), Integer.parseInt(tfSpikeDeep.getText()))));
    }

    public void newRFSpike(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );
        printLine(chart1, ReversFurie(Furie(spike(getSeries(), Integer.parseInt(tfCountOfPoints.getText()), Integer.parseInt(tfSpikeDeep.getText())))));
    }

    public void newRFShift(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );
        printLine(chart1, ReversFurie((shift(getSeries(), Integer.parseInt(tfShift.getText())))));
    }

    public void newASpike(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );

        printLine(chart1, getSeries(),
                spike(getSeries(), Integer.parseInt(tfCountOfPoints.getText()), Integer.parseInt(tfSpikeDeep.getText())),
                AntiSpike(spike(getSeries(), Integer.parseInt(tfCountOfPoints.getText()), Integer.parseInt(tfSpikeDeep.getText())),
                        Double.parseDouble(tfk.getText()),
                        Double.parseDouble(tfb.getText())));
    }

    public void newAShift(ActionEvent actionEvent) {
        setValue(Double.parseDouble(tfk.getText()),
                Double.parseDouble(tfb.getText()),
                Integer.parseInt(tfN.getText())
        );

        printLine(chart1, getSeries(), AntiShift(shift(getSeries(), Integer.parseInt(tfShift.getText()))));
    }
}
