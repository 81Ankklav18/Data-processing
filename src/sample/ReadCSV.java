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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ReadCSV extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Double, Double> series1;

    public ReadCSV()
    {
        super(0);
    }

    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    TextField tfN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Double, Double> getSeries(String csvpath) throws IOException {
        series1 = new XYChart.Series<>();

        Scanner scanner = new Scanner(new File(csvpath));
        scanner.useDelimiter("(;)|(\\r\\n)");
        ArrayList<String> data = new ArrayList<String>();
        while(scanner.hasNext()){
            data.add(scanner.next());
            //System.out.print(scanner.next()+"|");
        }
        scanner.close();

        for (int i = 4; i+6+5 <= data.size(); i+=6) {
            series1.getData().add(new XYChart.Data<>((double)((i+2)/6), Double.valueOf(data.get(i+6))));
        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }

        return series1;
    }

    private int size(XYChart.Series<Double, Double> ser) {
        int size = ser.getData().size();
        return size;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ReadCSV.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 600, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {
        printLine(chart1,
                 getSeries("src/google5.csv")
                ,getSeries("src/apple5.csv")
                ,getSeries("src/microsoft5.csv")
                ,getSeries("src/netflix5.csv")
                );
    }
}
