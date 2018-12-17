package sample.Kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Line;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ReadCSV extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Double, Double> series1;
    private XYChart.Series<Double, Double> series2;

    public ReadCSV() {
        super(0);
    }

    @FXML
    LineChart<Double, Double> chart1;
    @FXML
    LineChart<Double, Double> chart2;
    @FXML
    LineChart<Double, Double> chart3;
    @FXML
    LineChart<Double, Double> chart4;
    @FXML
    AreaChart<?, ?> bch;
    @FXML
    LineChart<Double, Double> chart6;
    @FXML
    LineChart<Double, Double> chart7;
    @FXML
    LineChart<Double, Double> chart8;
    @FXML
    TextField tfN;
    @FXML
    NumberAxis xAxis;
    @FXML
    CategoryAxis yAxis;
    @FXML
    NumberAxis xBarAxis;
    @FXML
    NumberAxis yBarAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    private XYChart.Series<Double, Double> getSeries(String csvpath) throws IOException {
        series1 = new XYChart.Series<>();

        Scanner scanner = new Scanner(new File(csvpath));
        scanner.useDelimiter("(;)|(\\r\\n)");
        ArrayList<String> data = new ArrayList<String>();
        while (scanner.hasNext()) {
            data.add(scanner.next());
            //System.out.print(scanner.next()+"|");
        }
        scanner.close();

        for (int i = 4; i + 6 + 5 <= data.size(); i += 6) {
            series1.getData().add(new XYChart.Data<>((double) ((i + 2) / 6), Double.valueOf(data.get(i + 6))));
        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }
        series1.setName(csvpath);
        return series1;
    }

/*    private void getDifference(String csvpath) throws IOException {
        XYChart.Series bseries1 = new XYChart.Series<>();
        XYChart.Series bseries2 = new XYChart.Series<>();

        Scanner scanner = new Scanner(new File(csvpath));
        scanner.useDelimiter("(;)|(\\r\\n)");
        ArrayList<String> data = new ArrayList<String>();
        while(scanner.hasNext()){
            data.add(scanner.next());
            //System.out.print(scanner.next()+"|");
        }
        scanner.close();
        double diff;
        double prev = 0.d;
        double arg1, arg2;
        double curr;

        for (int i = 4; i+24 <= data.size() - 1; i+=6) {
            curr = Double.valueOf(data.get(i+6)) - Double.valueOf(data.get(i+12));
            if (curr > prev){
                prev = Double.valueOf(data.get(i+6)) - Double.valueOf(data.get(i+12));
                bseries1.getData().add(new XYChart.Data<>(Double.valueOf(i), curr));}
                else {
                prev = Double.valueOf(data.get(i+6)) - Double.valueOf(data.get(i+12));
                bseries2.getData().add(new XYChart.Data<>(Double.valueOf(i), curr));}


        }

//        for (int i = 0; i < N; i++)
//        {
//            series1.getData().add(new XYChart.Data<>(Double.valueOf(i), Double.valueOf(rand.nextInt(highvalue)+lowvalue)));
//        }
        //series1.setName(csvpath);
        //printLine(bch, series1, series2);

        bch.getData().addAll(bseries1, bseries2);
    }*/

    private int size(XYChart.Series<Double, Double> ser) {
        int size = ser.getData().size();
        return size;
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample/Kursovaya/ReadCSV.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 1000, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {
        /*printLine(chart1
                ,getSeries("src/google5.csv")
                ,getSeries("src/apple5.csv")
                ,getSeries("src/microsoft5.csv")
                ,getSeries("src/netflix5.csv")
                );*/

        printLine(chart1, getSeries("src/google5.csv"));
        printLine(chart2, getSeries("src/apple5.csv"));
        printLine(chart3, getSeries("src/microsoft5.csv"));
        printLine(chart4, getSeries("src/netflix5.csv"));
    }

    public void newCorrelationIt(ActionEvent actionEvent) throws IOException {
        /*printLine(chart1, CrossCorrelation(
                Convalution(HPF(LPF(15, 0.002d, 128),15, 0.002d, 128),
                Convalution(LPF(15, 0.002d, 128), getSeries("src/google5.csv"))),
                Convalution(HPF(LPF(15, 0.002d, 128),15, 0.002d, 128),
                        Convalution(LPF(15, 0.002d, 128), getSeries("src/apple5.csv")))
                ));*/

        /*printLine(chart1
                ,Convalution(LPF(15, 0.002d, 128), getSeries("src/google5.csv"))
                ,Convalution(LPF(15, 0.002d, 128), getSeries("src/apple5.csv"))
                ,Convalution(LPF(15, 0.002d, 128), getSeries("src/microsoft5.csv"))
                ,Convalution(LPF(15, 0.002d, 128), getSeries("src/netflix5.csv")));*/
        //getDifference("src/apple5.csv");
    }

    public void newFurieIt(ActionEvent actionEvent) throws IOException {
        this.N = size(getSeries("src/apple5.csv"));
        printLine(chart1
                , Furie(getSeries("src/netflix5.csv")));
    }
}
