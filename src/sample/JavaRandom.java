package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class JavaRandom extends Line implements Initializable {
    private int lowvalue;
    private int highvalue;

    private XYChart.Series<Double, Double> series1;
    private Random rand = new Random();

    public JavaRandom(int lv, int hv, int N)
    {
        super(N);
        this.lowvalue = lv;
        this.highvalue = hv;
    }

    public JavaRandom()
    {
        super(0);
        this.lowvalue = 0;
        this.highvalue = 0;
    }

    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    TextField tflv;
    @FXML
    TextField tfhv;
    @FXML
    TextField tfN;
    @FXML
    RadioButton rbrn;
    @FXML
    RadioButton rbmyrn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    public XYChart.Series<Double, Double> getSeries()
    {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        double[] temp = new double[N];

        series1 = new XYChart.Series<>();
        double val;
        for (int i = 0; i < N; i++)
        {
            val = rand.nextInt(highvalue)+lowvalue;
            temp[i] = val;
            if(val > max)
            {
                max = val;
            }
            if (val < min)
            {
                min = val;
            }
        }

        for (int i = 0; i < N; i++)
        {
            series1.getData().add(new XYChart.Data<>((double) i, (temp[i] - min) / (max - min) - 0.5d));
        }

        return series1;
    }

    public XYChart.Series<Double, Double> getSeriesMy() throws InterruptedException {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        double[] temp = new double[N];

        series1 = new XYChart.Series<>();
        double val;
        for (int i = 0; i < N; i++)
        {
            val = Math.sin(System.currentTimeMillis()/2.d)/2.d+0.5d;
            sleep(10);
            temp[i] = val;
            if(val > max)
            {
                max = val;
            }
            if (val < min)
            {
                min = val;
            }
        }

        for (int i = 0; i < N; i++)
        {
            series1.getData().add(new XYChart.Data<>((double) i, (temp[i] - min) / (max - min) - 0.5d));
        }
        return series1;
    }

    public XYChart.Series<Double, Double> Furie(XYChart.Series<Double, Double> series1) throws InterruptedException {
        double[] re = new double[N];
        double[] im = new double[N];

        XYChart.Series<Double, Double> series2;
        series2 = new XYChart.Series<>();
        double val;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                re[i] = series1.getData().get(i).getYValue() * Math.cos((2 * Math.PI * i * j) / N) / N;
                im[i] = series1.getData().get(i).getYValue() * Math.sin((2 * Math.PI * i * j) / N) / N;
            }
        }

        for (int i = 0; i < N / 2; i++)
        {
            series2.getData().add(new XYChart.Data<>((double) i, Math.sqrt(Math.pow(re[i], 2) + Math.pow(im[i], 2))));
        }
        return series2;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("JavaRandom.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    void setValue(int lv, int hv, int N)
    {
        this.lowvalue = lv;
        this.highvalue = hv;
        super.N = N;
    }

    public void newBuildIt(ActionEvent actionEvent) throws InterruptedException {
        if (rbrn.isSelected())
        {
            setValue(Integer.parseInt(tflv.getText()), Integer.parseInt(tfhv.getText()), Integer.parseInt(tfN.getText()));
            printLine(chart1, getSeries());
        }
        if(rbmyrn.isSelected())
        {
            setValue(1, 1, Integer.parseInt(tfN.getText()));
            printLine(chart1, getSeriesMy());
        }
    }


    public void newAC(ActionEvent actionEvent) throws InterruptedException {
        if (rbrn.isSelected())
        {
            setValue(Integer.parseInt(tflv.getText()), Integer.parseInt(tfhv.getText()), Integer.parseInt(tfN.getText()));
            printLine(chart1, AutoCorrelation(getSeries()));
        }
        if(rbmyrn.isSelected())
        {
            setValue(1, 1, Integer.parseInt(tfN.getText()));
            printLine(chart1, AutoCorrelation(getSeriesMy()));
        }
    }

    public void newAC1(ActionEvent actionEvent) throws InterruptedException {
        if (rbrn.isSelected())
        {
            setValue(Integer.parseInt(tflv.getText()), Integer.parseInt(tfhv.getText()), Integer.parseInt(tfN.getText()));
            printLine(chart1, AutoCov(getSeries()));
        }
        if(rbmyrn.isSelected())
        {
            setValue(1, 1, Integer.parseInt(tfN.getText()));
            printLine(chart1, AutoCov(getSeriesMy()));
        }
    }

    public void newMC(ActionEvent actionEvent) throws InterruptedException {
        setValue(Integer.parseInt(tflv.getText()), Integer.parseInt(tfhv.getText()), Integer.parseInt(tfN.getText()));

        printLine(chart1, MutualCorrelation(getSeries(), getSeriesMy()));
    }

    public void newFr(ActionEvent actionEvent) throws InterruptedException {
        setValue(Integer.parseInt(tflv.getText()), Integer.parseInt(tfhv.getText()), Integer.parseInt(tfN.getText()));
        printLine(chart1, Furie(getSeries()));
    }
}
