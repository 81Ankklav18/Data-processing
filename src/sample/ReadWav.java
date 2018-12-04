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
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;

import static com.sun.deploy.config.Config.getCacheDirectory;

public class ReadWav extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Float, Float> series1;

    public ReadWav()
    {
        super(11025);
    }

    @FXML
    LineChart<Float, Float> chart1;
    @FXML
    TextField tfN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Float, Float> getSeries() throws IOException {
        series1 = new XYChart.Series<>();
//  read the file into a byte array
        RandomAccessFile file = new RandomAccessFile( "src/betkhoven-lunnaja-sonata.wav", "r");
        //FileInputStream fis = new FileInputStream(file);
        //TODO: убрать константу
        float[] fl = new float[11025];

        double[] fld = new double[11025];

        try(FileChannel fc = new RandomAccessFile("src/betkhoven-lunnaja-sonata.wav", "rw").getChannel()) {
            DoubleBuffer fb = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size())
                    .order(ByteOrder.nativeOrder()).asDoubleBuffer();
            fb.get(fld);
        }

        for (int i = 0; i < fl.length; i++) {
            series1.getData().add(new XYChart.Data<>((float) i, fl[i]));
        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }

        return series1;
    }

    public XYChart.Series<Float, Float> Furie(XYChart.Series<Float, Float> series1) {
        float[] re = new float[N];
        float[] im = new float[N];

        XYChart.Series<Float, Float> series2;
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

        for (int i = 0; i < N; i++)
        {
            series2.getData().add(new XYChart.Data<>((float) i, (float)((Math.sqrt(Math.pow(re[i], 2) + Math.pow(im[i], 2))))));
        }
        return series2;
    }

    public XYChart.Series<Float, Float> ReversFurie(XYChart.Series<Float, Float> series1) {
        float[] re = new float[N];
        float[] im = new float[N];

        XYChart.Series<Float, Float> series2;
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

        for (int i = 0; i < N; i++)
        {
            series2.getData().add(new XYChart.Data<>((float) i, re[i]+im[i]));
        }
        XYChart.Series<Float, Float> series3;
        series3 = new XYChart.Series<>();
        float[] re1 = new float[N];
        float[] im1 = new float[N];

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
            series3.getData().add(new XYChart.Data<>((float) i, (float)re1[i] + im1[i]));
        }
        return series3;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ReadWav.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {

        printLineFl(chart1, getSeries());
    }

}
