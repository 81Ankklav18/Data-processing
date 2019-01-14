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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ReadCSV extends Line implements Initializable {
    private int x;
    private int y;

    private XYChart.Series<Double, Double> series1;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*...*/}

    public XYChart.Series<Double, Double> getSeries(String csvpath) throws IOException {
        series1 = new XYChart.Series<>();

        Scanner scanner = new Scanner(new File(csvpath));
        scanner.useDelimiter("(;)|(\\r\\n)");
        ArrayList<String> data = new ArrayList<String>();
        while (scanner.hasNext()) {
            data.add(scanner.next());
        }
        scanner.close();

        for (int i = 4; i + 6 + 5 <= data.size(); i += 6) {
            series1.getData().add(new XYChart.Data<>((double) ((i + 2) / 6), Double.valueOf(data.get(i + 6))));
        }

        series1.setName(csvpath);
        return series1;
    }

    ArrayList<String> getData(String csvpath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(csvpath));
        scanner.useDelimiter("(;)|(\\r\\n)");
        ArrayList<String> data = new ArrayList<String>();
        while (scanner.hasNext()) {
            data.add(scanner.next());
        }
        scanner.close();

        return data;
    }

    private int size(XYChart.Series<Double, Double> ser) {
        int size = ser.getData().size();
        return size;
    }

    public XYChart.Series<Double, Double> printFurieCSV(String csvpath) throws IOException {
        this.N = size(getSeries(csvpath));
        return Furie(getSeries(csvpath));
    }

    public void show() throws IOException {
        Stage stageLF = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ReadCSV.fxml"));
        stageLF.setTitle("Simple Graphics");

        stageLF.setScene(new Scene(root, 1000, 600));

        stageLF.show();
    }

    public void newBuildIt(ActionEvent actionEvent) throws IOException {
        printLine(chart1, getSeries("src/google5.csv"));
        printLine(chart2, getSeries("src/apple5.csv"));
        printLine(chart3, getSeries("src/microsoft5.csv"));
        printLine(chart4, getSeries("src/netflix5.csv"));
    }

    public void newFurieIt(ActionEvent actionEvent) throws IOException {
        this.N = size(getSeries("src/apple5.csv"));
        printLine(chart1, Furie(getSeries("src/google5.csv")));
        printLine(chart2, Furie(getSeries("src/apple5.csv")));
        printLine(chart3, Furie(getSeries("src/microsoft5.csv")));
        printLine(chart4, Furie(getSeries("src/netflix5.csv")));
    }

    public void newCourseProjectAnalysis(ActionEvent actionEvent) throws IOException {
        CourseProject courseProject = new CourseProject();
        courseProject.show();
    }
}
