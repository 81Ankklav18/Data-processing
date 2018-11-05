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

        series1 = new XYChart.Series<Double, Double>();
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
            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf((temp[i]-min)/(max-min)-0.5d)));
        }

        return series1;
    }

    public XYChart.Series<Double, Double> getSeriesMy() throws InterruptedException {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        double[] temp = new double[N];

        series1 = new XYChart.Series<Double, Double>();
        double rn = 0;
        double val;
        for (int i = 0; i < N; i++)
        {
            val = Math.sin(System.currentTimeMillis()/2)/2+0.5d;
            sleep(100);
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
            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf((temp[i]-min)/(max-min)-0.5d)));
        }
        return series1;
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
        if (rbrn.isSelected() == true)
        {
            setValue(Integer.parseInt(tflv.getText()), Integer.parseInt(tfhv.getText()), Integer.parseInt(tfN.getText()));
            printLine(chart1, getSeries());
        }
        if(rbmyrn.isSelected() == true)
        {
            setValue(Integer.parseInt(tflv.getText()), Integer.parseInt(tfhv.getText()), Integer.parseInt(tfN.getText()));
            printLine(chart1, getSeriesMy());
        }
    }
}
